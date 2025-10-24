package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

// Sealed class para definir as rotas da nossa aplicação
sealed class Screen(val route: String) {

    // Esta será a nossa nova tela de entrada.
    object Welcome : Screen("welcome_screen")
    object VehicleList : Screen("vehicle_list_screen")
    object AddVehicle : Screen("add_vehicle_screen")
    // Futuramente: object VehicleDetails : Screen("vehicle_details_screen/{plate}")
    // 1. ADICIONE ESTE NOVO OBJETO
    object VehicleDetails : Screen("vehicle_details_screen/{plate}") {

        // 2. (BOA PRÁTICA) Criar uma função auxiliar para
        //    construir a rota. Isso evita "strings mágicas"
        //    no resto do app.
        fun createRoute(plate: String) = "vehicle_details_screen/$plate"
    }
}