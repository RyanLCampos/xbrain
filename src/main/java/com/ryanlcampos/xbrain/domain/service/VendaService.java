package com.ryanlcampos.xbrain.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryanlcampos.xbrain.domain.model.Venda;
import com.ryanlcampos.xbrain.domain.model.Vendedor;
import com.ryanlcampos.xbrain.domain.repository.VendaRepository;
@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorService vendedorService;

    public Venda salvar(Venda venda) {

        
        Vendedor vendedor = vendedorService.obterPorId(venda.getVendedor().getId());

        venda.setVendedor(vendedor);
        
        return vendaRepository.save(venda);
    }
}
