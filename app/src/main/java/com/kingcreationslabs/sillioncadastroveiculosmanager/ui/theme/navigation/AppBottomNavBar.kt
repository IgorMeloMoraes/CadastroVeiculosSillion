package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Este é o Composable real para a nossa Barra de Navegação Inferior.
 */
@Composable
fun AppBottomNavBar(navController: NavHostController) {

    // 1. Cria a lista de itens da barra usando o nosso sealed class
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    )

    // 2. O Composable do Material 3 para a barra de navegação
    NavigationBar {

        // 3. (LÓGICA DE SELEÇÃO)
        // Observamos a pilha de navegação para saber qual é a rota atual
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // 4. Itera sobre a nossa lista de itens
        items.forEach { item ->

            // 5. O Composable para cada item individual na barra
            NavigationBarItem(
                // 6. (LÓGICA DE SELEÇÃO)
                // O item é "selecionado" se a sua rota for a rota atual
                selected = (currentRoute == item.route),

                // 7. (LÓGICA DE CLIQUE)
                onClick = {
                    // Só navega se o item estiver habilitado
                    if (item.isEnabled) {
                        navController.navigate(item.route) {
                            // Lógica de navegação padrão para BottomNav:
                            // Evita empilhar a mesma tela várias vezes
                            launchSingleTop = true
                            // Restaura o estado da tela ao voltar
                            restoreState = true
                            // "Reseta" a pilha para o início (Home)
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                },

                // 8. O ícone do item (ex: Icons.Default.Home)
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                // 9. O rótulo (título) do item (ex: "Início")
                label = {
                    Text(text = item.title)
                },

                // 10. (LÓGICA DE HABILITAÇÃO)
                // Desativa o clique e muda a cor dos itens "figurantes"
                enabled = item.isEnabled
            )
        }
    }
}