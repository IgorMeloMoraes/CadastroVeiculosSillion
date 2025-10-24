package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo

// Esta data class representa TUDO que a UI precisa saber
data class VehicleListUiState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(),
    val error: String? = null,
    val userMessage: String? = null,

    val selectedType: TipoDeVeiculo? = null
)
