package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    onBack: () -> Unit, // Função para navegar de volta (da Sprint 1)
    viewModel: AddVehicleViewModel = hiltViewModel()
) {
    //  Observa o UiState do ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    //  Lógica para "escutar" o sucesso ao salvar.
    //    LaunchedEffect é um Composable que executa uma 'suspend fun'
    //    quando sua 'key' (uiState.isSaveSuccess) muda para true.
    LaunchedEffect(key1 = uiState.isSaveSuccess) {
        if (uiState.isSaveSuccess) {
            Toast.makeText(context, "Veículo salvo!", Toast.LENGTH_SHORT).show()
            viewModel.onSaveComplete() // Reseta o estado
            onBack() // Navega de volta
        }
    }

    // Lógica para "escutar" erros
    LaunchedEffect(key1 = uiState.saveError) {
        if (uiState.saveError != null) {
            Toast.makeText(context, uiState.saveError, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Veículo") },
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
        // 4. Column rolável para o formulário
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Torna o form rolável
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- Campos de Texto ---

            OutlinedTextField(
                value = uiState.plate,
                onValueChange = viewModel::onPlateChange,
                label = { Text("Placa*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.manufacturer,
                onValueChange = viewModel::onManufacturerChange,
                label = { Text("Fabricante*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.model,
                onValueChange = viewModel::onModelChange,
                label = { Text("Modelo*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            // Linha para Ano e Cor
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.modelYear,
                    onValueChange = viewModel::onModelYearChange,
                    label = { Text("Ano") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = uiState.color,
                    onValueChange = viewModel::onColorChange,
                    label = { Text("Cor") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = uiState.mileage,
                onValueChange = viewModel::onMileageChange,
                label = { Text("Quilometragem") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                label = { Text("Proprietário") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = viewModel::onNotesChange,
                label = { Text("Observações") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Seletores de Data ---

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

            // --- Botão de Salvar ---

            Button(
                onClick = viewModel::saveVehicle,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = !uiState.isSaving // Desabilita o botão enquanto salva
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("SALVAR VEÍCULO")
                }
            }
        }
    }
}

// --- Composable Auxiliar para Seleção de Data ---

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
                    // O DatePicker usa UTC. Adicionamos o offset do fuso horário
                    // para garantir que a data local seja salva corretamente.
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

    // O "Botão" que o usuário vê (parecido com um TextField)
    OutlinedTextField(
        value = selectedDate.toFormattedDateString(), // Converte Long para "dd/MM/yyyy"
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

// --- Função de Extensão (Helper) ---
// (Pode ser movida para um arquivo 'utils/Extensions.kt' futuramente)

@SuppressLint("SimpleDateFormat")
private fun Long?.toFormattedDateString(): String {
    if (this == null) return ""
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}