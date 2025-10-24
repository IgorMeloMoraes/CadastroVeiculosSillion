package com.kingcreationslabs.sillioncadastroveiculosmanager.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// Anotação do Room: Define o nome da tabela
@Entity(tableName = "veiculos")
data class Veiculos(

    // Anotação do Room: Define a chave primária.
    // O autoGenerate = true não é ideal aqui, pois queremos que o ID
    // do Firebase e do Room sejam os mesmos. Vamos usar a Placa como ID.
    @PrimaryKey
    val placa: String = "", // Placa (ex: ABC1234) - Usaremos como ID

    // --- Campos de Identificação Básica ---
    val modelo: String = "",
    val fabricante: String = "",
    val anoFabricacao: Int = 0, // Ano (Ex: 2023)
    val cor: String = "",
    val quilometragem: Long = 0, // Quilometragem


    // --- Campos de Gerenciamento (Business Logic) ---
    // (Requisito Mínimo)
    // Nota: Usamos Long? (nullable Long) para salvar o timestamp (milissegundos).
    // É mais fácil de salvar no Room e no Firebase do que objetos Date.
    // Vamos usar o Timestamp do Firebase para as datas.
    // A anotação @ServerTimestamp faz o Firebase preencher isso automaticamente
    // na criação, se o campo for nulo. Mas para revisões, vamos querer
    // que o usuário insira. Vamos usar java.util.Date, que o Room e o Firebase
    // convertem bem.
    val ultimaRevisao: Date? = null,
    val proximaRevisao: Date? = null,

    // (Campo Adicional Empresarial)
    // Mostra que pensamos em outros "lembretes" valiosos para o usuário.
    val vencimentoLicenciamento: Long? = null, // Vencimento do Licenciamento

    // Mostra que pensamos no ciclo de vida do ativo.
    // Ex: "Ativo", "Em Manutenção", "Vendido"
    val status: String = "Ativo",

    // Mostra que pensamos no "dono" da informação.
    val nomeProprietario: String = "",

    // Mostra que pensamos na flexibilidade do sistema.
    val obervacoesAdicionais: String = "",

    // --- Campo de Auditoria ---
    // Anotação do Firebase: Quando este objeto for salvo no Firestore,
    // o Firebase preencherá este campo automaticamente com a hora do servidor.
    // Útil para saber quando o registro foi criado ou atualizado.
    // Campo para sabermos quando foi criado/atualizado no Firebase
    @ServerTimestamp
    val timestamp: Date? = null
)
