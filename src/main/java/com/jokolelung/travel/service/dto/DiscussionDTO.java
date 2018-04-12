package com.jokolelung.travel.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Discussion entity.
 */
public class DiscussionDTO implements Serializable {

    private Long id;

    @Lob
    private String text;

    private Instant postDate;

    private Long requestid;

    private Long preorderid;

    private Long userId;

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

    public Long getRequestid() {
        return requestid;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getPreorderid() {
        return preorderid;
    }

    public void setPreorderid(Long preorderid) {
        this.preorderid = preorderid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscussionDTO discussionDTO = (DiscussionDTO) o;
        if(discussionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discussionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscussionDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            "}";
    }
}
