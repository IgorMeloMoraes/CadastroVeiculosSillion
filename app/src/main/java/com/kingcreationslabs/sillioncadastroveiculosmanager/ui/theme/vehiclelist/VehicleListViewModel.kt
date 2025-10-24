package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//  @HiltViewModel: Diz ao Hilt que esta classe é um ViewModel
//    e que ele deve gerenciar sua criação.
@HiltViewModel
class VehicleListViewModel @Inject constructor(
    //  @Inject constructor: O Hilt vai injetar a *implementação*
    //    do VehicleRepository (que ensinamos no RepositoryModule).
    private val repository: VehicleRepository
) : ViewModel() { //  Herda de ViewModel

    //  O "Estado" privado e mutável da nossa UI.
    //    Só o ViewModel pode modificar este.
    private val _uiState = MutableStateFlow(VehicleListUiState())

    //  O "Estado" público e imutável (read-only).
    //    A UI (Compose) vai "observar" este StateFlow.
    val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

    //  O bloco "init" é executado assim que o ViewModel é criado.
    init {
        loadVehicles()
    }

    //  Função principal que busca os dados.
    private fun loadVehicles() {
        //  "viewModelScope.launch" inicia uma Coroutine que
        //    viverá enquanto o ViewModel viver. É seguro para
        //    operações longas, como observar um banco de dados.
        viewModelScope.launch {
            repository.getVehiclesStream() //  Pega o Flow do Repositório
                .onStart {
                    //  Antes de começar a coletar, atualiza o estado
                    //    para "isLoading = true".
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    //  Se o Flow (do Room) der um erro,
                    //     capturamos e atualizamos o estado de erro.
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                }
                .collect { vehicleList ->
                    //  (O principal) Esta é a "magia" do Flow.
                    //     Toda vez que o Room emitir uma nova lista
                    //     (seja na primeira vez, ou após um "upsert"),
                    //     este bloco "collect" será executado.
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicles = vehicleList
                        )
                    }
                }
        }
    }

    // Futuramente, adicionaremos funções aqui como:
    // fun onAddVehicleClicked(vehicle: Vehicle) { ... }
    // fun onDeleteVehicle(vehicle: Vehicle) { ... }
}