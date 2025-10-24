package com.kingcreationslabs.sillioncadastroveiculosmanager.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.VehicleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao,
    private val firestore: FirebaseFirestore
) : VehicleRepository {

    companion object {
        private const val VEHICLES_COLLECTION = "vehicles"
    }

    // --- Lógica de Leitura (READ) ---
    override fun getVehiclesStream(): Flow<List<Vehicle>> {
        return vehicleDao.getVehiclesStream()
    }

    override fun getVehicleByPlate(plate: String): Flow<Vehicle?> {
        return vehicleDao.getVehicleByPlate(plate)
    }

    // --- Lógica de Escrita (CREATE / UPDATE) ---
    override suspend fun upsertVehicle(vehicle: Vehicle) {
        try {
            firestore.collection(VEHICLES_COLLECTION)
                .document(vehicle.plate)
                .set(vehicle)
                .await()

            vehicleDao.upsertVehicle(vehicle)
        } catch (e: Exception) {
            throw e
        }
    }

    // --- Lógica de Escrita (DELETE) ---
    override suspend fun deleteVehicle(vehicle: Vehicle) {
        try {
            firestore.collection(VEHICLES_COLLECTION)
                .document(vehicle.plate)
                .delete()
                .await()

            vehicleDao.deleteVehicle(vehicle)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun syncVehiclesFromFirestore() {
        try {
            //  Busca TODOS os documentos da coleção "vehicles" no Firestore
            val querySnapshot = firestore.collection(VEHICLES_COLLECTION)
                .get()
                .await()

            //  Converte os documentos recebidos em objetos Vehicle
            val vehiclesFromFirestore = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Vehicle::class.java)
            }

            if (vehiclesFromFirestore.isNotEmpty()) {
                vehiclesFromFirestore.forEach { vehicle ->
                    vehicleDao.upsertVehicle(vehicle)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}