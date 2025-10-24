package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao // Anotação que diz ao Room que esta é uma interface DAO
interface VehicleDao {

    // Função de Inserir/Atualizar (Upsert)
    // "@Upsert" é uma anotação moderna do Room. Ela significa:
    // "Se o veículo (baseado na @PrimaryKey 'placa') já existir, ATUALIZE-o.
    // Se não existir, INSIRA-o."
    // É perfeito para o nosso CRUD, pois simplifica o Create e o Update.
    // "suspend" significa que esta função deve ser chamada de uma Coroutine.
    @Upsert
    suspend fun upsertVehicle(vehicle: Vehicle)


    // Função de Deletar
    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    // Função de Buscar Todos (A principal desta Sprint)
    // "@Query" permite escrever SQL.
    // O mais importante: ela retorna um "Flow<List<Vehicle>>".
    // "Flow" é um fluxo de dados. Isso significa que, se qualquer dado
    // na "vehicle_table" mudar, o Room automaticamente emitirá uma
    // nova lista para quem estiver "ouvindo" (nosso ViewModel).
    // Isso cumpre o requisito de "LiveData ou Flow".
    @Query("SELECT * FROM vehicle_table ORDER BY plate ASC")
    fun getVehiclesStream(): Flow<List<Vehicle>>

    // 5. Função de Buscar Um (Usaremos no futuro)
    @Query("SELECT * FROM vehicle_table WHERE plate = :plate")
    fun getVehicleByPlate(plate: String): Flow<Vehicle?>

}