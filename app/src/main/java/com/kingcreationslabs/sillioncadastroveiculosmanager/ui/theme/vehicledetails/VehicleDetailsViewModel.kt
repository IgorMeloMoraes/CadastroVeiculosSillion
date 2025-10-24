package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo // Importe o Enum

private val PLATE_REGEX = Regex("^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$")

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    private val repository: VehicleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState = _uiState.asStateFlow()

    private val vehiclePlate: String? = savedStateHandle.get("plate")

    init {
        if (vehiclePlate != null) {
            loadVehicle(vehiclePlate)
        } else {
            _uiState.update { it.copy(saveError = "Erro: Placa não encontrada.") }
        }
    }

    private fun loadVehicle(plate: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val vehicle = repository.getVehicleByPlate(plate).filterNotNull().first()

            _uiState.update {
                it.copy(
                    isSaving = false,
                    plate = vehicle.plate,
                    model = vehicle.model,
                    manufacturer = vehicle.manufacturer,
                    modelYear = vehicle.modelYear.toString(),
                    color = vehicle.color,
                    mileage = vehicle.mileage.toString(),
                    ownerName = vehicle.ownerName,
                    status = vehicle.status,
                    notes = vehicle.notes,

                    type = vehicle.type,

                    lastRevision = vehicle.lastRevision?.time,
                    nextRevision = vehicle.nextRevision?.time,
                    registrationDueDate = vehicle.registrationDueDate?.time
                )
            }
        }
    }


    fun onPlateChange(newValue: String) {

    }

    fun onModelChange(newValue: String) {
        _uiState.update { it.copy(model = newValue) }
    }

    fun onManufacturerChange(newValue: String) {
        _uiState.update { it.copy(manufacturer = newValue) }
    }

    fun onModelYearChange(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 4) {
            _uiState.update { it.copy(modelYear = newValue) }
        }
    }

    fun onColorChange(newValue: String) {
        _uiState.update { it.copy(color = newValue) }
    }

    fun onMileageChange(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            _uiState.update { it.copy(mileage = newValue) }
        }
    }

    fun onOwnerNameChange(newValue: String) {
        _uiState.update { it.copy(ownerName = newValue) }
    }

    fun onNotesChange(newValue: String) {
        _uiState.update { it.copy(notes = newValue) }
    }

    fun onTypeChanged(type: TipoDeVeiculo) {
        _uiState.update { it.copy(type = type) }
    }

    fun onLastRevisionDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(lastRevision = dateInMillis) }
    }

    fun onNextRevisionDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(nextRevision = dateInMillis) }
    }

    fun onRegistrationDueDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(registrationDueDate = dateInMillis) }
    }

    fun saveVehicle() {
        val currentState = _uiState.value

        if (currentState.plate.isBlank() || currentState.model.isBlank() || currentState.manufacturer.isBlank()) {
            _uiState.update { it.copy(saveError = "Placa, Modelo e Fabricante são obrigatórios.") }
            return
        }

        if (currentState.isPlateError || !PLATE_REGEX.matches(currentState.plate.replace("-", ""))) {
            _uiState.update { it.copy(saveError = "Formato da placa inválido. Use AAA-1234 ou AAA1B23.") }
            return
        }

        val vehicle = Vehicle(
            plate = currentState.plate,
            model = currentState.model,
            manufacturer = currentState.manufacturer,
            modelYear = currentState.modelYear.toIntOrNull() ?: 0,
            color = currentState.color,
            mileage = currentState.mileage.toLongOrNull() ?: 0L,
            ownerName = currentState.ownerName,
            status = currentState.status,
            notes = currentState.notes,

            type = currentState.type,

            lastRevision = currentState.lastRevision?.let { Date(it) },
            nextRevision = currentState.nextRevision?.let { Date(it) },
            registrationDueDate = currentState.registrationDueDate?.let { Date(it) },
            timestamp = null
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null) }
            try {
                repository.upsertVehicle(vehicle)
                _uiState.update { it.copy(isSaving = false, isSaveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, saveError = "Erro ao salvar: ${e.message}") }
            }
        }
    }

    fun onSaveComplete() {
        _uiState.update { it.copy(isSaveSuccess = false) }
    }
}