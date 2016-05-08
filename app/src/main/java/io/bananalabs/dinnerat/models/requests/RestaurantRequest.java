package io.bananalabs.dinnerat.models.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.bananalabs.dinnerat.models.Restaurant;

/**
 * Created by EDC on 5/3/16.
 */
public class RestaurantRequest {

    public String name;
    public String state;
    public Integer perPage;
    public Integer page;

    public RestaurantRequest() {
        name = "";
        state = "NY";
        perPage = 100;
        page = 1;
    }

    public RestaurantRequest(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public RestaurantRequest(String name, String state, Integer perPage, Integer page) {
        this.name = name;
        this.state = state;
        this.perPage = perPage;
        this.page = page;
    }

    public static class RestaurantResponse {
        @SerializedName("total_entries")
        public int totalEntries;
        @SerializedName("per_page")
        public int perPage;
        @SerializedName("current_page")
        public int currentPage;
        @SerializedName("restaurants")
        public List<Restaurant> restaurants;
    }

}
