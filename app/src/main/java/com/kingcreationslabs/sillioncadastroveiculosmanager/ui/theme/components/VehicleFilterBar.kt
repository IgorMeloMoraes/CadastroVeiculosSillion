package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip // Usaremos o FilterChip para o estilo
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo
// ^-- (Confirme o caminho para seu Enum 'TipoDeVeiculo')

/**
 * Um Composable que exibe uma barra horizontal de filtros
 * baseados nos tipos de 'TipoDeVeiculo'.
 */
@OptIn(ExperimentalMaterial3Api::class) // FilterChip é experimental
@Composable
fun VehicleFilterBar(
    selectedType: TipoDeVeiculo?, // O tipo atualmente selecionado (null = Todos)
    onFilterSelected: (TipoDeVeiculo?) -> Unit, // Função chamada ao clicar
    modifier: Modifier = Modifier
) {
    // 1. Cria a lista de opções de filtro.
    //    Começamos com 'null' (representando "Todos") e depois
    //    adicionamos todos os tipos do nosso Enum.
    val filterOptions = listOf<TipoDeVeiculo?>(null) + TipoDeVeiculo.entries

    // 2. LazyRow para a lista horizontal
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp), // Espaçamento nas laterais
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaço entre os chips
    ) {
        items(filterOptions) { type ->
            // 3. O texto a ser exibido no Chip
            val label = type?.nomeAmigavel ?: "Todos" // Se for null, mostra "Todos"

            // 4. O FilterChip
            FilterChip(
                selected = (selectedType == type), // O chip está selecionado se for igual ao 'selectedType'
                onClick = { onFilterSelected(type) }, // Chama a função do ViewModel
                label = { Text(label) }
            )
        }
    }
}