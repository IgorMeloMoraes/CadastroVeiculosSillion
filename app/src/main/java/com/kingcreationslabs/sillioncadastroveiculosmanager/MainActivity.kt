package com.kingcreationslabs.sillioncadastroveiculosmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding // Import necessário
import androidx.compose.material3.Scaffold // Import necessário
import androidx.compose.runtime.Composable // Import necessário
import androidx.compose.ui.Modifier // Import necessário
import androidx.navigation.compose.rememberNavController // Import necessário
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.AppNavigation
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.AppBottomNavBar // 1. (NOVO) Importa o placeholder da Barra (vamos criar
// na Tarefa 3)
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.SillionCadastroVeiculosManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.currentBackStackEntryAsState // (NOVA IMPORTAÇÃO)
import androidx.compose.runtime.getValue // (NOVA IMPORTAÇÃO)
import androidx.navigation.NavDestination // (NOVA IMPORTAÇÃO)
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.Screen // (NOVA IMPORTAÇÃO)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 2. Chamamos um novo Composable principal
            MainScreenView()
        }
    }
}

// 3. (NOVO) Composable que contém a estrutura principal da UI
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

        // 1. O Scaffold começa aqui
        Scaffold(
            bottomBar = {
                if (isBottomBarVisible) {
                    AppBottomNavBar(navController = navController)
                }
            }
            // 2. Este '{' abre o "bloco de conteúdo" do Scaffold.
            //    'innerPadding' SÓ EXISTE dentro deste bloco.
        ) { innerPadding ->

            // 3. (A CORREÇÃO)
            // Chame o AppNavigation AQUI, DENTRO do Scaffold,
            // passando o 'innerPadding' para ele.
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )

            // 4. O Scaffold só fecha DEPOIS do AppNavigation
        }
    }
}


