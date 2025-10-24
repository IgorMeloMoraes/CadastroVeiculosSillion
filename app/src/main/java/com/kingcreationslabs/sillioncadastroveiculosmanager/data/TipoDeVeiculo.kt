package com.kingcreationslabs.sillioncadastroveiculosmanager.data

/**
 * Representa os tipos de veículos suportados pelo app.
 * Vamos adicionar um ícone (do Material Icons) associado a cada um
 * e um nome "amigável" para exibição.
 */
enum class TipoDeVeiculo(
    val nomeAmigavel: String,
    // (O @DrawableRes será usado na Sprint 4 para os ícones)
    // val iconRes: Int
) {
    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhão"),
    TRATOR("Trator"),
    OUTRO("Outro"); // Um valor padrão para casos não listados

    // (Opcional, mas útil)
    // Isso nos permite converter uma String "CARRO" de volta para o Enum
    companion object {
        fun fromString(value: String?): TipoDeVeiculo {
            return entries.find { it.name == value } ?: OUTRO
        }
    }
}