package com.coder.ecommerce.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Divisions.
 */
@Entity
@Table(name = "divisions")
public class Divisions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "bn_name", nullable = false)
    private String bn_name;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Divisions name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBn_name() {
        return bn_name;
    }

    public Divisions bn_name(String bn_name) {
        this.bn_name = bn_name;
        return this;
    }

    public void setBn_name(String bn_name) {
        this.bn_name = bn_name;
    }

    public String getUrl() {
        return url;
    }

    public Divisions url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Divisions)) {
            return false;
        }
        return id != null && id.equals(((Divisions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Divisions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bn_name='" + getBn_name() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
