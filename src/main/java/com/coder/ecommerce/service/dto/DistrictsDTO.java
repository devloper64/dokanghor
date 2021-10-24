package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Districts} entity.
 */
public class DistrictsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String bn_name;

    @NotNull
    private String lat;

    @NotNull
    private String lon;

    @NotNull
    private String url;


    private Long divisionsId;
    
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDivisionsId() {
        return divisionsId;
    }

    public void setDivisionsId(Long divisionsId) {
        this.divisionsId = divisionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictsDTO)) {
            return false;
        }

        return id != null && id.equals(((DistrictsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bn_name='" + getBn_name() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", url='" + getUrl() + "'" +
            ", divisionsId=" + getDivisionsId() +
            "}";
    }
}
