package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

// --- IMPORTS CORRETOS PARA A API NOVA ---
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox // <-- API NOVA (Correta)
import androidx.compose.material3.SwipeToDismissBoxState // <-- API NOVA (Correta)
import androidx.compose.material3.SwipeToDismissBoxValue // <-- API NOVA (Correta)
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState // <-- API NOVA (Correta)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle // <-- Import do seu Modelo
// (Verifique se o 'data' está correto, ou se é 'model')

// 1. OptIn para a API experimental
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    onNavigateToAddVehicle: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efeito para "escutar" por mensagens (CORRIGIDO com 'val message')
    LaunchedEffect(uiState.userMessage) {
        val message = uiState.userMessage
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            viewModel.userMessageShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddVehicle) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Veículo")
            }
        }
    ) { paddingValues ->
        VehicleListContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onDeleteVehicle = viewModel::deleteVehicle,
            // 2. (MUDANÇA) Passe a nova função para o Content
            onNavigateToDetails = onNavigateToDetails

        )
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
        // ... (isLoading, error, isEmpty... tudo igual)
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(
                text = "Erro: ${uiState.error}",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        } else if (uiState.vehicles.isEmpty()) {
            Text(
                text = "Nenhum veículo cadastrado.\nClique no botão '+' para adicionar.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        // --- CONTEÚDO DA LISTA COM A API CORRETA ---
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.vehicles, key = { it.plate }) { vehicle ->

                    // 2. (MUDANÇA) Usando 'rememberSwipeToDismissBoxState'
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissValue ->
                            // 3. (MUDANÇA) Usando 'SwipeToDismissBoxValue.EndToStart'
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                onDeleteVehicle(vehicle)
                                return@rememberSwipeToDismissBoxState true
                            }
                            return@rememberSwipeToDismissBoxState false
                        }
                    )

                    // 4. (MUDANÇA) Usando 'SwipeToDismissBox'
                    SwipeToDismissBox(
                        state = dismissState,
                        // 5. O fundo agora é 'backgroundContent'
                        backgroundContent = {
                            SwipeToDeleteBackground(dismissState = dismissState)
                        },
                        // Habilita apenas o deslize da direita para a esquerda
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false
                    ) {
                        // Este é o conteúdo principal que fica visível
                        VehicleListItem(
                            vehicle = vehicle,
                            onClick = {
                                // Tarefa 2 (Update)
                               // 5. (MUDANÇA) Chama a função de navegação
                                        //    passando a placa do veículo!
                                        onNavigateToDetails(it.plate)
                            }
                        )
                    }
                }
            }
        }
    }
}

// 6. (MUDANÇA) Composable auxiliar para o fundo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteBackground(dismissState: SwipeToDismissBoxState) { // <-- MUDANÇA (SwipeToDismissBoxState)
    val color by animateColorAsState(
        // 7. (MUDANÇA) Acesso ao 'targetValue'
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
            .background(color)
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