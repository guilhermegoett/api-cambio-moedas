package com.goett.moedas.adapter.controller;

import com.goett.model.Produto;
import com.goett.moedas.ProdutosApi;
import com.goett.moedas.dto.ProdutoDTO;
import com.goett.moedas.infra.persistence.mapper.ProdutoMapper;
import com.goett.moedas.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "produtos", description = "Operações relacionadas aos produtos do mercado do reino")
public class ProdutoController implements ProdutosApi {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @Override
    public ResponseEntity<List<Produto>> produtosGet() {
        List<ProdutoDTO> dtos = produtoService.listarProdutos();
        List<Produto> produtos = dtos.stream()
            .map(produtoMapper::toModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @Override
    public ResponseEntity<Produto> produtosIdGet(Integer id) {
        Optional<ProdutoDTO> dtoOpt = produtoService.buscarPorId(id.longValue());
        return dtoOpt
            .map(dto -> ResponseEntity.ok(produtoMapper.toModel(dto)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Produto> produtosPost(@Valid Produto produto) {
        ProdutoDTO dto = produtoMapper.toDto(produto);
        ProdutoDTO criado = produtoService.criarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.toModel(criado));
    }
}
