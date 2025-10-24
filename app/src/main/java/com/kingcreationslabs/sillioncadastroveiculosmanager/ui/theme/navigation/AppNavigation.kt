package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist.VehicleListScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails.VehicleDetailsScreen // 1. IMPORTE A NOVA TELA
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // (Ou 'controladorNavegacao', o que você usou)

    NavHost(
        navController = navController,
        // startDestination = Screen.VehicleList.route
        // 2. (MUDANÇA CRÍTICA) O app agora começa na tela de Boas-Vindas
        startDestination = Screen.Welcome.route
    ) {

        // 3. (NOVA ROTA) Adiciona o composable da tela de Boas-Vindas
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToMain = {
                    // 4. (LÓGICA DE NAVEGAÇÃO)
                    // Quando o usuário clicar em "Get Started",
                    // navegamos para a lista...
                    navController.navigate(Screen.VehicleList.route) {
                        // ...e removemos a WelcomeScreen da pilha.
                        // Isso impede que o usuário clique em "Voltar"
                        // e veja a tela de boas-vindas novamente.
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --- ROTA DA LISTA (MODIFICADA) ---
        composable(route = Screen.VehicleList.route) {
            VehicleListScreen(
                onNavigateToAddVehicle = {
                    navController.navigate(Screen.AddVehicle.route)
                },
                // 2. (MUDANÇA) Passa uma nova função lambda
                //    para a tela de lista.
                onNavigateToDetails = { plate ->
                    // 3. (MUDANÇA) Quando chamada, navega para a
                    //    rota de detalhes usando nossa função auxiliar.
                    navController.navigate(Screen.VehicleDetails.createRoute(plate))
                }
            )
        }

        // --- ROTA DE ADICIONAR (Sem mudanças) ---
        composable(route = Screen.AddVehicle.route) {
            AddVehicleScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // --- ROTA DE DETALHES (CORRIGIDA) ---
        composable(
            route = Screen.VehicleDetails.route,
            // 1. A definição do argumento DEVE continuar aqui
            arguments = listOf(
                navArgument("plate") { type = NavType.StringType }
            )
        ) {
            // 2. NÃO precisamos mais extrair a 'plate' aqui.
            //    O VehicleDetailsViewModel fará isso sozinho
            //    usando o Hilt e o SavedStateHandle.

            // 3. Apenas chame a tela.
            VehicleDetailsScreen(
                onBack = {
                    navController.popBackStack()
                }
                // O 'viewModel = hiltViewModel()' será
                // chamado automaticamente dentro da tela.
            )
        }
    }
}