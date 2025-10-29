// PACOTE
package com.HortiSystem.Sistema.model;

/**
 * ENUMERAÇÃO UNIDADE MEDIDA - DEFINE AS UNIDADES DE MEDIDA DISPONÍVEIS
 * Enum é um tipo especial que representa um conjunto fixo de constantes
 * Usado para garantir que apenas valores válidos sejam aceitos
 */
public enum UnidadeMedida {
    KG,  // Quilograma - para produtos vendidos por peso
    UN,  // Unidade - para produtos vendidos por unidade
    LT,  // Litro - para produtos líquidos vendidos por litro
    ML,  // Mililitro - para produtos líquidos em pequenas quantidades
    GR,  // Grama - para produtos vendidos por peso em pequenas quantidades
    CX   // Caixa - para produtos vendidos em caixas
}