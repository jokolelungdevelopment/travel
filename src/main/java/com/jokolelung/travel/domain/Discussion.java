package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Discussion
 */
@ApiModel(description = "Discussion")
@Entity
@Table(name = "discussion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Discussion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "post_date")
    private Instant postDate;

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

    public String getText() {
        return text;
    }

    public Discussion text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public Discussion postDate(Instant postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
    }

    public Long getRequestid() {
        return requestid;
    }

    public Discussion requestid(Long requestid) {
        this.requestid = requestid;
        return this;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getPreorderid() {
        return preorderid;
    }

    public Discussion preorderid(Long preorderid) {
        this.preorderid = preorderid;
        return this;
    }

    public void setPreorderid(Long preorderid) {
        this.preorderid = preorderid;
    }

    public User getUser() {
        return user;
    }

    public Discussion user(User user) {
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
        Discussion discussion = (Discussion) o;
        if (discussion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discussion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discussion{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            "}";
    }
}
