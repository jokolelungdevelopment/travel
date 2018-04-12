package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Journey
 */
@ApiModel(description = "Journey")
@Entity
@Table(name = "journey")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private City city;

    @ManyToOne
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public Journey trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public City getCity() {
        return city;
    }

    public Journey city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public Journey country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Journey journey = (Journey) o;
        if (journey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Journey{" +
            "id=" + getId() +
            "}";
    }
}
