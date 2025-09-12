package com.ryanlcampos.xbrain.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanlcampos.xbrain.domain.exceptions.EntidadeNaoEncontradoException;
import com.ryanlcampos.xbrain.domain.model.Venda;
import com.ryanlcampos.xbrain.domain.service.VendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid Venda venda) {
        try {

            Venda vendaCadastrada = vendaService.salvar(venda);

            return ResponseEntity.status(HttpStatus.CREATED).body(vendaCadastrada);
        } catch (EntidadeNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/vendas-por-vendedor")
    public List<Object[]> resumoVendas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return vendaService.obterVendasPorVendedores(inicio, fim);
    }
}
