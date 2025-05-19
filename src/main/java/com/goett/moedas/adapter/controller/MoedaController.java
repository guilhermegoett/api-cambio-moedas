package com.goett.moedas.adapter.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.goett.model.Moeda;
import com.goett.moedas.MoedasApi;
import com.goett.moedas.dto.MoedaDTO;
import com.goett.moedas.infra.persistence.mapper.MoedaMapper;
import com.goett.moedas.service.MoedaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
public class MoedaController implements MoedasApi{

    private final MoedaService moedaService;
    private final MoedaMapper moedaMapper;

    @Override
    public ResponseEntity<List<Moeda>> moedasGet() {
        List<MoedaDTO> dtos = moedaService.listarMoedas();
        List<Moeda> moedas = dtos.stream()
            .map(moedaMapper::toModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(moedas);
    }

    @Override
    public ResponseEntity<Moeda> moedasIdGet(Integer id) {
        Optional<MoedaDTO> dtoOpt = moedaService.buscarPorId(id.longValue());
        return dtoOpt
            .map(dto -> ResponseEntity.ok(moedaMapper.toModel(dto)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Moeda> moedasPost(@Valid Moeda moeda) {
        MoedaDTO dto = moedaMapper.toDto(moeda);
        MoedaDTO criado = moedaService.criarMoeda(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(moedaMapper.toModel(criado));
    }
}
