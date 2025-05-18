package com.goett.moedas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.goett.moedas.dto.MoedaDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.MoedaEntity;
import com.goett.moedas.infra.persistence.MoedaRepository;
import com.goett.moedas.infra.persistence.mapper.MoedaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoedaService {

    private final MoedaRepository moedaRepository;
    private final MoedaMapper moedaMapper;

    public List<MoedaDTO> listarMoedas() {
        return moedaRepository.findAll().stream()
            .map(moedaMapper::toDto)
            .collect(Collectors.toList());
    }

    public MoedaDTO criarMoeda(MoedaDTO dto) {
        Optional<MoedaEntity> existente = moedaRepository.findByNome(dto.getNome());
        if (existente.isPresent()) {
            throw new ProdutoJaExisteException(dto.getNome());
        }

        MoedaEntity moedaEntity = moedaMapper.toEntity(dto);
        MoedaEntity salvo = moedaRepository.save(moedaEntity);
        return moedaMapper.toDto(salvo);
    }

    public Optional<MoedaDTO> buscarPorId(Long id) {
        return moedaRepository.findById(id)
            .map(moedaMapper::toDto);
    }
}
