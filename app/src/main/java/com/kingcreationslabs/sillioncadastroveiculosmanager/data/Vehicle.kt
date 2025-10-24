package com.kingcreationslabs.sillioncadastroveiculosmanager.data


import androidx.room.ColumnInfo // 1. (NOVA IMPORTAÇÃO)
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// Anotação do Room: Define o nome da tabela
@Entity(tableName = "vehicle_table")
data class Vehicle(

    // Anotação do Room: Define a chave primária.
    // O autoGenerate = true não é ideal aqui, pois queremos que o ID
    // do Firebase e do Room sejam os mesmos. Vamos usar a Placa como ID.
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
    // (Requisito Mínimo)
    // Nota: Usamos Long? (nullable Long) para salvar o timestamp (milissegundos).
    // É mais fácil de salvar no Room e no Firebase do que objetos Date.
    // Vamos usar o Timestamp do Firebase para as datas.
    // A anotação @ServerTimestamp faz o Firebase preencher isso automaticamente
    // na criação, se o campo for nulo. Mas para revisões, vamos querer
    // que o usuário insira. Vamos usar java.util.Date, que o Room e o Firebase
    // convertem bem.
    val lastRevision: Date? = null, // Ultima revisão (ex: 2023-01-01)
    val nextRevision: Date? = null, // Proxima revisão (ex: 2023-02-01)

    // (Campo Adicional Empresarial)
    // Mostra que pensamos em outros "lembretes" valiosos para o usuário.
    val registrationDueDate: Date? = null, // Vencimento do Licenciamento (ex: 2023-06-01)


    // Mostra que pensamos no ciclo de vida do ativo.
    // Ex: "Ativo", "Em Manutenção", "Vendido"
    val status: String = "Ativo", // (Ex: "Active", "Maintenance", "Sold")

    // Mostra que pensamos no "dono" da informação.
    val ownerName: String = "", // nome do dono (ex: João Silva)


    // Mostra que pensamos na flexibilidade do sistema.
    val notes: String = "", // observações (ex: Objeto novo)

    // 2. (NOVO CAMPO ADICIONADO)
    // Define o valor padrão para o Room (como String)
    @ColumnInfo(defaultValue = "OUTRO")
    // Define o valor padrão para o Kotlin (quando criamos um novo objeto)
    val type: TipoDeVeiculo = TipoDeVeiculo.CARRO,

    // --- Campo de Auditoria ---
    // Anotação do Firebase: Quando este objeto for salvo no Firestore,
    // o Firebase preencherá este campo automaticamente com a hora do servidor.
    // Útil para saber quando o registro foi criado ou atualizado.
    // Campo para sabermos quando foi criado/atualizado no Firebase
    @ServerTimestamp
    val timestamp: Date? = null
)
