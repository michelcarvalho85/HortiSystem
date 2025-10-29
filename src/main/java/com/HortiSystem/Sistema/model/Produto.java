// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.model;

import jakarta.persistence.*; // Anotações JPA para persistência
import lombok.Getter; // Gera getters automaticamente
import lombok.Setter; // Gera setters automaticamente
import java.math.BigDecimal; // Para valores monetários precisos
import java.time.LocalDate; // Para datas


/**
 * CLASSE ENTIDADE - REPRESENTA A TABELA DO BANCO DE DADOS
 * Esta classe mapeia a tabela 'produto_table' no banco de dados
 * usando a JPA (Jakarta Persistence API)
 */
@Getter // Lombok: gera automaticamente todos os métodos getters
@Setter // Lombok: gera automaticamente todos os métodos setters
@Entity // Indica que esta classe é uma entidade JPA (mapeia para uma tabela)
@Table(name = "produto_table") // Especifica o nome da tabela no banco
public class Produto {

    /**
     * ATRIBUTOS DA CLASSE - REPRESENTAM AS COLUNAS DA TABELA
     */

    // CHAVE PRIMÁRIA - Identificador único do produto
    @Id // Marca este campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID (auto-incremento)
    private Long id;

    // NOME DO PRODUTO - Campo público (geralmente não recomendado)
    public String nome;

    // QUANTIDADE EM ESTOQUE
    private int quantidade;

    // VALOR DO PRODUTO - Usa BigDecimal para precisão monetária
    private BigDecimal valor;

    // UNIDADE DE MEDIDA - Enum que define a unidade (kg, unidade, litro, etc.)
    private UnidadeMedida unidadeMedida;

    // CATEGORIA DO PRODUTO (hortaliças, frutas, etc.)
    private String categoria;

    /**
     * CONSTRUTORES - Métodos para criar instâncias da classe
     */

    // CONSTRUTOR PADRÃO (VAZIO) - Obrigatório para JPA
    public Produto(){
    }


    // CONSTRUTOR COMPLETO - Permite criar produto com todos os atributos
    public Produto(Long id , String nome , int quantidade , BigDecimal valor ,
                   UnidadeMedida unidadeMedida , String categoria ) {
        this.id = id ;
        this.nome = nome ;
        this.quantidade = quantidade ;
        this.valor = valor ;
        this.unidadeMedida = unidadeMedida ;
        this.categoria = categoria;
    }


    /**
     * MÉTODO DE VALIDAÇÃO - Verifica se os dados do produto são válidos
     * @return true se todos os dados estão válidos, false caso contrário
     */
<<<<<<< HEAD
    public boolean validarDados(){
=======
    public boolean ValidadarDados(){
>>>>>>> 2792662d5b50809780d28e9605544be4100c1603
        // Verifica se nome não é nulo ou vazio
        if (nome == null || nome.isBlank()) return false;

        // Verifica se quantidade é maior que zero
        if (quantidade <= 0) return false;

        // Verifica se valor não é nulo e é maior que zero
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) return false;

        // Verifica se unidade de medida foi definida
        if (unidadeMedida == null) return false;

        // Verifica se categoria não é nula ou vazia
        if (categoria == null || categoria.isBlank()) return false;

        // Se passou por todas as verificações, dados são válidos
        return true;
    }

    /**
     * MÉTODOS GETTERS E SETTERS MANUAIS
     * (Apesar de usar @Getter e @Setter do Lombok,
     *  estes métodos foram implementados manualmente)
     */

    // GETTER E SETTER para nome
    public String getNome() { return nome; }
    public void setNome(String novoNome) { this.nome = novoNome; }

    // GETTER E SETTER para quantidade
    public int getQuantidade(){ return quantidade; }
    public void setQuantidade(int novaQuantidade){ this.quantidade = novaQuantidade; }

    // GETTER E SETTER para valor
    public BigDecimal getValor(){ return valor; }
    public void setValor(BigDecimal novaValor){ this.valor = novaValor; }

    // GETTER E SETTER para unidade de medida
    public UnidadeMedida getUnidadeMedida(){ return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida novaUnidadeMedida){ this.unidadeMedida = novaUnidadeMedida;}

    // GETTER E SETTER para categoria
    public String getCategoria(){ return categoria; }
    public void setCategoria(String novaCategoria){ this.categoria = novaCategoria; }
}