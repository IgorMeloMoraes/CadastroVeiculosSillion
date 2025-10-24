package com.kingcreationslabs.sillioncadastroveiculosmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration // 1. (NOVA IMPORTAÇÃO)
import androidx.sqlite.db.SupportSQLiteDatabase // 2. (NOVA IMPORTAÇÃO)

// Anotação "@Database"
@Database(
    entities = [Vehicle::class], // Lista de todas as tabelas (entidades)
    version = 2,                 // 3. (MUDANÇA) Versão aumentada para 2                 //  Versão do banco (se mudarmos o schema, incrementamos)
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

        // 4. (NOVO) Esta é a nossa instrução de migração
        /**
         * Ensina o Room a migrar da Versão 1 para a Versão 2.
         * A instrução SQL 'ALTER TABLE' adiciona a nova coluna 'type'
         * à 'vehicle_table' existente.
         *
         * - 'TEXT': O Room salvará nosso Enum 'TipoDeVeiculo' como String.
         * - 'NOT NULL': Nosso data class não permite nulos.
         * - 'DEFAULT 'OUTRO'': Para todos os veículos que JÁ EXISTEM
         * no banco, o Room preencherá esta coluna com o valor "OUTRO".
         */
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE vehicle_table ADD COLUMN type TEXT NOT NULL DEFAULT 'OUTRO'"
                )
            }
        }
    }
}