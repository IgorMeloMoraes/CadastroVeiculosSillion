package com.kingcreationslabs.sillioncadastroveiculosmanager.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- ESQUEMA DE CORES CLARO (ATUALIZADO) ---
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue, // Azul principal
    secondary = AccentGreen, // Verde secundário/acento
    tertiary = Pink40, // Pode manter ou ajustar

    // --- CORES DE FUNDO E SUPERFÍCIE ---
    background = LightGrayBackground, // Fundo principal das telas
    surface = Color.White, // Fundo dos Cards, AppBars, etc. (Branco contrasta bem com F6F6F6)

    // --- CORES "SOBRE" (Textos e Ícones) ---
    onPrimary = Color.White, // Texto sobre fundo primário (azul)
    onSecondary = Color.Black, // Texto sobre fundo secundário (verde)
    onTertiary = Color.White,
    onBackground = CardTextColor, // Cor principal do texto sobre o fundo F6F6F6
    onSurface = CardTextColor, // Cor principal do texto sobre Cards brancos
    onSurfaceVariant = CardTextColor.copy(alpha = 0.7f) // Cor secundária (mais clara) para textos/ícones

    /* Outras cores personalizáveis:
    primaryContainer = ...,
    onPrimaryContainer = ...,
    secondaryContainer = ...,
    onSecondaryContainer = ...,
    tertiaryContainer = ...,
    onTertiaryContainer = ...,
    error = ...,
    onError = ...,
    errorContainer = ...,
    onErrorContainer = ...,
    outline = ...,
    surfaceVariant = ..., // Fundo de componentes como Chips
    scrim = ...
    */
)

// --- ESQUEMA DE CORES ESCURO (Pode manter ou ajustar se quiser suportar Dark Mode) ---
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    // Ajuste as cores de fundo e texto para o modo escuro, se desejar
    // background = Color(0xFF1C1B1F),
    // surface = Color(0xFF1C1B1F),
    // onPrimary = Color(0xFF381E72),
    // ...
)

@Composable
fun SillionCadastroVeiculosManagerTheme( // <-- Seu nome de tema
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Permite cores dinâmicas no Android 12+ (opcional)
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme // Usaremos o nosso LightColorScheme atualizado
    }

    // ... (Código do SideEffect para status bar - não precisa mudar)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Cor da Status Bar
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // (Do seu Typography.kt)
        content = content
    )
}