package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
// ... (outros imports)
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleListUiState())
    val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

    init {
        //  MUDANÇA: O loadVehicles agora só "assiste" ao Room.
        observeLocalVehicles()

        //  MUDANÇA: Adicionamos uma chamada separada para sincronizar
        //    com o Firebase assim que o ViewModel for criado.
        syncVehiclesFromRemote()
    }

    //  MUDANÇA: Renomeamos 'loadVehicles' para ser mais específico
    private fun observeLocalVehicles() {
        viewModelScope.launch {
            repository.getVehiclesStream() // Pega o Flow do Room
                .onStart {
                    // (Lógica antiga)
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    // (Lógica antiga) Se o Room falhar (raro)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Erro ao carregar dados locais: ${exception.message}"
                        )
                    }
                }
                .collect { vehicleList ->
                    // (Lógica antiga)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicles = vehicleList,
                            // Limpa qualquer erro anterior assim que novos dados chegarem
                            error = null
                        )
                    }
                }
        }
    }

    //  MUDANÇA: Esta é a nova função que chama o Repository
    private fun syncVehiclesFromRemote() {
        viewModelScope.launch {
            try {
                //  Inicia o "carregamento" (mesmo que a lista já esteja sendo exibida)
                //    para indicar atividade de rede (opcional)
                _uiState.update { it.copy(isLoading = true) }

                //  Chama a nova função do repositório
                repository.syncVehiclesFromFirestore()

                //  Se a sincronização for bem-sucedida, o 'collect'
                //    do 'observeLocalVehicles' cuidará de atualizar a UI.
                //    Podemos apenas parar o indicador 'isLoading'.
                //    (Na verdade, o 'collect' já fará isso, mas é bom ser explícito)
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                //  Se o repository.sync...() lançar uma exceção (sem internet),
                //    nós a capturamos aqui e mostramos um erro.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Falha ao sincronizar: ${e.message}"
                    )
                }
            }
        }
    }

    // ... (outras funções futuras)
}