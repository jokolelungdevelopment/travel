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

import com.jokolelung.travel.domain.enumeration.Status;

/**
 * Request
 */
@ApiModel(description = "Request")
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_date")
    private Instant requestDate;

    @Column(name = "viewed")
    private Long viewed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "travelerid")
    private Long travelerid;

    @OneToMany(mappedBy = "request")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public Request requestDate(Instant requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Long getViewed() {
        return viewed;
    }

    public Request viewed(Long viewed) {
        this.viewed = viewed;
        return this;
    }

    public void setViewed(Long viewed) {
        this.viewed = viewed;
    }

    public Status getStatus() {
        return status;
    }

    public Request status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTravelerid() {
        return travelerid;
    }

    public Request travelerid(Long travelerid) {
        this.travelerid = travelerid;
        return this;
    }

    public void setTravelerid(Long travelerid) {
        this.travelerid = travelerid;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public Request offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public Request addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setRequest(this);
        return this;
    }

    public Request removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setRequest(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Request notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Request addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setRequest(this);
        return this;
    }

    public Request removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setRequest(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public User getUser() {
        return user;
    }

    public Request user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public Request product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", requestDate='" + getRequestDate() + "'" +
            ", viewed=" + getViewed() +
            ", status='" + getStatus() + "'" +
            ", travelerid=" + getTravelerid() +
            "}";
    }
}
