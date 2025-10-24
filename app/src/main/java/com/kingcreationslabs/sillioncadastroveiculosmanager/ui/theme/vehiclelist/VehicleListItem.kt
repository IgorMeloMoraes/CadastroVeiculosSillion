package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

// --- IMPORTS NECESSÁRIOS ---
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape // Para o fundo do ícone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle // <-- SEU MODELO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// (Se a função toFormattedDateString já existe em outro lugar,
//  importe-a. Senão, pode mantê-la aqui ou movê-la para um
//  arquivo de utils)
@SuppressLint("SimpleDateFormat")
private fun Date?.toFormattedDateStringOrPlaceholder(placeholder: String = "N/D"): String {
    if (this == null) return placeholder
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}


@Composable
fun VehicleListItem(
    vehicle: Vehicle,
    onClick: (Vehicle) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(vehicle) }
            .padding(horizontal = 8.dp, vertical = 4.dp), // Padding menor entre os cards
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Fundo do Card
    ) {
        // 1. Linha principal para organizar os elementos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding interno do Card
            verticalAlignment = Alignment.CenterVertically // Alinha verticalmente
        ) {
            // --- Elemento 1 (Esquerda): Ícone do Tipo ---
            Box(
                modifier = Modifier
                    .size(40.dp) // Tamanho do círculo de fundo
                    .clip(CircleShape) // Faz ser um círculo
                    .background(MaterialTheme.colorScheme.primaryContainer), // Cor de fundo
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vehicle.type.icon, // Pega o ícone do Enum!
                    contentDescription = vehicle.type.nomeAmigavel,
                    modifier = Modifier.size(24.dp), // Tamanho do ícone
                    tint = MaterialTheme.colorScheme.onPrimaryContainer // Cor do ícone
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Espaço entre ícone e texto

            // --- Elemento 2 (Meio): Coluna com Modelo e Data ---
            Column(
                modifier = Modifier.weight(1f) // Ocupa o espaço restante
            ) {
                // Modelo (Texto Principal)
                Text(
                    text = vehicle.model.ifBlank { "Sem Modelo" }, // Placeholder se vazio
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // Adiciona "..." se for muito longo
                )
                // Data da Próxima Revisão (Texto Secundário)
                Text(
                    text = "Próx. Revisão: ${vehicle.nextRevision.toFormattedDateStringOrPlaceholder()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Cor mais suave
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Espaço entre textos e placa

            // --- Elemento 3 (Direita): Placa ---
            Text(
                text = vehicle.plate,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary // Cor de destaque
            )
        }
    }
}