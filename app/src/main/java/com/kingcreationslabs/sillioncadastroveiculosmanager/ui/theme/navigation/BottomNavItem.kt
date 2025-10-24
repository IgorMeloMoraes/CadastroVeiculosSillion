package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Define os itens (botões) que aparecerão na
 * nossa Barra de Navegação Inferior (BottomNavigationBar).
 *
 * Cada item tem um título, um ícone, a rota para onde ele navega
 * e um 'isEnabled' para os nossos itens "figurantes".
 */
sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val isEnabled: Boolean = true // Por padrão, todos são habilitados
) {
    // 1. HOME (Nossa tela de lista de veículos)
    //    Ela usa a rota que já definimos no 'Screen.kt'
    object Home : BottomNavItem(
        title = "Início",
        icon = Icons.Default.Home,
        route = Screen.VehicleList.route
    )

    // 2. ADICIONAR (Nossa tela de adicionar veículo)
    //    Também usa uma rota do 'Screen.kt'
    object Add : BottomNavItem(
        title = "Adicionar",
        icon = Icons.Default.AddCircle,
        route = Screen.AddVehicle.route
    )

    // 3. PERFIL (Item figurante, como você sugeriu)
    object Profile : BottomNavItem(
        title = "Perfil",
        icon = Icons.Default.Person,
        route = "profile_screen", // Rota "fantasma"
        isEnabled = false // <-- DESABILITADO
    )

    // 4. CONFIGURAÇÕES (Item figurante, como você sugeriu)
    object Settings : BottomNavItem(
        title = "Config.",
        icon = Icons.Default.Settings,
        route = "settings_screen", // Rota "fantasma"
        isEnabled = false // <-- DESABILITADO
    )
}