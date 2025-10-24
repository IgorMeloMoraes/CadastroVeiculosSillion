package com.kingcreationslabs.sillioncadastroveiculosmanager.data

// 1. (NOVAS IMPORTAÇÕES) Importamos os ícones que vamos usar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Representa os tipos de veículos suportados pelo app.
 *
 * 2. (MUDANÇA) O construtor agora também aceita um 'icon'.
 */
enum class TipoDeVeiculo(
    val nomeAmigavel: String,
    val icon: ImageVector // <-- Ícone associado
) {
    // 3. (MUDANÇA) Associamos cada tipo a um ícone importado
    CARRO(
        nomeAmigavel = "Carro",
        icon = Icons.Filled.DirectionsCar
    ),
    MOTO(
        nomeAmigavel = "Moto",
        icon = Icons.Filled.TwoWheeler
    ),
    CAMINHAO(
        nomeAmigavel = "Caminhão",
        icon = Icons.Filled.LocalShipping
    ),
    TRATOR(
        nomeAmigavel = "Trator",
        icon = Icons.Filled.Agriculture // Ícone de Trator
    ),
    OUTRO(
        nomeAmigavel = "Outro",
        icon = Icons.Filled.HelpOutline // Ícone genérico
    );

    // O 'companion object' (se existir) não precisa de mudanças.
    companion object {
        fun fromString(value: String?): TipoDeVeiculo {
            return entries.find { it.name == value } ?: OUTRO
        }
    }
}