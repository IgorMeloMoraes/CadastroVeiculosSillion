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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _selectedType = MutableStateFlow<TipoDeVeiculo?>(null)

    private val _uiState = MutableStateFlow(VehicleListUiState())
    val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

    init {
        observeVehiclesAndFilter()
        syncVehiclesFromRemote()
    }

    private fun observeVehiclesAndFilter() {
        viewModelScope.launch {
            combine(
                repository.getVehiclesStream(),
                _selectedType
            ) { vehicles, selectedType ->
                val filteredVehicles = if (selectedType == null) {
                    vehicles
                } else {
                    vehicles.filter { it.type == selectedType }
                }

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
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicles = filteredList,
                            selectedType = currentFilter,
                            error = null
                        )
                    }
                }
        }
    }

    private fun syncVehiclesFromRemote() {
        viewModelScope.launch {
            try {
                repository.syncVehiclesFromFirestore()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Falha ao sincronizar: ${e.message}"
                    )
                }
            }
        }
    }

    fun onFilterChanged(type: TipoDeVeiculo?) {
        _selectedType.value = type

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