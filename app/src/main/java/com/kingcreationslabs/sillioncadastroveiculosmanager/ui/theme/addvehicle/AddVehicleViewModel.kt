package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo // Importe o Enum

// Adicione a constante Regex fora das funções
private val PLATE_REGEX = Regex("^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$")
@HiltViewModel
class AddVehicleViewModel @Inject constructor(

    private val repository: VehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState = _uiState.asStateFlow()

    // --- Funções para atualizar o estado (chamadas pela UI) ---

    // Modifique a função onPlateChange
    fun onPlateChange(newValue: String) {
        val uppercaseValue = newValue.uppercase()
        // Verifica o formato E o tamanho (7 caracteres sem hífen, 8 com)
        val isValid = PLATE_REGEX.matches(uppercaseValue.replace("-", "")) && uppercaseValue.replace("-","").length == 7

        _uiState.update {
            it.copy(
                plate = uppercaseValue,
                isPlateError = !isValid // Mostra erro se NÃO for válido
            )
        }
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
        // Permite apenas números no campo de quilometragem
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

    // 1. (NOVA FUNÇÃO)
    fun onTypeChanged(type: TipoDeVeiculo) {
        _uiState.update { it.copy(type = type) }
    }

    // --- Funções de Data (o 'dateInMillis' vem do DatePicker) ---

    fun onLastRevisionDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(lastRevision = dateInMillis) }
    }

    fun onNextRevisionDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(nextRevision = dateInMillis) }
    }

    fun onRegistrationDueDateSelected(dateInMillis: Long?) {
        _uiState.update { it.copy(registrationDueDate = dateInMillis) }
    }

    // --- Ação Principal: Salvar ---

    fun saveVehicle() {
        // Pega o estado atual
        val currentState = _uiState.value

        // 1. Validação (simples, mas demonstra a ideia)
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

        // 2. Conversão (de UiState -> Model)
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

            // 2. (MUDANÇA) Passe o tipo do state para o modelo
            type = currentState.type,

            // Converte os timestamps (Long?) para objetos Date?
            lastRevision = currentState.lastRevision?.let { Date(it) },
            nextRevision = currentState.nextRevision?.let { Date(it) },
            registrationDueDate = currentState.registrationDueDate?.let { Date(it) },
            timestamp = null // Firestore cuidará disso
        )

        // 3. Inicia o processo de salvar
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null) }
            try {
                // 4. Chama o Repositório (que salva no Firebase e depois Room)
                repository.upsertVehicle(vehicle)

                // 5. Sucesso!
                _uiState.update { it.copy(isSaving = false, isSaveSuccess = true) }
            } catch (e: Exception) {
                // 6. Falha
                _uiState.update { it.copy(isSaving = false, saveError = "Erro ao salvar: ${e.message}") }
            }
        }
    }

    // Função para a UI chamar após a navegação de volta
    fun onSaveComplete() {
        _uiState.update { it.copy(isSaveSuccess = false) }
    }
}