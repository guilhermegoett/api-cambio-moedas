package com.goett.moedas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.goett.moedas.dto.ProdutoDTO;
import com.goett.moedas.exception.ProdutoJaExisteException;
import com.goett.moedas.infra.entity.ProdutoEntity;
import com.goett.moedas.infra.persistence.ProdutoRepository;
import com.goett.moedas.infra.persistence.mapper.ProdutoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
            .map(produtoMapper::toDto)
            .collect(Collectors.toList());
    }

    public ProdutoDTO criarProduto(ProdutoDTO dto) {
        Optional<ProdutoEntity> existente = produtoRepository.findByNome(dto.getNome());
        if (existente.isPresent()) {
            throw new ProdutoJaExisteException(dto.getNome());
        }

        ProdutoEntity produtoEntity = produtoMapper.toEntity(dto);
        ProdutoEntity salvo = produtoRepository.save(produtoEntity);
        return produtoMapper.toDto(salvo);
    }

    public Optional<ProdutoDTO> buscarPorId(Long id) {
        return produtoRepository.findById(id)
            .map(produtoMapper::toDto);
    }
}
