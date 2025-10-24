package com.kingcreationslabs.sillioncadastroveiculosmanager.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Anotação que diz ao Hilt que isto é um Módulo
@InstallIn(SingletonComponent::class) //  Viverá enquanto o app viver (Singleton)
object FirebaseModule {

    //  Esta é a "receita" (provider)
    @Provides
    @Singleton //  Garante que só existirá UMA instância do Firestore no app
    fun provideFirebaseFirestore(): FirebaseFirestore {
        //  Usamos a biblioteca KTX (firebase-ktx) que
        //    adicionamos na Sprint 1 para pegar a instância
        //    padrão do Firestore.
        return Firebase.firestore
    }

    // (Futuramente, se precisássemos de Firebase Auth ou Storage,
    //  adicionaríamos mais funções @Provides aqui.)
}