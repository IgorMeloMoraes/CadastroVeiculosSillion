package com.kingcreationslabs.sillioncadastroveiculosmanager.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// Anotação do Room: Define o nome da tabela
@Entity(tableName = "vehicle_table")
data class Vehicle(

    @PrimaryKey
    val plate: String = "", // Placa (ex: ABC1234) - Usaremos como ID

    // --- Campos de Identificação Básica ---
    val model: String = "", // modelo (ex: Civic)
    val manufacturer: String = "", // fabricante (ex: Honda)
    val modelYear: Int = 0, // Ano Fabricação (Ex: 2023)
    val color: String = "", // cor (ex: Preto)

    // --- Campos de Informações Complementares ---
    val mileage: Long = 0, // Quilometragem (ex: 10000)

    // --- Campos de Gerenciamento (Business Logic) ---
    val lastRevision: Date? = null, // Ultima revisão (ex: 2023-01-01)
    val nextRevision: Date? = null, // Proxima revisão (ex: 2023-02-01)
    val registrationDueDate: Date? = null, // Vencimento do Licenciamento (ex: 2023-06-01)

    val status: String = "Ativo", // (Ex: "Ativo", "Em Manutenção", "Vendido")
    val ownerName: String = "", // nome do dono (ex: João Silva)
    val notes: String = "", // observações (ex: Objeto novo)

    @ColumnInfo(defaultValue = "OUTRO")
    val type: TipoDeVeiculo = TipoDeVeiculo.CARRO,

    // --- Campo de Auditoria ---
    @ServerTimestamp
    val timestamp: Date? = null
)
