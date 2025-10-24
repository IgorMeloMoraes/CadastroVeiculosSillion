package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VehicleListScreen(
    onNavigateToAddVeiculos: () -> Unit // Função para navegar para outra tela
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tela de Lista de Veículos (WIP)")
    }
}