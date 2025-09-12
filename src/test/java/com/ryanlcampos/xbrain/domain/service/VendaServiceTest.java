package com.ryanlcampos.xbrain.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ryanlcampos.xbrain.domain.exceptions.EntidadeNaoEncontradoException;
import com.ryanlcampos.xbrain.domain.model.Venda;
import com.ryanlcampos.xbrain.domain.model.Vendedor;
import com.ryanlcampos.xbrain.domain.repository.VendaRepository;

public class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendedorService vendedorService;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarVendaComVendedorExistente() {
        Vendedor vendedor = new Vendedor();

        vendedor.setId(1L);
        vendedor.setNome("Ryan Lucas");

        Venda venda = new Venda();

        venda.setId(1L);
        venda.setValor(new BigDecimal(3500));
        venda.setVendedor(vendedor);

        when(vendedorService.obterPorId(1L)).thenReturn(vendedor);

        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda resultado = vendaService.salvar(venda);

        assertEquals(1L, resultado.getId());
        assertEquals("Ryan Lucas", resultado.getVendedor().getNome());
        assertEquals(new BigDecimal(3500), resultado.getValor());

        verify(vendedorService, times(1)).obterPorId(1L);
        verify(vendaRepository, times(1)).save(venda);
    }

    @Test
    void deveLancarExcecaoQuandoVendedorNaoExistir() {
        Venda venda = new Venda();

        Vendedor vendedor = new Vendedor();

        vendedor.setId(1L);

        venda.setVendedor(vendedor);

        when(vendedorService.obterPorId(anyLong()))
                .thenThrow(new EntidadeNaoEncontradoException("Vendedor não encontrado"));

        EntidadeNaoEncontradoException exception = assertThrows(EntidadeNaoEncontradoException.class,
                () -> vendaService.salvar(venda));

        assertEquals("Vendedor não encontrado", exception.getMessage());
    }

    @Test
    void deveRetornarListaDeVendedoresEVendas() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 9, 12, 0, 0);

        Object[] registro = {"Ryan Lucas", new BigDecimal(154.352), new BigDecimal(10.564)};

        List<Object[]> registros = new ArrayList<>();
        registros.add(registro);

        when(vendaRepository.obterVendasPorVendedores(inicio, fim)).thenReturn(registros);

        List<Object[]> resultado = vendaService.obterVendasPorVendedores(inicio, fim);

        assertEquals(1, resultado.size());

        Object[] linha = resultado.get(0);
        assertEquals("Ryan Lucas", linha[0]);
        assertEquals(new BigDecimal("154.35"), linha[1]);
        assertEquals(new BigDecimal("10.56"), linha[2]);

        verify(vendaRepository, times(1)).obterVendasPorVendedores(inicio, fim);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverVendas() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(10);
        LocalDateTime fim = LocalDateTime.now();

        when(vendaRepository.obterVendasPorVendedores(inicio, fim)).thenReturn(new ArrayList<>());

        List<Object[]> resultado = vendaService.obterVendasPorVendedores(inicio, fim);

        assertEquals(0, resultado.size());
    }
}
