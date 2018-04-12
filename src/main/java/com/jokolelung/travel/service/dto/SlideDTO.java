package com.jokolelung.travel.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Slide entity.
 */
public class SlideDTO implements Serializable {

    private Long id;

    private Long imgurl;

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImgurl() {
        return imgurl;
    }

    public void setImgurl(Long imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SlideDTO slideDTO = (SlideDTO) o;
        if(slideDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slideDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SlideDTO{" +
            "id=" + getId() +
            ", imgurl=" + getImgurl() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
