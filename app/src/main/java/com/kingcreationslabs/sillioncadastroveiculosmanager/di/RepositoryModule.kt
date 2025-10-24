package com.kingcreationslabs.sillioncadastroveiculosmanager.di

import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepository
import com.kingcreationslabs.sillioncadastroveiculosmanager.repository.VehicleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //  Viverá enquanto o App viver
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(
        vehicleRepositoryImpl: VehicleRepositoryImpl
    ): VehicleRepository
}