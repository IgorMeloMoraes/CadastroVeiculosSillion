package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    // Função de Inserir/Atualizar (Upsert)
    @Upsert
    suspend fun upsertVehicle(vehicle: Vehicle)

    // Função de Deletar
    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    // Função de Buscar Todos (A principal desta Sprint)
    @Query("SELECT * FROM vehicle_table ORDER BY plate ASC")
    fun getVehiclesStream(): Flow<List<Vehicle>>

    //  Função de Buscar Um
    @Query("SELECT * FROM vehicle_table WHERE plate = :plate")
    fun getVehicleByPlate(plate: String): Flow<Vehicle?>
}