package com.goett.moedas.infra.persistence.mapper;

import com.goett.model.Produto;
import com.goett.moedas.dto.ProdutoDTO;
import com.goett.moedas.infra.entity.ProdutoEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoDTO toDto(ProdutoEntity produto);
    ProdutoDTO toDto(Produto produto);
    Produto toModel(ProdutoDTO dto);
    ProdutoEntity toEntity(ProdutoDTO dto);
}
