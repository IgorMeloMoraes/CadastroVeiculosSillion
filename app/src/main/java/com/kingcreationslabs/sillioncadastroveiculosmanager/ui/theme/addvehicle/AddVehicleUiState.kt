package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle

data class AddVehicleUiState(
    // Campos do formulário (usamos Strings, o ViewModel validará)
    val plate: String = "",
    val model: String = "",
    val manufacturer: String = "",
    val modelYear: String = "",
    val color: String = "",
    val mileage: String = "",
    val ownerName: String = "",
    val status: String = "Active", // "Active" como padrão
    val notes: String = "",

    // Campos de Data (usamos Long? para o timestamp)
    val lastRevision: Long? = null,
    val nextRevision: Long? = null,
    val registrationDueDate: Long? = null,

    // Campos de controle da UI
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val isSaveSuccess: Boolean = false // Usaremos para disparar a navegação
)
