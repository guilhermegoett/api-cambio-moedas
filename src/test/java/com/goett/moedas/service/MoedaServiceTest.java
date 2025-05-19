package com.goett.moedas.service;

import com.goett.moedas.dto.MoedaDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.MoedaEntity;
import com.goett.moedas.infra.persistence.MoedaRepository;
import com.goett.moedas.infra.persistence.mapper.MoedaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoedaServiceTest {

    private MoedaRepository moedaRepository;
    private MoedaMapper moedaMapper;
    private MoedaService moedaService;

    @BeforeEach
    void setUp() {
        moedaRepository = mock(MoedaRepository.class);
        moedaMapper = mock(MoedaMapper.class);
        moedaService = new MoedaService(moedaRepository, moedaMapper);
    }

    @Test
    void deveListarMoedas() {
        MoedaEntity entity = new MoedaEntity();
        MoedaDTO dto = new MoedaDTO();
        when(moedaRepository.findAll()).thenReturn(List.of(entity));
        when(moedaMapper.toDto(entity)).thenReturn(dto);

        List<MoedaDTO> result = moedaService.listarMoedas();

        assertEquals(1, result.size());
        verify(moedaRepository, times(1)).findAll();
        verify(moedaMapper, times(1)).toDto(entity);
    }

    @Test
    void deveCriarMoedaComSucesso() {
        MoedaDTO dto = new MoedaDTO();
        dto.setNome("OURO_REAL");

        MoedaEntity entity = new MoedaEntity();
        entity.setNome("OURO_REAL");

        when(moedaRepository.findByNome("OURO_REAL")).thenReturn(Optional.empty());
        when(moedaMapper.toEntity(dto)).thenReturn(entity);
        when(moedaRepository.save(entity)).thenReturn(entity);
        when(moedaMapper.toDto(entity)).thenReturn(dto);

        MoedaDTO result = moedaService.criarMoeda(dto);

        assertEquals("OURO_REAL", result.getNome());
        verify(moedaRepository).findByNome("OURO_REAL");
        verify(moedaRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoQuandoMoedaJaExiste() {
        MoedaDTO dto = new MoedaDTO();
        dto.setNome("TIBAR");

        MoedaEntity existente = new MoedaEntity();
        existente.setNome("TIBAR");

        when(moedaRepository.findByNome("TIBAR")).thenReturn(Optional.of(existente));

        assertThrows(ProdutoJaExisteException.class, () -> moedaService.criarMoeda(dto));

        verify(moedaRepository, never()).save(any());
    }

    @Test
    void deveBuscarMoedaPorIdComSucesso() {
        Long id = 1L;
        MoedaEntity entity = new MoedaEntity();
        MoedaDTO dto = new MoedaDTO();

        when(moedaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(moedaMapper.toDto(entity)).thenReturn(dto);

        Optional<MoedaDTO> result = moedaService.buscarPorId(id);

        assertTrue(result.isPresent());
        verify(moedaRepository).findById(id);
        verify(moedaMapper).toDto(entity);
    }

    @Test
    void deveRetornarVazioAoBuscarMoedaInexistentePorId() {
        when(moedaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<MoedaDTO> result = moedaService.buscarPorId(99L);

        assertTrue(result.isEmpty());
        verify(moedaRepository).findById(99L);
    }
}
