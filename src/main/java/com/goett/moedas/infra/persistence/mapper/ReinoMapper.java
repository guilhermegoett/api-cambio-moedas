package com.goett.moedas.infra.persistence.mapper;

import com.goett.model.Reino;
import com.goett.moedas.dto.ReinoDTO;
import com.goett.moedas.infra.entity.ReinoEntity;
import com.goett.moedas.infra.entity.MoedaEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReinoMapper {

    // ReinoEntity -> ReinoDTO
    @Mapping(source = "moeda.id", target = "moedaId")
    ReinoDTO toDto(ReinoEntity reinoEntity);

    // Reino (modelo de domínio) -> ReinoDTO
    ReinoDTO toDto(Reino reino);

    // ReinoDTO -> Reino (modelo de domínio)
    Reino toModel(ReinoDTO dto);

    // ReinoDTO -> ReinoEntity
    @Mapping(source = "moedaId", target = "moeda")
    ReinoEntity toEntity(ReinoDTO dto);

    // Método auxiliar para converter moedaId (Long) em MoedaEntity
    default MoedaEntity map(Long moedaId) {
        if (moedaId == null) {
            return null;
        }
        MoedaEntity moeda = new MoedaEntity();
        moeda.setId(moedaId);
        return moeda;
    }

    // Método auxiliar para converter MoedaEntity em moedaId (Long)
    default Long map(MoedaEntity moeda) {
        return moeda != null ? moeda.getId() : null;
    }
}
