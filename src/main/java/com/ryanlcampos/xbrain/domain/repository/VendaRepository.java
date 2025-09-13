package com.ryanlcampos.xbrain.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ryanlcampos.xbrain.domain.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{


    @Query("""
        SELECT v.vendedor.nome, 
               SUM(v.valor), 
               SUM(v.valor) / COUNT(DISTINCT CAST(v.dataVenda AS DATE))
        FROM Venda v
        WHERE CAST(v.dataVenda AS DATE) BETWEEN :inicio AND :fim
        GROUP BY v.vendedor.nome
    """)
    List<Object[]> obterVendasPorVendedores(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
