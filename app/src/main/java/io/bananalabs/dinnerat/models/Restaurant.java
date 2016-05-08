package io.bananalabs.dinnerat.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import za.co.cporm.model.CPDefaultRecord;
import za.co.cporm.model.CPOrm;
import za.co.cporm.model.annotation.Column.Column;
import za.co.cporm.model.annotation.Column.Unique;
import za.co.cporm.model.annotation.Table;
import za.co.cporm.model.query.Select;

/**
 * Created by EDC on 5/1/16.
 */
@Table
public class Restaurant extends CPDefaultRecord {

    @SerializedName("id")
    @Column
    @Unique
    private Long restaurantId;
    @SerializedName("name")
    @Column
    private String name;
    @SerializedName("address")
    @Column
    private String address;
    @SerializedName("city")
    @Column
    private String city;
    @SerializedName("state")
    @Column
    private String state;
    @SerializedName("area")
    @Column
    private String area;
    @SerializedName("postal_code")
    @Column
    private String zip;
    @SerializedName("country")
    @Column
    private String country;
    @SerializedName("phone")
    @Column
    private String phone;
    @SerializedName("price")
    @Column
    private String price;
    @SerializedName("lat")
    @Column
    private Double latitude;
    @SerializedName("lon")
    @Column
    private Double longitude;
    @SerializedName("reserve_url")
    @Column
    private String reserveUrl;
    @SerializedName("mobile_reserve_url")
    @Column
    private String mobileReserveUrl;
    @SerializedName("image_url")
    @Column
    private String imageUrl;

    public Restaurant() {
        this.restaurantId = (long) 0;
        this.name = "";
        this.address = "";
        this.city = "";
        this.state = "NY";
        this.area = "";
        this.zip = "";
        this.country = "";
        this.phone = "";
        this.price = "";
        this.latitude = (double) 0;
        this.longitude = (double) 0;
        this.reserveUrl = "";
        this.mobileReserveUrl = "";
        this.imageUrl = "";
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

    @Nullable
    public static Restaurant findById(Long id) {
        return Restaurant.findById(Restaurant.class, id);
    }

    @Override
    public String toString() {
        if (name != null)
            return name;
        return super.toString();
    }

    @Override
    public void save() {
        save(CPOrm.getApplicationContext());
    }

    @Override
    public void save(Context context) {
        Restaurant r = Select.from(Restaurant.class).whereEquals("name", this.name).first();
        if (r != null) {
            this._id = r._id;
        }
        super.save(context);
    }

    /*
                Accessors
                 */
    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getReserveUrl() {
        return reserveUrl;
    }

    public void setReserveUrl(String reserveUrl) {
        this.reserveUrl = reserveUrl;
    }

    public String getMobileReserveUrl() {
        return mobileReserveUrl;
    }

    public void setMobileReserveUrl(String mobileReserveUrl) {
        this.mobileReserveUrl = mobileReserveUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static class Contract {
        public static final String COL_ID = "_id";
        public static final String COL_RESTAURANT_ID = "restaurant_id";
        public static final String COL_NAME = "name";
        public static final String COL_ADDRESS = "address";
        public static final String COL_CITY = "city";
        public static final String COL_STATE = "state";
        public static final String COL_AREA = "area";
        public static final String COL_ZIP = "zip";
        public static final String COL_COUNTRY = "country";
        public static final String COL_PHONE = "phone";
        public static final String COL_PRICE = "price";
        public static final String COL_LATITUDE = "latitude";
        public static final String COL_LONGITUDE = "longitude";
        public static final String COL_RESERVE_URL = "reserve_url";
        public static final String COL_MOBILE_RESERVE_URL = "mobile_reserve_url";
        public static final String COL_IMAGE_URL = "image_url";
    }
}
