package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle


import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo
data class AddVehicleUiState(
    // Campos do formulário
    val plate: String = "",
    val model: String = "",
    val manufacturer: String = "",
    val modelYear: String = "",
    val color: String = "",
    val mileage: String = "",
    val ownerName: String = "",
    val status: String = "Active",
    val notes: String = "",

    val type: TipoDeVeiculo = TipoDeVeiculo.CARRO,

    // Campos de Data (ussei Long? para o timestamp)
    val lastRevision: Long? = null,
    val nextRevision: Long? = null,
    val registrationDueDate: Long? = null,

    // Campos de controle da UI
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val isSaveSuccess: Boolean = false,
    val isPlateError: Boolean = false
)
