package com.goett.moedas.service;

import com.goett.moedas.dto.ReinoDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.ReinoEntity;
import com.goett.moedas.infra.persistence.ReinoRepository;
import com.goett.moedas.infra.persistence.mapper.ReinoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReinoServiceTest {

    private ReinoRepository reinoRepository;
    private ReinoMapper reinoMapper;
    private ReinoService reinoService;

    @BeforeEach
    void setUp() {
        reinoRepository = mock(ReinoRepository.class);
        reinoMapper = mock(ReinoMapper.class);
        reinoService = new ReinoService(reinoRepository, reinoMapper);
    }

    @Test
    void deveListarReinos() {
        ReinoEntity entity = new ReinoEntity();
        ReinoDTO dto = new ReinoDTO();

        when(reinoRepository.findAll()).thenReturn(List.of(entity));
        when(reinoMapper.toDto(entity)).thenReturn(dto);

        List<ReinoDTO> result = reinoService.listarReinos();

        assertEquals(1, result.size());
        verify(reinoRepository).findAll();
        verify(reinoMapper).toDto(entity);
    }

    @Test
    void deveCriarReinoComSucesso() {
        ReinoDTO dto = new ReinoDTO();
        dto.setNome("ASGAR");

        ReinoEntity entity = new ReinoEntity();
        entity.setNome("ASGAR");

        when(reinoRepository.findByNome("ASGAR")).thenReturn(Optional.empty());
        when(reinoMapper.toEntity(dto)).thenReturn(entity);
        when(reinoRepository.save(entity)).thenReturn(entity);
        when(reinoMapper.toDto(entity)).thenReturn(dto);

        ReinoDTO result = reinoService.criarReino(dto);

        assertEquals("ASGAR", result.getNome());
        verify(reinoRepository).findByNome("ASGAR");
        verify(reinoRepository).save(entity);
        verify(reinoMapper).toDto(entity);
    }

    @Test
    void deveLancarExcecaoQuandoReinoJaExiste() {
        ReinoDTO dto = new ReinoDTO();
        dto.setNome("ERGON");

        ReinoEntity existente = new ReinoEntity();
        existente.setNome("ERGON");

        when(reinoRepository.findByNome("ERGON")).thenReturn(Optional.of(existente));

        assertThrows(ProdutoJaExisteException.class, () -> reinoService.criarReino(dto));

        verify(reinoRepository, never()).save(any());
        verify(reinoMapper, never()).toEntity(any());
    }

    @Test
    void deveBuscarReinoPorIdComSucesso() {
        Long id = 1L;
        ReinoEntity entity = new ReinoEntity();
        ReinoDTO dto = new ReinoDTO();

        when(reinoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(reinoMapper.toDto(entity)).thenReturn(dto);

        Optional<ReinoDTO> result = reinoService.buscarPorId(id);

        assertTrue(result.isPresent());
        verify(reinoRepository).findById(id);
        verify(reinoMapper).toDto(entity);
    }

    @Test
    void deveRetornarVazioAoBuscarReinoInexistentePorId() {
        when(reinoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ReinoDTO> result = reinoService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        verify(reinoRepository).findById(99L);
    }
}
