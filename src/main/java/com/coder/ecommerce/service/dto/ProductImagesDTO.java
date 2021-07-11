package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.ProductImages} entity.
 */
public class ProductImagesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String image;


    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductImagesDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductImagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductImagesDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", productId=" + getProductId() +
            "}";
    }
}
