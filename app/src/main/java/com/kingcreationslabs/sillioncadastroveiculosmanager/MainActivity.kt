package com.kingcreationslabs.sillioncadastroveiculosmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.AppNavigation
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.AppBottomNavBar
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.SillionCadastroVeiculosManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreenView()
        }
    }
}

@Composable
fun MainScreenView() {
    SillionCadastroVeiculosManagerTheme {
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val screensWithoutBottomBar = listOf(
            Screen.Welcome.route,
            Screen.AddVehicle.route,
            Screen.VehicleDetails.route
        )

        val isBottomBarVisible = currentDestination?.route !in screensWithoutBottomBar

        Scaffold(
            bottomBar = {
                if (isBottomBarVisible) {
                    AppBottomNavBar(navController = navController)
                }
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


