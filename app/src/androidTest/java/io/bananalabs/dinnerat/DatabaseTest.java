package io.bananalabs.dinnerat;

import android.test.AndroidTestCase;

import java.util.List;

import io.bananalabs.dinnerat.Utils.DatabaseInitialization;
import io.bananalabs.dinnerat.models.Restaurant;
import za.co.cporm.model.CPOrm;
import za.co.cporm.model.query.Select;

/**
 * Created by EDC on 5/1/16.
 *
 */
public class DatabaseTest extends AndroidTestCase {

    public void testA_Insert() {
        createRestaurantOne().save();
        createRestaurantTwo().save();
        Utilities.delay();
        List<Restaurant> restaurants = Select.from(Restaurant.class).queryAsList();
        assertNotNull(restaurants);
        assertTrue(restaurants.size() > 2);
    }

    public void testB_Fetch() {
        Restaurant restaurant = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurant);
    }

    public void testC_Update() {
        Restaurant restaurant = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurant);
        restaurant.setGrade("B");
        restaurant.save();
        Utilities.delay();
        Restaurant restaurantUpdated = Restaurant.fetchByName(NAME_1);
        assertNotNull(restaurantUpdated);
        assertEquals(restaurantUpdated.getGrade(), "B");
    }

    public void testAA_BulkInsert() {
        CPOrm.deleteAll(Restaurant.class);
        DatabaseInitialization databaseInitialization = new DatabaseInitialization(getContext());
        assertTrue(databaseInitialization.insertRestaurants());
        assertTrue(databaseInitialization.closeDataBase());

        List<Restaurant> restaurants = Restaurant.fetchAllRestaurants();
        assertNotNull(restaurants);
        assertTrue(restaurants.size() == 200);

        Restaurant restaurant = Restaurant.fetchByName("28 MR. MINGS CAFFE");
        assertNotNull(restaurant);
        assertEquals("NY", restaurant.getState());
    }

    private Restaurant createRestaurantOne() {
        Restaurant restaurant = new Restaurant(ID_1);
        restaurant.setName(NAME_1);
        restaurant.setStreet(STREET_1);
        restaurant.setBuilding(BUILDING_1);
        restaurant.setCity(CITY_1);
        restaurant.setState(STATE_1);
        restaurant.setZip(ZIP_1);
        restaurant.setGrade(GRADE_1);
        restaurant.setGradedate(GRADEDATE_1);
        restaurant.setPrice(PRICE_1);
        restaurant.setPhone(PHONE_1);
        restaurant.setCuisine(CUISINE_1);
        restaurant.setViolation(VIOLATION_1);
        restaurant.setLatitude(LATITUDE_1);
        restaurant.setLongitude(LONGITUDE_1);
        restaurant.setTotalRating(TOTAL_RATING_1);
        restaurant.setCount(COUNT_1);
        restaurant.setApproved(APPROVED_1);
        return restaurant;
    }

    private Restaurant createRestaurantTwo() {
        Restaurant restaurant = new Restaurant(ID_2);
        restaurant.setName(NAME_2);
        restaurant.setStreet(STREET_2);
        restaurant.setBuilding(BUILDING_2);
        restaurant.setCity(CITY_2);
        restaurant.setState(STATE_2);
        restaurant.setZip(ZIP_2);
        restaurant.setGrade(GRADE_2);
        restaurant.setGradedate(GRADEDATE_2);
        restaurant.setPrice(PRICE_2);
        restaurant.setPhone(PHONE_2);
        restaurant.setCuisine(CUISINE_2);
        restaurant.setViolation(VIOLATION_2);
        restaurant.setLatitude(LATITUDE_2);
        restaurant.setLongitude(LONGITUDE_2);
        restaurant.setTotalRating(TOTAL_RATING_2);
        restaurant.setCount(COUNT_2);
        restaurant.setApproved(APPROVED_2);
        return restaurant;
    }

    public static final Long ID_1 = (long) 970023;
    public static final String NAME_1 = "#1 GARDEN CHINESE";
    public static final String STREET_1 = "PROSPECT PARK WEST";
    public static final String BUILDING_1 = "221";
    public static final String CITY_1 = "BROOKLYN";
    public static final String STATE_1 = "NY";
    public static final Integer ZIP_1 = 11215;
    public static final String GRADE_1 = "A";
    public static final Integer GRADEDATE_1 = 42100;
    public static final String PRICE_1 = "0";
    public static final String PHONE_1 = "7188321795";
    public static final String CUISINE_1 = "Chinese";
    public static final String VIOLATION_1 = "Violation Comments: Cold food item held above 41Âº F (smoked fish and reduced oxygen packaged foods above 38 ÂºF) except during necessary preparation. Critical Action: Critical";
    public static final Float LATITUDE_1 = (float) 40.6601;
    public static final Float LONGITUDE_1 = (float) -73.9803;
    public static final Float TOTAL_RATING_1 = (float) 0;
    public static final Integer COUNT_1 = 0;
    public static final Boolean APPROVED_1 = true;

    public static final Long ID_2 = (long) 970024;
    public static final String NAME_2 = "#1 SABOR LATINO RESTAURANT";
    public static final String STREET_2 = "WHITE PLAINS ROAD";
    public static final String BUILDING_2 = "'4120'";
    public static final String CITY_2 = "'BRONX'";
    public static final String STATE_2 = "NY";
    public static final Integer ZIP_2 = 10466;
    public static final String GRADE_2 = "A";
    public static final Integer GRADEDATE_2 = 41303;
    public static final String PRICE_2 = "";
    public static final String PHONE_2 = "7186532222";
    public static final String CUISINE_2 = "Latin (Cuban, Dominican, Puerto Rican, South & Central American)";
    public static final String VIOLATION_2 = "Violation Comments: Filth flies or food/refuse/sewage-associated (FRSA) flies present in facilityZs food and/or non-food areas. Filth flies include house flies, little house flies, blow flies, bottle flies and flesh flies. Food/refuse/sewage-associated flies include fruit flies, drain flies and Phorid flies. Critical Action: Critical";
    public static final Float LATITUDE_2 = (float) 40.7523;
    public static final Float LONGITUDE_2 = (float) -73.9876;
    public static final Float TOTAL_RATING_2 = (float) 0;
    public static final Integer COUNT_2 = 0;
    public static final Boolean APPROVED_2 = true;

}
