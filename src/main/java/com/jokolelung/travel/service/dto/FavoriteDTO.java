package com.jokolelung.travel.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Favorite entity.
 */
public class FavoriteDTO implements Serializable {

    private Long id;

    private Long requestid;

    private Long preorderid;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        FavoriteDTO favoriteDTO = (FavoriteDTO) o;
        if(favoriteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favoriteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FavoriteDTO{" +
            "id=" + getId() +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            "}";
    }
}
