package com.HortiSystem.Sistema.repository;

import com.HortiSystem.Sistema.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REPOSITÓRIO DE VENDAS - Interface para operações no banco de dados de vendas
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    /**
     * BUSCAR VENDAS POR DATA
     */
    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);

    /**
     * BUSCAR VENDAS POR FORMA DE PAGAMENTO
     */
    List<Venda> findByFormaPagamento(String formaPagamento);

    /**
     * BUSCAR VENDAS POR STATUS
     */
    List<Venda> findByStatus(String status);

    /**
     * CALCULAR TOTAL DE VENDAS POR PERÍODO
     */
    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim AND v.status = 'FINALIZADA'")
    BigDecimal calcularTotalVendasPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * CONTAR QUANTIDADE DE VENDAS POR PERÍODO
     */
    @Query("SELECT COUNT(v) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim AND v.status = 'FINALIZADA'")
    Long contarVendasPeriodo(LocalDateTime inicio, LocalDateTime fim);
}