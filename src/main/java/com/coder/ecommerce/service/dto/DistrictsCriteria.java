package com.coder.ecommerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.coder.ecommerce.domain.Districts} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.DistrictsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistrictsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter bn_name;

    private StringFilter lat;

    private StringFilter lon;

    private StringFilter url;

    private LongFilter divisionsId;

    public DistrictsCriteria() {
    }

    public DistrictsCriteria(DistrictsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.bn_name = other.bn_name == null ? null : other.bn_name.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.lon = other.lon == null ? null : other.lon.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.divisionsId = other.divisionsId == null ? null : other.divisionsId.copy();
    }

    @Override
    public DistrictsCriteria copy() {
        return new DistrictsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBn_name() {
        return bn_name;
    }

    public void setBn_name(StringFilter bn_name) {
        this.bn_name = bn_name;
    }

    public StringFilter getLat() {
        return lat;
    }

    public void setLat(StringFilter lat) {
        this.lat = lat;
    }

    public StringFilter getLon() {
        return lon;
    }

    public void setLon(StringFilter lon) {
        this.lon = lon;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public LongFilter getDivisionsId() {
        return divisionsId;
    }

    public void setDivisionsId(LongFilter divisionsId) {
        this.divisionsId = divisionsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DistrictsCriteria that = (DistrictsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(bn_name, that.bn_name) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(lon, that.lon) &&
            Objects.equals(url, that.url) &&
            Objects.equals(divisionsId, that.divisionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        bn_name,
        lat,
        lon,
        url,
        divisionsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (bn_name != null ? "bn_name=" + bn_name + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (lon != null ? "lon=" + lon + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (divisionsId != null ? "divisionsId=" + divisionsId + ", " : "") +
            "}";
    }

}
