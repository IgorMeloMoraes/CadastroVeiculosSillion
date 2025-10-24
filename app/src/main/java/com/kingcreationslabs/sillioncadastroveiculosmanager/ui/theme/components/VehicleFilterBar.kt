package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.TipoDeVeiculo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleFilterBar(
    selectedType: TipoDeVeiculo?,
    onFilterSelected: (TipoDeVeiculo?) -> Unit,
    modifier: Modifier = Modifier
) {

    val filterOptions = listOf<TipoDeVeiculo?>(null) + TipoDeVeiculo.entries

    // LazyRow para a lista horizontal
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filterOptions) { type ->

            val label = type?.nomeAmigavel ?: "Todos"

            FilterChip(
                selected = (selectedType == type),
                onClick = { onFilterSelected(type) },
                label = { Text(label) }
            )
        }
    }
}