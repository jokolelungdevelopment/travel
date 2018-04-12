package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Messages
 */
@ApiModel(description = "Messages")
@Entity
@Table(name = "messages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "post_date")
    private Instant postDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Inbox inbox;

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

    public Messages text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public Messages postDate(Instant postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return user;
    }

    public Messages user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Inbox getInbox() {
        return inbox;
    }

    public Messages inbox(Inbox inbox) {
        this.inbox = inbox;
        return this;
    }

    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
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
        Messages messages = (Messages) o;
        if (messages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Messages{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", postDate='" + getPostDate() + "'" +
            "}";
    }
}
