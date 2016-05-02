package io.bananalabs.dinnerat.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import za.co.cporm.model.CPDefaultRecord;
import za.co.cporm.model.annotation.Column.Column;
import za.co.cporm.model.annotation.Table;
import za.co.cporm.model.query.Select;

/**
 * Created by EDC on 5/1/16.
 */
@Table
public class Restaurant extends CPDefaultRecord<Restaurant> {
    @Column
    private Long restaurantId;
    @Column
    private String name;
    @Column
    private String street;
    @Column
    private String building;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private Integer zip;
    @Column
    private String grade;
    @Column
    private Integer gradedate;
    @Column
    private String price;
    @Column
    private String phone;
    @Column
    private String cuisine;
    @Column
    private String violation;
    @Column
    private Float latitude;
    @Column
    private Float longitude;
    @Column
    private Float total_rating;
    @Column
    private Integer count;
    @Column
    private Boolean approved;

    public Restaurant() {
        this.restaurantId = (long) 0;
        this.name = "";
        this.street = "";
        this.building = "";
        this.city = "";
        this.state = "NY";
        this.zip = 0;
        this.grade = "";
        this.gradedate = 0;
        this.price = "";
        this.phone = "";
        this.cuisine = "";
        this.violation = "";
        this.latitude = (float) 0;
        this.longitude = (float) 0;
        this.total_rating = (float) 0;
        this.count = 0;
        this.approved = true;
    }

    /*
    Class methods
     */
    @Nullable
    public static Restaurant fetchByName(@NonNull String name) {
        return Select.from(Restaurant.class).whereLike("name", name).first();
    }

    @Nullable
    public static List<Restaurant> fetchAllRestaurants() {
        return Select.from(Restaurant.class).queryAsList();
    }




    /*
    Accessors
     */
    public Restaurant(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getGradedate() {
        return gradedate;
    }

    public void setGradedate(Integer gradedate) {
        this.gradedate = gradedate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getTotal_rating() {
        return total_rating;
    }

    public void setTotalRating(Float total_rating) {
        this.total_rating = total_rating;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
