package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails

// --- IMPORTS ---
// (São os mesmos da AddVehicleScreen)
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleUiState // <-- REUTILIZAMOS O UiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
// 1. (NOVA IMPORTAÇÃO) Importe o componente que acabamos de criar
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components.TypeSelectionDropdown

// 1. (MUDANÇA) Removemos o 'plate: String?'
//    Não precisamos mais dele, pois o ViewModel cuidará disso.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    onBack: () -> Unit,
    // 2. (MUDANÇA) Chamamos o ViewModel correto
    viewModel: VehicleDetailsViewModel = hiltViewModel()
) {
    // 3. (MUDANÇA) Observamos o UiState do ViewModel de Detalhes
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // 4. (SEM MUDANÇA) A lógica de 'isSaveSuccess' e 'saveError' é IDÊNTICA
    LaunchedEffect(key1 = uiState.isSaveSuccess) {
        if (uiState.isSaveSuccess) {
            Toast.makeText(context, "Veículo atualizado!", Toast.LENGTH_SHORT).show()
            viewModel.onSaveComplete()
            onBack()
        }
    }

    LaunchedEffect(key1 = uiState.saveError) {
        if (uiState.saveError != null) {
            Toast.makeText(context, uiState.saveError, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // 5. (MUDANÇA) Título da tela
                title = { Text("Editar Veículo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // O restante do formulário é IDÊNTICO, exceto pelo campo 'plate'
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- CAMPO DE PLACA BLOQUEADO ---
            // 6. (MUDANÇA PRINCIPAL)
            OutlinedTextField(
                value = uiState.plate,
                onValueChange = { }, // Não faz nada
                label = { Text("Placa (Não pode ser editada)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false, // <-- A MUDANÇA
                singleLine = true
            )

            // O RESTANTE DO FORMULÁRIO É EXATAMENTE O MESMO

            OutlinedTextField(
                value = uiState.manufacturer,
                onValueChange = viewModel::onManufacturerChange,
                label = { Text("Fabricante*") },
                // ... (igual)
            )

            OutlinedTextField(
                value = uiState.model,
                onValueChange = viewModel::onModelChange,
                label = { Text("Modelo*") },
                // ... (igual)
            )

            // 2. (NOVO COMPONENTE ADICIONADO AQUI)
            TypeSelectionDropdown(
                selectedType = uiState.type,
                onTypeSelected = viewModel::onTypeChanged,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                // ... (igual)
            ) {
                OutlinedTextField(
                    value = uiState.modelYear,
                    onValueChange = viewModel::onModelYearChange,
                    // ... (igual)
                )
                OutlinedTextField(
                    value = uiState.color,
                    onValueChange = viewModel::onColorChange,
                    // ... (igual)
                )
            }

            OutlinedTextField(
                value = uiState.mileage,
                onValueChange = viewModel::onMileageChange,
                // ... (igual)
            )

            OutlinedTextField(
                value = uiState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                // ... (igual)
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = viewModel::onNotesChange,
                // ... (igual)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Seletores de Data (IDÊNTICOS) ---
            DatePickerButton(
                labelText = "Última Revisão",
                selectedDate = uiState.lastRevision,
                onDateSelected = viewModel::onLastRevisionDateSelected
            )

            DatePickerButton(
                labelText = "Próxima Revisão",
                selectedDate = uiState.nextRevision,
                onDateSelected = viewModel::onNextRevisionDateSelected
            )

            DatePickerButton(
                labelText = "Vencimento Licenciamento",
                selectedDate = uiState.registrationDueDate,
                onDateSelected = viewModel::onRegistrationDueDateSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botão de Salvar (IDÊNTICO) ---
            Button(
                onClick = viewModel::saveVehicle, // Chama o 'saveVehicle' do ViewModel de Detalhes
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = !uiState.isSaving
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        // ... (igual)
                    )
                } else {
                    Text("SALVAR ALTERAÇÕES") // 7. (MUDANÇA) Texto do botão
                }
            }
        }
    }
}

// --- (CÓDIGO DUPLICADO) ---
// (Nota de Sênior: Para um projeto maior, moveríamos
// estes dois helpers para um arquivo 'utils/ComposeUtils.kt'
// para evitar duplicação. Para este teste, está ok copiá-los.)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerButton(
    labelText: String,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val selectedMillis = datePickerState.selectedDateMillis
                    onDateSelected(selectedMillis)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = selectedDate.toFormattedDateString(),
        onValueChange = { },
        readOnly = true,
        label = { Text(labelText) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data")
            }
        }
    )
}

@SuppressLint("SimpleDateFormat")
private fun Long?.toFormattedDateString(): String {
    if (this == null) return ""
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}