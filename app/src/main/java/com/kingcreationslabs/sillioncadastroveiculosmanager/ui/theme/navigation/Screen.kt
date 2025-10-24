package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

sealed class Screen(val route: String) {

    object Welcome : Screen("welcome_screen")
    object VehicleList : Screen("vehicle_list_screen")
    object AddVehicle : Screen("add_vehicle_screen")
    object VehicleDetails : Screen("vehicle_details_screen/{plate}") {

        fun createRoute(plate: String) = "vehicle_details_screen/$plate"
    }
}