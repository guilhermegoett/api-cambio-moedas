package com.goett.moedas.service;

import com.goett.moedas.dto.ProdutoDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.ProdutoEntity;
import com.goett.moedas.infra.persistence.ProdutoRepository;
import com.goett.moedas.infra.persistence.mapper.ProdutoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    private ProdutoRepository produtoRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        produtoRepository = mock(ProdutoRepository.class);
        produtoMapper = mock(ProdutoMapper.class);
        produtoService = new ProdutoService(produtoRepository, produtoMapper);
    }

    @Test
    void deveListarProdutos() {
        ProdutoEntity entity = new ProdutoEntity();
        ProdutoDTO dto = new ProdutoDTO();

        when(produtoRepository.findAll()).thenReturn(List.of(entity));
        when(produtoMapper.toDto(entity)).thenReturn(dto);

        List<ProdutoDTO> result = produtoService.listarProdutos();

        assertEquals(1, result.size());
        verify(produtoRepository, times(1)).findAll();
        verify(produtoMapper, times(1)).toDto(entity);
    }

    @Test
    void deveCriarProdutoComSucesso() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("ESCUDO");

        ProdutoEntity entity = new ProdutoEntity();
        entity.setNome("ESCUDO");

        when(produtoRepository.findByNome("ESCUDO")).thenReturn(Optional.empty());
        when(produtoMapper.toEntity(dto)).thenReturn(entity);
        when(produtoRepository.save(entity)).thenReturn(entity);
        when(produtoMapper.toDto(entity)).thenReturn(dto);

        ProdutoDTO result = produtoService.criarProduto(dto);

        assertEquals("ESCUDO", result.getNome());
        verify(produtoRepository).findByNome("ESCUDO");
        verify(produtoRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoJaExiste() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("MACHADO");

        ProdutoEntity existente = new ProdutoEntity();
        existente.setNome("MACHADO");

        when(produtoRepository.findByNome("MACHADO")).thenReturn(Optional.of(existente));

        assertThrows(ProdutoJaExisteException.class, () -> produtoService.criarProduto(dto));

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        Long id = 1L;
        ProdutoEntity entity = new ProdutoEntity();
        ProdutoDTO dto = new ProdutoDTO();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(produtoMapper.toDto(entity)).thenReturn(dto);

        Optional<ProdutoDTO> result = produtoService.buscarPorId(id);

        assertTrue(result.isPresent());
        verify(produtoRepository).findById(id);
        verify(produtoMapper).toDto(entity);
    }

    @Test
    void deveRetornarVazioAoBuscarProdutoInexistentePorId() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProdutoDTO> result = produtoService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        verify(produtoRepository).findById(99L);
    }
}
