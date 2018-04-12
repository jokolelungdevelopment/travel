package com.jokolelung.travel.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.jokolelung.travel.domain.enumeration.Size;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String productname;

    @Lob
    private String description;

    private String brand;

    private String url;

    private String quantity;

    private Boolean spesialtreatment;

    private Boolean fragile;

    private Long productweight;

    private Size productsize;

    private Double productprice;

    private Double pruducttip;

    private Double additionalcharge;

    private Double total;

    private Long userId;

    private Long categoryId;

    private Long cityId;

    private Long countryId;

    private Long currencyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean isSpesialtreatment() {
        return spesialtreatment;
    }

    public void setSpesialtreatment(Boolean spesialtreatment) {
        this.spesialtreatment = spesialtreatment;
    }

    public Boolean isFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public Long getProductweight() {
        return productweight;
    }

    public void setProductweight(Long productweight) {
        this.productweight = productweight;
    }

    public Size getProductsize() {
        return productsize;
    }

    public void setProductsize(Size productsize) {
        this.productsize = productsize;
    }

    public Double getProductprice() {
        return productprice;
    }

    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }

    public Double getPruducttip() {
        return pruducttip;
    }

    public void setPruducttip(Double pruducttip) {
        this.pruducttip = pruducttip;
    }

    public Double getAdditionalcharge() {
        return additionalcharge;
    }

    public void setAdditionalcharge(Double additionalcharge) {
        this.additionalcharge = additionalcharge;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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

        ProductDTO productDTO = (ProductDTO) o;
        if(productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", productname='" + getProductname() + "'" +
            ", description='" + getDescription() + "'" +
            ", brand='" + getBrand() + "'" +
            ", url='" + getUrl() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", spesialtreatment='" + isSpesialtreatment() + "'" +
            ", fragile='" + isFragile() + "'" +
            ", productweight=" + getProductweight() +
            ", productsize='" + getProductsize() + "'" +
            ", productprice=" + getProductprice() +
            ", pruducttip=" + getPruducttip() +
            ", additionalcharge=" + getAdditionalcharge() +
            ", total=" + getTotal() +
            "}";
    }
}
