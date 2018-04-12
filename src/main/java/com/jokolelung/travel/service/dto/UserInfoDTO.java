package com.jokolelung.travel.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.jokolelung.travel.domain.enumeration.StatusUser;

/**
 * A DTO for the UserInfo entity.
 */
public class UserInfoDTO implements Serializable {

    private Long id;

    private String fullname;

    private String birthdate;

    private String phoneNumber;

    private StatusUser status;

    private Double balance;

    private String imgurl;

    @Lob
    private String gmailToken;

    @Lob
    private String facebookToken;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StatusUser getStatus() {
        return status;
    }

    public void setStatus(StatusUser status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGmailToken() {
        return gmailToken;
    }

    public void setGmailToken(String gmailToken) {
        this.gmailToken = gmailToken;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
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

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if(userInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
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
