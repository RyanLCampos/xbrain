package com.ryanlcampos.xbrain.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        if(venda.getVendedor() == null) {
            throw new IllegalArgumentException("Vendedor é obrigatório");
        }
        
        Vendedor vendedor = vendedorService.obterPorId(venda.getVendedor().getId());

        venda.setVendedor(vendedor);
        
        return vendaRepository.save(venda);
    }

    public List<Map<String, Object>> obterVendasPorVendedores(LocalDate inicio, LocalDate fim){
        
        List<Object[]> resultados = vendaRepository.obterVendasPorVendedores(inicio, fim);

        List<Map<String, Object>> lista = new ArrayList<>();

        for(Object[] valor : resultados) {
            String nome = (String) valor[0];
            BigDecimal total = ((BigDecimal) valor[1]).setScale(2, RoundingMode.HALF_UP);
            BigDecimal media = ((BigDecimal) valor[2]).setScale(2, RoundingMode.HALF_UP);

            Map<String, Object> elemento = new LinkedHashMap<>();

            elemento.put("nome", nome);
            elemento.put("totalVendas", total);
            elemento.put("mediaDiaria", media);

            lista.add(elemento);
        }

        return lista;
    }
}
