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
 * Inbox
 */
@ApiModel(description = "Inbox")
@Entity
@Table(name = "inbox")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "subject")
    private String subject;

    @Column(name = "post_date")
    private Instant postDate;

    @OneToMany(mappedBy = "inbox")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Messages> messages = new HashSet<>();

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Inbox subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public Inbox postDate(Instant postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
    }

    public Set<Messages> getMessages() {
        return messages;
    }

    public Inbox messages(Set<Messages> messages) {
        this.messages = messages;
        return this;
    }

    public Inbox addMessages(Messages messages) {
        this.messages.add(messages);
        messages.setInbox(this);
        return this;
    }

    public Inbox removeMessages(Messages messages) {
        this.messages.remove(messages);
        messages.setInbox(null);
        return this;
    }

    public void setMessages(Set<Messages> messages) {
        this.messages = messages;
    }

    public User getSender() {
        return sender;
    }

    public Inbox sender(User user) {
        this.sender = user;
        return this;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public User getReceiver() {
        return receiver;
    }

    public Inbox receiver(User user) {
        this.receiver = user;
        return this;
    }

    public void setReceiver(User user) {
        this.receiver = user;
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
        Inbox inbox = (Inbox) o;
        if (inbox.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inbox.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inbox{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", postDate='" + getPostDate() + "'" +
            "}";
    }
}
