package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.MobileIntroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MobileIntro} and its DTO {@link MobileIntroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MobileIntroMapper extends EntityMapper<MobileIntroDTO, MobileIntro> {



    default MobileIntro fromId(Long id) {
        if (id == null) {
            return null;
        }
        MobileIntro mobileIntro = new MobileIntro();
        mobileIntro.setId(id);
        return mobileIntro;
    }
}
