package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AddVehicleScreen(
    onBack: () -> Unit // Função para navegar de volta
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tela de Adicionar Veículo (WIP)")
    }
}