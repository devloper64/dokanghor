package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Upazilas} entity.
 */
public class UpazilasDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String bn_name;

    @NotNull
    private String url;


    private Long districtsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBn_name() {
        return bn_name;
    }

    public void setBn_name(String bn_name) {
        this.bn_name = bn_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDistrictsId() {
        return districtsId;
    }

    public void setDistrictsId(Long districtsId) {
        this.districtsId = districtsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpazilasDTO)) {
            return false;
        }

        return id != null && id.equals(((UpazilasDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UpazilasDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bn_name='" + getBn_name() + "'" +
            ", url='" + getUrl() + "'" +
            ", districtsId=" + getDistrictsId() +
            "}";
    }
}
