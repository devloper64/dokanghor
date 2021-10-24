package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.UpazilasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Upazilas} and its DTO {@link UpazilasDTO}.
 */
@Mapper(componentModel = "spring", uses = {DistrictsMapper.class})
public interface UpazilasMapper extends EntityMapper<UpazilasDTO, Upazilas> {

    @Mapping(source = "districts.id", target = "districtsId")
    UpazilasDTO toDto(Upazilas upazilas);

    @Mapping(source = "districtsId", target = "districts")
    Upazilas toEntity(UpazilasDTO upazilasDTO);

    default Upazilas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Upazilas upazilas = new Upazilas();
        upazilas.setId(id);
        return upazilas;
    }
}
