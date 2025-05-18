package com.goett.moedas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.goett.moedas.dto.ReinoDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.ReinoEntity;
import com.goett.moedas.infra.persistence.ReinoRepository;
import com.goett.moedas.infra.persistence.mapper.ReinoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReinoService {

    private final ReinoRepository reinoRepository;
    private final ReinoMapper reinoMapper;

    public List<ReinoDTO> listarReinos() {
        return reinoRepository.findAll().stream()
            .map(reinoMapper::toDto)
            .collect(Collectors.toList());
    }

    public ReinoDTO criarReino(ReinoDTO dto) {
        Optional<ReinoEntity> existente = reinoRepository.findByNome(dto.getNome());
        if (existente.isPresent()) {
            throw new ProdutoJaExisteException(dto.getNome());
        }

        ReinoEntity reinoEntity = reinoMapper.toEntity(dto);
        ReinoEntity salvo = reinoRepository.save(reinoEntity);
        return reinoMapper.toDto(salvo);
    }

    public Optional<ReinoDTO> buscarPorId(Long id) {
        return reinoRepository.findById(id)
            .map(reinoMapper::toDto);
    }
}
