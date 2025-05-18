package com.goett.moedas.infra.persistence.mapper;

import com.goett.model.Reino;
import com.goett.moedas.dto.ReinoDTO;
import com.goett.moedas.infra.entity.ReinoEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReinoMapper {
    ReinoDTO toDto(ReinoEntity reino);
    ReinoDTO toDto(Reino reino);
    Reino toModel(ReinoDTO dto);
    ReinoEntity toEntity(ReinoDTO dto);
}
