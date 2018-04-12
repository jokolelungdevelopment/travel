package com.jokolelung.travel.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Messages entity.
 */
public class MessagesDTO implements Serializable {

    private Long id;

    @Lob
    private String text;

    private Instant postDate;

    private Long userId;

    private Long inboxId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInboxId() {
        return inboxId;
    }

    public void setInboxId(Long inboxId) {
        this.inboxId = inboxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessagesDTO messagesDTO = (MessagesDTO) o;
        if(messagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessagesDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", postDate='" + getPostDate() + "'" +
            "}";
    }
}
