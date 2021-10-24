package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.DivisionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Divisions} and its DTO {@link DivisionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DivisionsMapper extends EntityMapper<DivisionsDTO, Divisions> {



    default Divisions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Divisions divisions = new Divisions();
        divisions.setId(id);
        return divisions;
    }
}
