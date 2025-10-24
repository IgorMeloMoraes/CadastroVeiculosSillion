package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle

@Composable
fun VehicleListItem(
    vehicle: Vehicle,
    onClick: (Vehicle) -> Unit, // Função para o futuro (quando clicarmos para ver detalhes)
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(vehicle) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Exibe a Placa (ID)
            Text(
                text = vehicle.plate,
                style = MaterialTheme.typography.titleLarge
            )
            // Exibe Modelo e Fabricante
            Text(
                text = "${vehicle.manufacturer} ${vehicle.model} (${vehicle.modelYear})",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}