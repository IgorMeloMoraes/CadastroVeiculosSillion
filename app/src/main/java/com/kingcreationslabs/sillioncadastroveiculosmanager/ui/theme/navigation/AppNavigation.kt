package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist.VehicleListScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehicledetails.VehicleDetailsScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.welcome.WelcomeScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {

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