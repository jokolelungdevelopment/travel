package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Transaction
 */
@ApiModel(description = "Transaction")
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requestid")
    private Long requestid;

    @Column(name = "preorderid")
    private Long preorderid;

    @Lob
    @Column(name = "deliveryto")
    private String deliveryto;

    @Column(name = "qty")
    private Long qty;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestid() {
        return requestid;
    }

    public Transaction requestid(Long requestid) {
        this.requestid = requestid;
        return this;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getPreorderid() {
        return preorderid;
    }

    public Transaction preorderid(Long preorderid) {
        this.preorderid = preorderid;
        return this;
    }

    public void setPreorderid(Long preorderid) {
        this.preorderid = preorderid;
    }

    public String getDeliveryto() {
        return deliveryto;
    }

    public Transaction deliveryto(String deliveryto) {
        this.deliveryto = deliveryto;
        return this;
    }

    public void setDeliveryto(String deliveryto) {
        this.deliveryto = deliveryto;
    }

    public Long getQty() {
        return qty;
    }

    public Transaction qty(Long qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public Transaction price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Transaction currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            ", deliveryto='" + getDeliveryto() + "'" +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            "}";
    }
}
