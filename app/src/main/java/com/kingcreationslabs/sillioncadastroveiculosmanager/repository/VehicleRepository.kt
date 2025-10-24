package com.kingcreationslabs.sillioncadastroveiculosmanager.repository

import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    // (Read) Retorna o fluxo de dados do Room
    fun getVehiclesStream(): Flow<List<Vehicle>>

    // (Read) Retorna um veículo específico pelo ID (placa)
    fun getVehicleByPlate(plate: String): Flow<Vehicle?>

    // (Create / Update)
    suspend fun upsertVehicle(vehicle: Vehicle)

    // (Delete)
    suspend fun deleteVehicle(vehicle: Vehicle)
}