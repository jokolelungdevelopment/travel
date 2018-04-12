package com.jokolelung.travel.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.jokolelung.travel.domain.enumeration.Status;

/**
 * A DTO for the Request entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    private Instant requestDate;

    private Long viewed;

    private Status status;

    private Long travelerid;

    private Long userId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Long getViewed() {
        return viewed;
    }

    public void setViewed(Long viewed) {
        this.viewed = viewed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTravelerid() {
        return travelerid;
    }

    public void setTravelerid(Long travelerid) {
        this.travelerid = travelerid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if(requestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", requestDate='" + getRequestDate() + "'" +
            ", viewed=" + getViewed() +
            ", status='" + getStatus() + "'" +
            ", travelerid=" + getTravelerid() +
            "}";
    }
}
