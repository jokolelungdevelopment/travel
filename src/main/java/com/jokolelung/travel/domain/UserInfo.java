package com.jokolelung.travel.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.jokolelung.travel.domain.enumeration.StatusUser;

/**
 * UserInfo
 */
@ApiModel(description = "UserInfo")
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusUser status;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "imgurl")
    private String imgurl;

    @Lob
    @Column(name = "gmail_token")
    private String gmailToken;

    @Lob
    @Column(name = "facebook_token")
    private String facebookToken;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public UserInfo fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public UserInfo birthdate(String birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StatusUser getStatus() {
        return status;
    }

    public UserInfo status(StatusUser status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusUser status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public UserInfo balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getImgurl() {
        return imgurl;
    }

    public UserInfo imgurl(String imgurl) {
        this.imgurl = imgurl;
        return this;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGmailToken() {
        return gmailToken;
    }

    public UserInfo gmailToken(String gmailToken) {
        this.gmailToken = gmailToken;
        return this;
    }

    public void setGmailToken(String gmailToken) {
        this.gmailToken = gmailToken;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public UserInfo facebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
        return this;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public User getUser() {
        return user;
    }

    public UserInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserInfo userInfo = (UserInfo) o;
        if (userInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", balance=" + getBalance() +
            ", imgurl='" + getImgurl() + "'" +
            ", gmailToken='" + getGmailToken() + "'" +
            ", facebookToken='" + getFacebookToken() + "'" +
            "}";
    }
}
