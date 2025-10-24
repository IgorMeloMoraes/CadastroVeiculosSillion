package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle

// Esta data class representa TUDO que a UI precisa saber
data class VehicleListUiState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(), // Começa com uma lista vazia
    val error: String? = null
)
