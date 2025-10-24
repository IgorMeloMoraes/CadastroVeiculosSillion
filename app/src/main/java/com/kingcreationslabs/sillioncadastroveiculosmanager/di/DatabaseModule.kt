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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //  Esta função "fabrica" o VehicleDatabase
    @Provides
    @Singleton // Garante que só existirá UMA instância do banco no app inteiro
    fun provideVehicleDatabase(
        @ApplicationContext context: Context
    ): VehicleDatabase {
        return Room.databaseBuilder(
            context,
            VehicleDatabase::class.java,
            VehicleDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addMigrations(VehicleDatabase.MIGRATION_1_2)
            .build()
    }

    //  Esta função "fabrica" o VehicleDao
    @Provides
    @Singleton
    fun provideVehicleDao(database: VehicleDatabase): VehicleDao {
        return database.vehicleDao()
    }
}