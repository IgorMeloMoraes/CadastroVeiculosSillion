package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingcreationslabs.sillioncadastroveiculosmanager.R

@Composable
fun WelcomeScreen(
    onNavigateToMain: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF609cf3), // Cor de Cima: #609cf3
                        Color(0xFF3b4ca6)  // Cor de Baixo: #3b4ca6
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Espaço superior
            Spacer(modifier = Modifier.weight(1f))

            // O Logotipo
            Image(
                painter = painterResource(id = R.drawable.ic_logo_splash),
                contentDescription = "Logotipo do Aplicativo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // O Texto de Boas-Vindas (Título)
            Text(
                text = "Gerencie seus Veículos com Facilidade",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp
            )

            // 1. (MUDANÇA) Espaçamento entre os textos reduzido
            Spacer(modifier = Modifier.height(32.dp)) // <-- Era 16.dp

            // Texto (Subtítulo)
            Text(
                text = "Mantenha o controle de revisões, licenciamento e muito mais.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            // 2. (MUDANÇA) Espaço inferior reduzido para subir o botão
            Spacer(modifier = Modifier.weight(1f)) // <-- Era 2f

            // O Botão "Get Started"
            Button(
                onClick = onNavigateToMain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF3b4ca6)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "GET STARTED",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // 1. (A SOLUÇÃO) Adiciona um espaçador FIXO
            //    abaixo do botão para "levantá-lo".
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}