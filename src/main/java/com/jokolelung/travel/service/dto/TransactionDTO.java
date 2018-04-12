package com.jokolelung.travel.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Transaction entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    private Long requestid;

    private Long preorderid;

    @Lob
    private String deliveryto;

    private Long qty;

    private Double price;

    private Long currencyId;

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

    public String getDeliveryto() {
        return deliveryto;
    }

    public void setDeliveryto(String deliveryto) {
        this.deliveryto = deliveryto;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if(transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", requestid=" + getRequestid() +
            ", preorderid=" + getPreorderid() +
            ", deliveryto='" + getDeliveryto() + "'" +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            "}";
    }
}
