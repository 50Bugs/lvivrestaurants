package com.restaurants.lvivrestaurants.domain;

import jakarta.persistence.*;
import java.util.Set;


@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String rating;
    private String averagePrice;
    private String websiteURL;
    @Column(length = 600)
    private String imageURL;
    @Column(length = 800)
    private String googleMapsURL;
    //@Column(length = 500)
    private String menuURL;
    @Column(length = 8000)
    private String info;

    private String cuisine;
    private String schedule;

    public Restaurant() {
        name = "";
        address = "";
        phone = "";
        email = "";
        websiteURL = "";
        info = "";
        imageURL = "";
        rating = "";
        googleMapsURL = "";
        averagePrice = "";
        menuURL = "";
        cuisine = "";
        schedule = "";
    }

    public Restaurant(String name, String address, String phone, String email,
                      String websiteURL, String info, String imageURL) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.websiteURL = websiteURL;
        this.info = info;
        this.imageURL = imageURL;
        rating = "";
        googleMapsURL = "";
        averagePrice = "";
        menuURL = "";
        cuisine = "";
        schedule = "";
    }

    public Restaurant(String name, String address, String phone, String websiteURL, String imageURL,
                      String googleMapsURL, String menuURL, String rating, String averagePrice, String info) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.websiteURL = websiteURL;
        this.imageURL = imageURL;
        this.googleMapsURL = googleMapsURL;
        this.menuURL = menuURL;
        this.rating = rating;
        this.averagePrice = averagePrice;
        this.info = info;
        email = "";
        cuisine = "";
        schedule = "";
    }

    public Restaurant(String name, String address, String phone, String info,
                      String imageURL, String rating, String averagePrice, String cuisine, String schedule) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.info = info;
        this.imageURL = imageURL;
        this.rating = rating;
        this.averagePrice = averagePrice;
        this.cuisine = cuisine;
        this.schedule = schedule;
        googleMapsURL = "";
        email = "";
        websiteURL = "";
        menuURL = "";
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", rating='" + rating + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                ", websiteURL='" + websiteURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", googleMapsURL='" + googleMapsURL + '\'' +
                ", menuURL='" + menuURL + '\'' +
                ", info='" + info + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", schedule='" + schedule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String getGoogleMapsURL() {
        return googleMapsURL;
    }

    public void setGoogleMapsURL(String googleMapsURL) {
        this.googleMapsURL = googleMapsURL;
    }

    public String getMenuURL() {
        return menuURL;
    }

    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
