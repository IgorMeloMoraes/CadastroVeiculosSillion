package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components

// --- GARANTA ESTAS IMPORTAÇÕES ---
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier // <- Importação padrão do Modifier
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo
// ^-- (Confirme o caminho para seu Enum 'TipoDeVeiculo')

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelectionDropdown(
    selectedType: TipoDeVeiculo,
    onTypeSelected: (TipoDeVeiculo) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = modifier
    ) { // <-- Início do Escopo

        OutlinedTextField(
            value = selectedType.nomeAmigavel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de Veículo*") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            // 1. (A CORREÇÃO)
            // Chamamos o .menuAnchor() primeiro (fornecido pelo escopo)
            // e DEPOIS o .fillMaxWidth().
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            TipoDeVeiculo.entries.forEach { tipo ->
                DropdownMenuItem(
                    text = { Text(tipo.nomeAmigavel) },
                    onClick = {
                        onTypeSelected(tipo)
                        isExpanded = false
                    }
                )
            }
        }
    } // <-- Fim do Escopo
}