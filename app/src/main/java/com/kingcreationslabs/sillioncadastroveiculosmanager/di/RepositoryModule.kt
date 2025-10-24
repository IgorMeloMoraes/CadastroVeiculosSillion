package com.kingcreationslabs.sillioncadastroveiculosmanager.di

import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Diz ao Hilt que é um módulo
@InstallIn(SingletonComponent::class) //  Viverá enquanto o App viver
abstract class RepositoryModule { //  Precisa ser "abstract" para usar @Binds

    //  A anotação "@Binds" é a mais eficiente. Ela diz ao Hilt:
    //    "Quando alguém pedir um 'VehicleRepository' (a interface),
    //    forneça a ele um 'VehicleRepositoryImpl' (a implementação)."
    @Binds
    @Singleton //  Garante que só teremos um repositório no app
    abstract fun bindVehicleRepository(
        vehicleRepositoryImpl: VehicleRepositoryImpl
    ): VehicleRepository
}