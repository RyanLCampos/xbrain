package com.ryanlcampos.xbrain.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Object[]> obterVendasPorVendedores(LocalDateTime inicio, LocalDateTime fim){
        
        List<Object[]> resultados = vendaRepository.obterVendasPorVendedores(inicio, fim);

        List<Object[]> lista = new ArrayList<>();

        for(Object[] valor : resultados) {
            String nome = (String) valor[0];
            BigDecimal total = ((BigDecimal) valor[1]).setScale(2, RoundingMode.HALF_UP);
            BigDecimal media = ((BigDecimal) valor[2]).setScale(2, RoundingMode.HALF_UP);

            lista.add(new Object[]{nome, total, media});
        }

        return lista;
    }
}
