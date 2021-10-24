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
 * Criteria class for the {@link com.coder.ecommerce.domain.Upazilas} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.UpazilasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /upazilas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UpazilasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter bn_name;

    private StringFilter url;

    private LongFilter districtsId;

    public UpazilasCriteria() {
    }

    public UpazilasCriteria(UpazilasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.bn_name = other.bn_name == null ? null : other.bn_name.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.districtsId = other.districtsId == null ? null : other.districtsId.copy();
    }

    @Override
    public UpazilasCriteria copy() {
        return new UpazilasCriteria(this);
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

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public LongFilter getDistrictsId() {
        return districtsId;
    }

    public void setDistrictsId(LongFilter districtsId) {
        this.districtsId = districtsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UpazilasCriteria that = (UpazilasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(bn_name, that.bn_name) &&
            Objects.equals(url, that.url) &&
            Objects.equals(districtsId, that.districtsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        bn_name,
        url,
        districtsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UpazilasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (bn_name != null ? "bn_name=" + bn_name + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (districtsId != null ? "districtsId=" + districtsId + ", " : "") +
            "}";
    }

}
