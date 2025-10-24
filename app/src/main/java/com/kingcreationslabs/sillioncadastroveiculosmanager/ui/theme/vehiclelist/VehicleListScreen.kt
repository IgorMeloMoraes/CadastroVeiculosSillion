package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// A assinatura da função agora recebe o ViewModel como um parâmetro padrão.
// O Hilt (via hiltViewModel()) cuidará de injetá-lo automaticamente.
@Composable
fun VehicleListScreen(
    onNavigateToAddVehicle: () -> Unit,
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    // Coletamos o UiState do ViewModel de forma segura (lifecycle-aware)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //  O Scaffold é o layout base do Material. Ele nos dá
    //    slots para a barra superior, conteúdo principal e o FAB.
    Scaffold(
        floatingActionButton = {
            //  Este é o botão "+" que chama a função de navegação
            //    que recebemos do nosso AppNavigation (Sprint 1).
            FloatingActionButton(onClick = onNavigateToAddVehicle) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Veículo"
                )
            }
        }
    ) { paddingValues -> // paddingValues contém o espaço usado pelo FAB/AppBar

        //  Chamamos um Composable separado para o conteúdo principal,
        //    passando o UiState e o padding.
        VehicleListContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState
        )
    }
}

//  (Boa Prática) Separamos o "Conteúdo" da "Tela"
@Composable
private fun VehicleListContent(
    modifier: Modifier = Modifier,
    uiState: VehicleListUiState
) {
    //  Usamos um Box para centralizar o Loading ou Erro
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        //  Se estiver carregando, mostre um indicador de progresso
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
        //  Se houver um erro, mostre a mensagem
        else if (uiState.error != null) {
            Text(
                text = "Erro: ${uiState.error}",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
        //  Se a lista estiver vazia (e não carregando), mostre um aviso
        else if (uiState.vehicles.isEmpty()) {
            Text(
                text = "Nenhum veículo cadastrado.\nClique no botão '+' para adicionar.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        //  (O principal) Se tivermos veículos, mostre a lista
        else {
            // LazyColumn é o "RecyclerView" do Compose.
            // Ele só renderiza os itens que estão visíveis na tela.
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // "items" é a função que constrói a lista.
                // "key" é importante para o Compose otimizar a performance.
                items(uiState.vehicles, key = { it.plate }) { vehicle ->
                    // 12. Usamos o Composable de "célula" que criamos
                    VehicleListItem(
                        vehicle = vehicle,
                        onClick = {
                            // Deixaremos isso para uma sprint futura (Detalhes do Veículo)
                        }
                    )
                }
            }
        }
    }
}