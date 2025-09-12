package com.ryanlcampos.xbrain.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryanlcampos.xbrain.domain.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long>{
    
}
