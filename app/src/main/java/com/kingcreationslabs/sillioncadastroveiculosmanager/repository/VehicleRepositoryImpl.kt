package com.kingcreationslabs.sillioncadastroveiculosmanager.repository

import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.VehicleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// A classe "implementa" a interface VehicleRepository
// Usamos "@Inject constructor" para dizer ao Hilt:
//    "Para construir um VehicleRepositoryImpl, você PRECISA me
//    'injetar' (fornecer) um VehicleDao."
//    O Hilt sabe como fazer isso graças ao DatabaseModule da Tarefa 1.
class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao
) : VehicleRepository {

    //  Agora, apenas implementamos os métodos da interface
    //    simplesmente "repassando" a chamada para o DAO.
    //    (Nesta Sprint, o trabalho é fácil. Na Sprint 3,
    //     adicionaremos a lógica do Firebase aqui).

    override fun getVehiclesStream(): Flow<List<Vehicle>> {
        return vehicleDao.getVehiclesStream()
    }

    override fun getVehicleByPlate(plate: String): Flow<Vehicle?> {
        return vehicleDao.getVehicleByPlate(plate)
    }

    override suspend fun upsertVehicle(vehicle: Vehicle) {
        vehicleDao.upsertVehicle(vehicle)
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        vehicleDao.deleteVehicle(vehicle)
    }
}