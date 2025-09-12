package com.ryanlcampos.xbrain.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Data
@Entity
@Table
public class Venda {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vendedor vendedor;
}
