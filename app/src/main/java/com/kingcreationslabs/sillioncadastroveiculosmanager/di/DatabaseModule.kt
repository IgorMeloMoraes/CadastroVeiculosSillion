package com.kingcreationslabs.sillioncadastroveiculosmanager.di

import android.content.Context
import androidx.room.Room
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.VehicleDao
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.VehicleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Anotação que diz ao Hilt que isto é um Módulo
@InstallIn(SingletonComponent::class) //  As "fábricas" aqui viverão enquanto o app viver
object DatabaseModule {

    //  Esta função "fabrica" o VehicleDatabase
    @Provides
    @Singleton // Garante que só existirá UMA instância do banco no app inteiro
    fun provideVehicleDatabase(
        @ApplicationContext context: Context //  O Hilt nos dá o Contexto do App
    ): VehicleDatabase {
        return Room.databaseBuilder(
            context,
            VehicleDatabase::class.java,
            VehicleDatabase.DATABASE_NAME // Usando o nome que definimos
        )
            .fallbackToDestructiveMigration() // Se atualizarmos a versão, ele apaga o banco (ok para testes)
            .build()
    }

    //  Esta função "fabrica" o VehicleDao
    @Provides
    @Singleton //  Garante UMA instância do DAO
    fun provideVehicleDao(database: VehicleDatabase): VehicleDao {
        // 8. O Hilt vê que esta função precisa de um "VehicleDatabase".
        // Ele olha para a função acima (provideVehicleDatabase),
        // "fabrica" o banco e o passa para cá.
        return database.vehicleDao()
    }
}