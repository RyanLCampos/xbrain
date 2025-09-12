package com.ryanlcampos.xbrain.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryanlcampos.xbrain.domain.model.Venda;
import com.ryanlcampos.xbrain.domain.repository.VendaRepository;
@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }
}
