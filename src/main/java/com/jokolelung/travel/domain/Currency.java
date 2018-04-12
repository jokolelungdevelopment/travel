package com.jokolelung.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Currency
 */
@ApiModel(description = "Currency")
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_value")
    private String value;

    @OneToMany(mappedBy = "currency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "currency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Currency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public Currency value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Currency products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Currency addProduct(Product product) {
        this.products.add(product);
        product.setCurrency(this);
        return this;
    }

    public Currency removeProduct(Product product) {
        this.products.remove(product);
        product.setCurrency(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Currency transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Currency addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setCurrency(this);
        return this;
    }

    public Currency removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setCurrency(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
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
        Currency currency = (Currency) o;
        if (currency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
