package io.bananalabs.dinnerat.models.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by EDC on 5/3/16.
 */
public class RestaurantRequest {

    public String name;
    public String state;

    public RestaurantRequest() {
        name = "";
        state = "";
    }

    public RestaurantRequest(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public static class RestaurantResponse {
        @SerializedName("total_entries")
        public int totalEntries;
        @SerializedName("per_page")
        public int perPage;
        @SerializedName("current_page")
        public int currentPage;
        @SerializedName("restaurants")
        public List<OpenTableRestaurant> restaurants;
    }

    public static class OpenTableRestaurant {
        @SerializedName("id")
        public long id;
        @SerializedName("name")
        public String name;
        @SerializedName("mobile_reserve_url")
        public String mobileReserveUrl;
        @SerializedName("image_url")
        public String imageUrl;

        @Override
        public String toString() {
            if (name != null)
                return name;
            return super.toString();
        }
    }
}
