package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Divisions} entity.
 */
public class DivisionsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String bn_name;

    @NotNull
    private String url;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DivisionsDTO)) {
            return false;
        }

        return id != null && id.equals(((DivisionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bn_name='" + getBn_name() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
