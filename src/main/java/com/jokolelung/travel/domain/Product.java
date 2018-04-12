package com.jokolelung.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.jokolelung.travel.domain.enumeration.Size;

/**
 * Product
 */
@ApiModel(description = "Product")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productname")
    private String productname;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "url")
    private String url;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "spesialtreatment")
    private Boolean spesialtreatment;

    @Column(name = "fragile")
    private Boolean fragile;

    @Column(name = "productweight")
    private Long productweight;

    @Enumerated(EnumType.STRING)
    @Column(name = "productsize")
    private Size productsize;

    @Column(name = "productprice")
    private Double productprice;

    @Column(name = "pruducttip")
    private Double pruducttip;

    @Column(name = "additionalcharge")
    private Double additionalcharge;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreOrder> preOrders = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private City city;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public Product productname(String productname) {
        this.productname = productname;
        return this;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public Product url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuantity() {
        return quantity;
    }

    public Product quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean isSpesialtreatment() {
        return spesialtreatment;
    }

    public Product spesialtreatment(Boolean spesialtreatment) {
        this.spesialtreatment = spesialtreatment;
        return this;
    }

    public void setSpesialtreatment(Boolean spesialtreatment) {
        this.spesialtreatment = spesialtreatment;
    }

    public Boolean isFragile() {
        return fragile;
    }

    public Product fragile(Boolean fragile) {
        this.fragile = fragile;
        return this;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public Long getProductweight() {
        return productweight;
    }

    public Product productweight(Long productweight) {
        this.productweight = productweight;
        return this;
    }

    public void setProductweight(Long productweight) {
        this.productweight = productweight;
    }

    public Size getProductsize() {
        return productsize;
    }

    public Product productsize(Size productsize) {
        this.productsize = productsize;
        return this;
    }

    public void setProductsize(Size productsize) {
        this.productsize = productsize;
    }

    public Double getProductprice() {
        return productprice;
    }

    public Product productprice(Double productprice) {
        this.productprice = productprice;
        return this;
    }

    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }

    public Double getPruducttip() {
        return pruducttip;
    }

    public Product pruducttip(Double pruducttip) {
        this.pruducttip = pruducttip;
        return this;
    }

    public void setPruducttip(Double pruducttip) {
        this.pruducttip = pruducttip;
    }

    public Double getAdditionalcharge() {
        return additionalcharge;
    }

    public Product additionalcharge(Double additionalcharge) {
        this.additionalcharge = additionalcharge;
        return this;
    }

    public void setAdditionalcharge(Double additionalcharge) {
        this.additionalcharge = additionalcharge;
    }

    public Double getTotal() {
        return total;
    }

    public Product total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<ProductImage> getProductImages() {
        return productImages;
    }

    public Product productImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
        return this;
    }

    public Product addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProduct(this);
        return this;
    }

    public Product removeProductImage(ProductImage productImage) {
        this.productImages.remove(productImage);
        productImage.setProduct(null);
        return this;
    }

    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public Product requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public Product addRequest(Request request) {
        this.requests.add(request);
        request.setProduct(this);
        return this;
    }

    public Product removeRequest(Request request) {
        this.requests.remove(request);
        request.setProduct(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<PreOrder> getPreOrders() {
        return preOrders;
    }

    public Product preOrders(Set<PreOrder> preOrders) {
        this.preOrders = preOrders;
        return this;
    }

    public Product addPreOrder(PreOrder preOrder) {
        this.preOrders.add(preOrder);
        preOrder.setProduct(this);
        return this;
    }

    public Product removePreOrder(PreOrder preOrder) {
        this.preOrders.remove(preOrder);
        preOrder.setProduct(null);
        return this;
    }

    public void setPreOrders(Set<PreOrder> preOrders) {
        this.preOrders = preOrders;
    }

    public User getUser() {
        return user;
    }

    public Product user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public Product city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public Product country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Product currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
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
