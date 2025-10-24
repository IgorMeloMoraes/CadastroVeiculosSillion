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

// Adicione a constante Regex fora das funções
private val PLATE_REGEX = Regex("^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$")

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    private val repository: VehicleRepository,
    // 1. (NOVO) Injetamos o 'SavedStateHandle'
    // É um objeto especial do Hilt/Navigation que nos dá
    // acesso aos argumentos da rota (no caso, a "plate")
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 2. (REUTILIZADO) Usamos o MESMO UiState da tela de Adicionar
    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState = _uiState.asStateFlow()

    // 3. (NOVO) Pegamos a placa da rota
    private val vehiclePlate: String? = savedStateHandle.get("plate")

    // 4. (NOVO) O bloco 'init' agora carrega o veículo
    init {
        if (vehiclePlate != null) {
            loadVehicle(vehiclePlate)
        } else {
            // Caso de erro (não devia acontecer)
            _uiState.update { it.copy(saveError = "Erro: Placa não encontrada.") }
        }
    }

    // 5. (NOVO) Função que busca o veículo no repositório
    private fun loadVehicle(plate: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) } // Mostra "loading"

            // Usamos .filterNotNull().first() para pegar o *primeiro*
            // valor não-nulo do Flow. Isso "carrega" o veículo uma vez.
            val vehicle = repository.getVehicleByPlate(plate).filterNotNull().first()

            // 6. (NOVO) Converte o objeto 'Vehicle' (Model) para o 'AddVehicleUiState'
            // Isso pré-preenche o formulário
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

                    // 1. (MUDANÇA) Carregue o tipo do veículo no state
                    type = vehicle.type,

                    // Converte Date? (do Model) para Long? (do UiState)
                    lastRevision = vehicle.lastRevision?.time,
                    nextRevision = vehicle.nextRevision?.time,
                    registrationDueDate = vehicle.registrationDueDate?.time
                )
            }
        }
    }

    // --- (INÍCIO DO CÓDIGO COPIADO) ---
    // As funções abaixo são IDÊNTICAS às do AddVehicleViewModel
    // (Pode copiar e colar do seu AddVehicleViewModel.kt)

    fun onPlateChange(newValue: String) {
        // Na tela de edição, não permitimos mudar a placa.
        // Mas manteremos a função (embora a UI vá bloqueá-la)
        // _uiState.update { it.copy(plate = newValue.uppercase()) }
    }

    fun onModelChange(newValue: String) {
        _uiState.update { it.copy(model = newValue) }
    }

    fun onManufacturerChange(newValue: String) {
        _uiState.update { it.copy(manufacturer = newValue) }
    }

    fun onModelYearChange(newValue: String) {
        // Permite apenas números E limita a 4 caracteres
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

    // 2. (NOVA FUNÇÃO) (pode copiar do AddVehicleViewModel)
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

    // --- (FIM DO CÓDIGO COPIADO) ---

    // --- Ação Principal: Salvar (Update) ---
    // Esta função também é IDÊNTICA. Por quê?
    // Porque nosso 'repository.upsertVehicle()' já é inteligente!
    // Ele faz "Update" se a placa já existe, ou "Insert" se não.
    // Para a tela de Edição, ele sempre fará um "Update".
    fun saveVehicle() {
        val currentState = _uiState.value

        if (currentState.plate.isBlank() || currentState.model.isBlank() || currentState.manufacturer.isBlank()) {
            _uiState.update { it.copy(saveError = "Placa, Modelo e Fabricante são obrigatórios.") }
            return
        }

        // *** NOVA VALIDAÇÃO DE PLACA ***
        if (currentState.isPlateError || !PLATE_REGEX.matches(currentState.plate.replace("-", ""))) {
            _uiState.update { it.copy(saveError = "Formato da placa inválido. Use AAA-1234 ou AAA1B23.") }
            return
        }
        // *** FIM DA NOVA VALIDAÇÃO ***

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

            // 3. (MUDANÇA) Passe o tipo do state para o modelo
            //    (O resto do código já estava correto)
            type = currentState.type,

            lastRevision = currentState.lastRevision?.let { Date(it) },
            nextRevision = currentState.nextRevision?.let { Date(it) },
            registrationDueDate = currentState.registrationDueDate?.let { Date(it) },
            timestamp = null
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null) }
            try {
                // Esta chamada fará o UPDATE (Upsert)
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