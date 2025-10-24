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
    val isEnabled: Boolean = true
) {
    object Home : BottomNavItem(
        title = "Início",
        icon = Icons.Default.Home,
        route = Screen.VehicleList.route
    )

    object Add : BottomNavItem(
        title = "Adicionar",
        icon = Icons.Default.AddCircle,
        route = Screen.AddVehicle.route
    )

    object Profile : BottomNavItem(
        title = "Perfil",
        icon = Icons.Default.Person,
        route = "profile_screen",
        isEnabled = false
    )

    object Settings : BottomNavItem(
        title = "Config.",
        icon = Icons.Default.Settings,
        route = "settings_screen",
        isEnabled = false
    )
}