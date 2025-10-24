package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.addvehicle.AddVehicleScreen
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.vehiclelist.VehicleListScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.VehicleList.route // (Screen.kt podemos manter em inglês, pois é uma classe de "infra")
    ) {

        // Rota para a Lista de Veículos
        composable(route = Screen.VehicleList.route) {
            VehicleListScreen(
                onNavigateToAddVeiculos = {
                    navController.navigate(Screen.AddVehicle.route)
                }
            )
        }

        // Rota para Adicionar Veículo
        composable(route = Screen.AddVehicle.route) {
            AddVehicleScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}