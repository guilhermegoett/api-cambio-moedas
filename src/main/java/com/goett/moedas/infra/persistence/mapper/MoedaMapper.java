package com.goett.moedas.infra.persistence.mapper;

import com.goett.model.Moeda;
import com.goett.moedas.dto.MoedaDTO;
import com.goett.moedas.infra.entity.MoedaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoedaMapper {
    MoedaDTO toDto(MoedaEntity moeda);
    MoedaDTO toDto(Moeda moeda);
    Moeda toModel(MoedaDTO dto);
    MoedaEntity toEntity(MoedaDTO dto);
}
