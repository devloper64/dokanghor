package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.MobileIntro} entity.
 */
public class MobileIntroDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private String image;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobileIntroDTO)) {
            return false;
        }

        return id != null && id.equals(((MobileIntroDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MobileIntroDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
