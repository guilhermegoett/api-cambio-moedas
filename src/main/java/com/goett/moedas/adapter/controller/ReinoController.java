package com.goett.moedas.adapter.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goett.model.Reino;
import com.goett.moedas.ReinosApi;
import com.goett.moedas.dto.ReinoDTO;
import com.goett.moedas.infra.persistence.mapper.ReinoMapper;
import com.goett.moedas.service.ReinoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Reino", description = "Operações relacionadas aos reinos do mundo fantástico")
public class ReinoController implements ReinosApi {
    
    private final ReinoService reinoService;
    private final ReinoMapper reinoMapper;

    @Override
    public ResponseEntity<List<Reino>> reinosGet() {
        List<ReinoDTO> dtos = reinoService.listarReinos();
        List<Reino> reinos = dtos.stream()
            .map(reinoMapper::toModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(reinos);
    }

    @Override
    public ResponseEntity<Reino> reinosIdGet(Integer id) {
        Optional<ReinoDTO> dtoOpt = reinoService.buscarPorId(id.longValue());
        return dtoOpt
            .map(dto -> ResponseEntity.ok(reinoMapper.toModel(dto)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Reino> reinosPost(@Valid Reino reino) {
        ReinoDTO dto = reinoMapper.toDto(reino);
        ReinoDTO criado = reinoService.criarReino(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reinoMapper.toModel(criado));
    }
}
