package com.jokolelung.travel.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Inbox entity.
 */
public class InboxDTO implements Serializable {

    private Long id;

    @Lob
    private String subject;

    private Instant postDate;

    private Long senderId;

    private Long receiverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long userId) {
        this.senderId = userId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long userId) {
        this.receiverId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InboxDTO inboxDTO = (InboxDTO) o;
        if(inboxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inboxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InboxDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", postDate='" + getPostDate() + "'" +
            "}";
    }
}
