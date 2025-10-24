package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // 1. (NOVA IMPORTAÇÃO)
import androidx.navigation.NavHostController // 2. (NOVA IMPORTAÇÃO)
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// import androidx.navigation.compose.rememberNavController // 3. (REMOVIDO) Não precisamos mais disto
import androidx.navigation.navArgument
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist.VehicleListScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails.VehicleDetailsScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.welcome.WelcomeScreen

// 4. (MUDANÇA CRÍTICA) A função agora aceita parâmetros
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // 5. (REMOVIDO) A linha 'val navController = rememberNavController()'
    //    foi apagada daqui, pois ele vem da MainActivity.

    // 6. (MUDANÇA) O 'modifier' recebido é aplicado ao NavHost.
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier // <-- APLICA O MODIFIER (com o padding) AQUI
    ) {

        // --- O RESTANTE DO ARQUIVO (AS ROTAS) É IDÊNTICO ---

        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.VehicleList.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.VehicleList.route) {
            VehicleListScreen(
               /* onNavigateToAddVehicle = {
                    // Esta chamada ainda existe, mas vamos removê-la
                    // na próxima tarefa (quando removermos o FAB)
                    navController.navigate(Screen.AddVehicle.route)
                },*/
                onNavigateToDetails = { plate ->
                    navController.navigate(Screen.VehicleDetails.createRoute(plate))
                }
            )
        }

        composable(route = Screen.AddVehicle.route) {
            AddVehicleScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.VehicleDetails.route,
            arguments = listOf(
                navArgument("plate") { type = NavType.StringType }
            )
        ) {
            VehicleDetailsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}