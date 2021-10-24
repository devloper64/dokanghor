package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.DistrictsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Districts} and its DTO {@link DistrictsDTO}.
 */
@Mapper(componentModel = "spring", uses = {DivisionsMapper.class})
public interface DistrictsMapper extends EntityMapper<DistrictsDTO, Districts> {

    @Mapping(source = "divisions.id", target = "divisionsId")
    DistrictsDTO toDto(Districts districts);

    @Mapping(source = "divisionsId", target = "divisions")
    Districts toEntity(DistrictsDTO districtsDTO);

    default Districts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Districts districts = new Districts();
        districts.setId(id);
        return districts;
    }
}
