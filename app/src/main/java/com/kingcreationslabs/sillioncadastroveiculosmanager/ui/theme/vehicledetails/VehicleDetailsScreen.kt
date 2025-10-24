package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails

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
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components.TypeSelectionDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    onBack: () -> Unit,
    viewModel: VehicleDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = uiState.plate,
                onValueChange = { },
                label = { Text("Placa (Não pode ser editada)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true,

                isError = uiState.isPlateError,
                supportingText = {
                    if (uiState.isPlateError) {
                        Text(
                            text = "Formato: AAA-1234 ou AAA1B23",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )


            OutlinedTextField(
                value = uiState.manufacturer,
                onValueChange = viewModel::onManufacturerChange,
                label = { Text("Fabricante*") },
            )

            OutlinedTextField(
                value = uiState.model,
                onValueChange = viewModel::onModelChange,
                label = { Text("Modelo*") },
            )

            TypeSelectionDropdown(
                selectedType = uiState.type,
                onTypeSelected = viewModel::onTypeChanged,
                modifier = Modifier.fillMaxWidth()
            )

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

                label = { Text("Km") },
            )

            OutlinedTextField(
                value = uiState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                label = { Text("Prorietário") },
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = viewModel::onNotesChange,
                label = { Text("Notas") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Seletores de Data  ---
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

            // --- Botão de Salvar  ---
            Button(
                onClick = viewModel::saveVehicle, // Chama o 'saveVehicle' do ViewModel de Detalhes
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = !uiState.isSaving
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(

                    )
                } else {
                    Text("SALVAR ALTERAÇÕES")
                }
            }
        }
    }
}


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