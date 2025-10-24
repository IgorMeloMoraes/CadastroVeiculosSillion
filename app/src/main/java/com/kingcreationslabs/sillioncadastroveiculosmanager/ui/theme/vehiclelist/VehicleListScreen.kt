package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

// --- IMPORTS COMPLETOS E CORRIGIDOS ---
import androidx.compose.animation.animateColorAsState // <-- PARA ANIMAÇÃO DE COR
import androidx.compose.animation.core.animateFloatAsState // <-- PARA ANIMAÇÃO DE ESCALA
import androidx.compose.foundation.background // <-- PARA MODIFICADOR DE FUNDO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons // <-- PARA ACESSAR ÍCONES
import androidx.compose.material.icons.filled.Delete // <-- ÍCONE ESPECÍFICO
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon // <-- COMPOSABLE DE ÍCONE
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale // <-- PARA MODIFICADOR DE ESCALA
import androidx.compose.ui.graphics.Color // <-- PARA CORES
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp // <-- PARA UNIDADES (Density-independent Pixels)
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
// ^-- (Confirme o caminho para seu Modelo)

// --- IMPORTS DOS SEUS COMPONENTES ---
// (Certifique-se que os caminhos estão corretos para onde você os salvou)
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.components.VehicleFilterBar
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist.VehicleListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) { /* ... (igual) ... */ }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("Meus Veículos") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            VehicleFilterBar(
                selectedType = uiState.selectedType,
                onFilterSelected = viewModel::onFilterChanged,
                modifier = Modifier.fillMaxWidth()
            )

            VehicleListContent(
                uiState = uiState,
                onDeleteVehicle = viewModel::deleteVehicle,
                onNavigateToDetails = onNavigateToDetails
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VehicleListContent(
    modifier: Modifier = Modifier,
    uiState: VehicleListUiState,
    onDeleteVehicle: (Vehicle) -> Unit,
    onNavigateToDetails: (String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading && uiState.vehicles.isEmpty()) { /* ... (igual) ... */ }
        else if (uiState.error != null) { /* ... (igual) ... */ }
        else if (uiState.vehicles.isEmpty()) { /* ... (igual) ... */ }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.vehicles, key = { it.plate }) { vehicle ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        // *** CORREÇÃO DO ARGUMENT TYPE MISMATCH ***
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                onDeleteVehicle(vehicle)
                                true // <-- RETORNA Boolean
                            } else {
                                false // <-- RETORNA Boolean
                            }
                        }
                        // *** FIM DA CORREÇÃO ***
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = { SwipeToDeleteBackground(dismissState = dismissState) }, // Chamada ao Helper
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false
                    ) {
                        VehicleListItem( // Chamada ao Helper
                            vehicle = vehicle,
                            onClick = { onNavigateToDetails(it.plate) }
                        )
                    }
                }
            }
        }
    }
}

// *** CÓDIGO DO SWIPETODELETEBACKGROUND (MANTIDO AQUI POR ENQUANTO) ***
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteBackground(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
            else -> Color.Transparent
        }, label = "background_color_animation"
    )

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1.1f else 0.8f,
        label = "icon_scale_animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color) // <-- CORRIGIDO (estava faltando .background)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Excluir",
            modifier = Modifier.scale(scale),
            tint = Color.White
        )
    }
}
// *** FIM DO SWIPETODELETEBACKGROUND ***