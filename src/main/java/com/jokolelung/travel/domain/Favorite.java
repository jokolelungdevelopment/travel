package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Favorite
 */
@ApiModel(description = "Favorite")
@Entity
@Table(name = "favorite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requestid")
    private Long requestid;

    @Column(name = "preorderid")
    private Long preorderid;

    @ManyToOne
    private User user;

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

    public Favorite requestid(Long requestid) {
        this.requestid = requestid;
        return this;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getPreorderid() {
        return preorderid;
    }

    public Favorite preorderid(Long preorderid) {
        this.preorderid = preorderid;
        return this;
    }

    public void setPreorderid(Long preorderid) {
        this.preorderid = preorderid;
    }

    public User getUser() {
        return user;
    }

    public Favorite user(User user) {
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
        Favorite favorite = (Favorite) o;
        if (favorite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favorite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Favorite{" +
            "id=" + getId() +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            "}";
    }
}
