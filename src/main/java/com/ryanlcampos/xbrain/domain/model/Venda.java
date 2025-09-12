package com.ryanlcampos.xbrain.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @PrePersist
    public void PrePersist() {
        if(dataVenda == null) {
            dataVenda = LocalDateTime.now();
        }
    }

    @NotNull(message = "Valor é obrigatório")
    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vendedor vendedor;
}
