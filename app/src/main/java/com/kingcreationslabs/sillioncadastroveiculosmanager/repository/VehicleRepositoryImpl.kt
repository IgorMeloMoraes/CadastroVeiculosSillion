package com.kingcreationslabs.sillioncadastroveiculosmanager.repository

import com.google.firebase.firestore.FirebaseFirestore // <-- IMPORTAR
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.Vehicle
import com.kingcreationslabs.sillioncadastroveiculosmanager.data.VehicleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await //  IMPORTAR (para usar .await() em Tasks do Firebase)
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao,
    private val firestore: FirebaseFirestore //  INJETAR O FIRESTORE AQUI
) : VehicleRepository {

    //  Definir o nome da nossa "tabela" (coleção) no Firestore
    companion object {
        private const val VEHICLES_COLLECTION = "vehicles"
    }

    // --- Lógica de Leitura (READ) ---
    // (Estas funções não mudam. Elas AINDA leem apenas do Room)
    override fun getVehiclesStream(): Flow<List<Vehicle>> {
        return vehicleDao.getVehiclesStream()
    }

    override fun getVehicleByPlate(plate: String): Flow<Vehicle?> {
        return vehicleDao.getVehicleByPlate(plate)
    }

    // --- Lógica de Escrita (CREATE / UPDATE) ---
    //  ATUALIZAR ESTA FUNÇÃO
    override suspend fun upsertVehicle(vehicle: Vehicle) {
        try {
            // Primeiro, salva na nuvem (Firestore).
            // Usamos a 'plate' como ID do documento. Isso é CRUCIAL,
            // pois garante que as placas são únicas e facilita
            // o Update/Delete.
            firestore.collection(VEHICLES_COLLECTION)
                .document(vehicle.plate)
                .set(vehicle)
                .await() // Espera a operação do Firebase ser concluída

            // Se a linha acima NÃO deu exceção, o Firebase foi bem-sucedido.
            // Agora, salvamos localmente no Room.
            vehicleDao.upsertVehicle(vehicle)
        } catch (e: Exception) {
            // Se o Firebase falhar (ex: sem internet), uma exceção
            // será lançada. Nós a relançamos para o ViewModel
            // poder tratar (ex: mostrar uma mensagem de erro).
            throw e
        }
    }

    // --- Lógica de Escrita (DELETE) ---
    // (BÔNUS) ATUALIZAR ESTA FUNÇÃO TAMBÉM
    override suspend fun deleteVehicle(vehicle: Vehicle) {
        try {
            // Primeiro, deleta da nuvem
            firestore.collection(VEHICLES_COLLECTION)
                .document(vehicle.plate)
                .delete()
                .await()

            // Se foi bem-sucedido, deleta localmente
            vehicleDao.deleteVehicle(vehicle)
        } catch (e: Exception) {
            throw e
        }
    }
}