package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Vehicle::class], // Lista de todas as tabelas (entidades)
    version = 2,                 // Versão do banco (se mudarmos o schema, incrementamos)
    exportSchema = false         // Desativa exportação de schema (bom para testes)
)

@TypeConverters(Converters::class)
abstract class VehicleDatabase : RoomDatabase() {

    //  Função abstrata que "fornece" o DAO para o resto do app
    abstract fun vehicleDao(): VehicleDao

    companion object {
        const val DATABASE_NAME = "vehicle_db"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE vehicle_table ADD COLUMN type TEXT NOT NULL DEFAULT 'OUTRO'"
                )
            }
        }
    }
}