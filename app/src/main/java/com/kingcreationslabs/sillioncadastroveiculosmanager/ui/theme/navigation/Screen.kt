package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

// Sealed class para definir as rotas da nossa aplicação
sealed class Screen(val route: String) {
    object VehicleList : Screen("vehicle_list_screen")
    object AddVehicle : Screen("add_vehicle_screen")
    // Futuramente: object VehicleDetails : Screen("vehicle_details_screen/{plate}")
}