package com.jokolelung.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Trip
 */
@ApiModel(description = "Trip")
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journey> journeys = new HashSet<>();

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreOrder> preOrders = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Trip startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Trip endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<Journey> getJourneys() {
        return journeys;
    }

    public Trip journeys(Set<Journey> journeys) {
        this.journeys = journeys;
        return this;
    }

    public Trip addJourney(Journey journey) {
        this.journeys.add(journey);
        journey.setTrip(this);
        return this;
    }

    public Trip removeJourney(Journey journey) {
        this.journeys.remove(journey);
        journey.setTrip(null);
        return this;
    }

    public void setJourneys(Set<Journey> journeys) {
        this.journeys = journeys;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public Trip offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public Trip addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setTrip(this);
        return this;
    }

    public Trip removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setTrip(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<PreOrder> getPreOrders() {
        return preOrders;
    }

    public Trip preOrders(Set<PreOrder> preOrders) {
        this.preOrders = preOrders;
        return this;
    }

    public Trip addPreOrder(PreOrder preOrder) {
        this.preOrders.add(preOrder);
        preOrder.setTrip(this);
        return this;
    }

    public Trip removePreOrder(PreOrder preOrder) {
        this.preOrders.remove(preOrder);
        preOrder.setTrip(null);
        return this;
    }

    public void setPreOrders(Set<PreOrder> preOrders) {
        this.preOrders = preOrders;
    }

    public User getUser() {
        return user;
    }

    public Trip user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Trip trip = (Trip) o;
        if (trip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
