package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo // <-- IMPORT do Enum
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine // <-- (NOVA IMPORTAÇÃO)
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    // 1. (NOVO) StateFlow privado para guardar o filtro selecionado
    private val _selectedType = MutableStateFlow<TipoDeVeiculo?>(null) // Começa com "Todos"

    // 2. (MODIFICADO) O UiState agora é construído combinando vários fluxos
    private val _uiState = MutableStateFlow(VehicleListUiState())
    val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

    init {
        // 3. (MODIFICADO) Renomeado para ficar mais claro
        observeVehiclesAndFilter()
        syncVehiclesFromRemote()
    }

    // 4. (MODIFICADO) Função principal que combina e filtra
    private fun observeVehiclesAndFilter() {
        viewModelScope.launch {
            // Usamos 'combine' para juntar o fluxo de veículos do repo
            // com o nosso StateFlow do filtro selecionado.
            combine(
                repository.getVehiclesStream(), // Fluxo 1: Lista completa do Room
                _selectedType                // Fluxo 2: O filtro atual
            ) { vehicles, selectedType ->
                // Este bloco é executado sempre que 'vehicles' OU 'selectedType' mudar.

                // Filtramos a lista aqui, ANTES de atualizar a UI
                val filteredVehicles = if (selectedType == null) {
                    vehicles // Se o filtro for null ("Todos"), retorna a lista completa
                } else {
                    vehicles.filter { it.type == selectedType } // Senão, filtra pelo tipo
                }

                // Retornamos um 'Pair' (Par) com a lista filtrada e o filtro usado
                Pair(filteredVehicles, selectedType)
            }
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Erro ao carregar dados locais: ${exception.message}"
                        )
                    }
                }
                .collect { (filteredList, currentFilter) ->
                    // Recebemos o 'Pair' que criamos no 'combine'
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicles = filteredList, // A UI recebe a lista JÁ FILTRADA
                            selectedType = currentFilter, // Atualiza o filtro selecionado no State
                            error = null
                        )
                    }
                }
        }
    }

    private fun syncVehiclesFromRemote() {
        viewModelScope.launch {
            try {
                // Não precisamos mais do isLoading aqui, pois 'observeVehiclesAndFilter'
                // já trata disso no .onStart()
                // _uiState.update { it.copy(isLoading = true) } // (REMOVIDO)

                repository.syncVehiclesFromFirestore()

                // O isLoading será tratado pelo .collect() do combine quando os
                // dados sincronizados chegarem do Room.
                // _uiState.update { it.copy(isLoading = false) } // (REMOVIDO)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false, // Garante que parou o loading
                        error = "Falha ao sincronizar: ${e.message}"
                    )
                }
            }
        }
    }

    // 5. (NOVA FUNÇÃO) Chamada pela UI quando um filtro é clicado
    fun onFilterChanged(type: TipoDeVeiculo?) {
        _selectedType.value = type // Simplesmente atualiza o StateFlow do filtro
        // O 'combine' lá em cima vai reagir automaticamente a esta mudança.
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            try {
                repository.deleteVehicle(vehicle)
                _uiState.update { it.copy(userMessage = "Veículo excluído com sucesso") }
            } catch (e: Exception) {
                _uiState.update { it.copy(userMessage = "Erro ao excluir: ${e.message}") }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}