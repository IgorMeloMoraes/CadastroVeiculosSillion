package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
// 1. (NOVA IMPORTAÇÃO ou VERIFIQUE SE EXISTE)
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo

// Esta data class representa TUDO que a UI precisa saber
data class VehicleListUiState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(), // Começa com uma lista vazia
    val error: String? = null,
    val userMessage: String? = null, // 1. ADICIONE ESTA LINHA

    // 2. (NOVO CAMPO)
    // Armazena o tipo de veículo selecionado para filtro.
    // 'null' representa a seleção de "Todos".
    val selectedType: TipoDeVeiculo? = null
)
