package io.bananalabs.dinnerat;

import android.test.AndroidTestCase;

import java.util.List;

import io.bananalabs.dinnerat.models.Restaurant;
import za.co.cporm.model.query.Select;

/**
 * Created by EDC on 5/1/16.
 */
public class DatabaseTest extends AndroidTestCase {

    public void testA_Insert() {
        createRestaurantOne().save();
        createRestaurantTwo().save();
        Utilities.delay();
        List<Restaurant> restaurants = Select.from(Restaurant.class).queryAsList();
        assertNotNull(restaurants);
        assertTrue(restaurants.size() >= 2);
    }

    public void testB_Fetch() {
        Restaurant restaurant = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurant);
    }

    public void testC_Update() {
        Restaurant restaurant = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurant);
        restaurant.setCountry("DR");
        restaurant.save();
        Utilities.delay();
        Restaurant restaurantUpdated = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurantUpdated);
        assertEquals(restaurantUpdated.getCountry(), "DR");
    }

    private Restaurant createRestaurantOne() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(ID_1);
        restaurant.setName(NAME_1);
        restaurant.setCity(CITY_1);
        restaurant.setState(STATE_1);
        restaurant.setZip(ZIP_1);
        restaurant.setPrice(PRICE_1);
        restaurant.setPhone(PHONE_1);
        restaurant.setLatitude(LATITUDE_1);
        restaurant.setLongitude(LONGITUDE_1);
        return restaurant;
    }

    private Restaurant createRestaurantTwo() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(ID_2);
        restaurant.setName(NAME_2);
        restaurant.setCity(CITY_2);
        restaurant.setState(STATE_2);
        restaurant.setZip(ZIP_2);
        restaurant.setPrice(PRICE_2);
        restaurant.setPhone(PHONE_2);
        restaurant.setLatitude(LATITUDE_2);
        restaurant.setLongitude(LONGITUDE_2);
        return restaurant;
    }

    public static final Long ID_1 = (long) 970023;
    public static final String NAME_1 = "#1 GARDEN CHINESE";
    public static final String CITY_1 = "BROOKLYN";
    public static final String STATE_1 = "NY";
    public static final String ZIP_1 = "11215";
    public static final String PRICE_1 = "0";
    public static final String PHONE_1 = "7188321795";
    public static final Double LATITUDE_1 = (double) 40.6601;
    public static final Double LONGITUDE_1 = (double) -73.9803;

    public static final Long ID_2 = (long) 970024;
    public static final String NAME_2 = "#1 SABOR LATINO RESTAURANT";
    public static final String CITY_2 = "'BRONX'";
    public static final String STATE_2 = "NY";
    public static final String ZIP_2 = "10466";
    public static final String PRICE_2 = "";
    public static final String PHONE_2 = "7186532222";
    public static final Double LATITUDE_2 = (double) 40.7523;
    public static final Double LONGITUDE_2 = (double) -73.9876;

}
