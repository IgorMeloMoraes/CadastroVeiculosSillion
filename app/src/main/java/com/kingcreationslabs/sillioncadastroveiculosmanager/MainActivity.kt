package com.kingcreationslabs.sillioncadastroveiculosmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.SillionCadastroVeiculosManagerTheme
import dagger.hilt.android.AndroidEntryPoint // HILT
import androidx.compose.material3.Surface
import com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.navigation.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SillionCadastroVeiculosManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    // CHAME O NAVHOST AQUI
                    AppNavigation()
                }
                }
            }
        }
    }