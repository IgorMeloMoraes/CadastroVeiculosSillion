package com.kingcreationslabs.sillioncadastroveiculosmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CadastroVeiculosManagerApp : Application() {

    // O Hilt fará a mágica dele nos bastidores.
    override fun onCreate() {
        super.onCreate()
        // Podemos usar este local futuramente para inicializar
        // outras bibliotecas, se necessário.
    }
}