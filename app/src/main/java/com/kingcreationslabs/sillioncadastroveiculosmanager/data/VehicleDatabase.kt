package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Anotação "@Database"
@Database(
    entities = [Vehicle::class], // Lista de todas as tabelas (entidades)
    version = 1,                 //  Versão do banco (se mudarmos o schema, incrementamos)
    exportSchema = false         //  (Opcional) Desativa exportação de schema (bom para testes)
)
//  Vamos adicionar um conversor de tipo para o Room saber
// como salvar o "Date" do nosso campo "timestamp".
@TypeConverters(Converters::class)
abstract class VehicleDatabase : RoomDatabase() { // Precisa herdar de RoomDatabase

    //  Função abstrata que "fornece" o DAO para o resto do app
    abstract fun vehicleDao(): VehicleDao

    //  (Opcional, mas boa prática) Um "companion object"
    // para definir o nome do banco de dados em um só lugar.
    companion object {
        const val DATABASE_NAME = "vehicle_db"
    }
}