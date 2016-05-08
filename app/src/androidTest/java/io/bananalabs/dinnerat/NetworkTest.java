package io.bananalabs.dinnerat;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.bananalabs.dinnerat.models.requests.RestaurantRequest;
import io.bananalabs.dinnerat.network.ClientUsage;

/**
 * Created by EDC on 5/3/16.
 */
public class NetworkTest extends AndroidTestCase {

    public static boolean connectionDone = false;

    public void testRestaurantRequest() {
        final CountDownLatch latch = new CountDownLatch(1);
        try {
            ClientUsage.requestRestaurant(
                    getContext(),
                    new RestaurantRequest("Locanda", "NY"),
                    this,
                    new Response.Listener<RestaurantRequest.RestaurantResponse>() {
                        @Override
                        public void onResponse(RestaurantRequest.RestaurantResponse response) {
                            assertTrue(response != null);
                            assertTrue(response.totalEntries > 0);
                            connectionDone = true;
                            latch.countDown();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null)
                                Log.e(NetworkTest.class.getSimpleName(),
                                        "" + error.networkResponse.statusCode + ": " + error.getMessage());
                            else {
                                Log.e(NetworkTest.class.getSimpleName(), "No internet permission granted.");
                            }

                            latch.countDown();
                            connectionDone = false;
                        }
                    }
            );
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        try {
            latch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            Log.e(NetworkTest.class.getSimpleName(), ie.getMessage());
        }

        assertTrue(connectionDone);

    }

    static boolean connectionDoneB = false;

    public void testB_fetchRestaurants() throws UnsupportedEncodingException {
        final CountDownLatch latch = new CountDownLatch(1);
        ClientUsage.requestRestaurants(
                getContext(),
                new RestaurantRequest(),
                this,
                new Response.Listener<RestaurantRequest.RestaurantResponse>() {
                    @Override
                    public void onResponse(RestaurantRequest.RestaurantResponse response) {
                        connectionDoneB = true;
                        assertNotNull(response);
                        assertNotNull(response.restaurants);
                        assertTrue(response.restaurants.size() == 100);
                        latch.countDown();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        connectionDoneB = true;
                        error.printStackTrace();
                        assertTrue(false);
                        latch.countDown();
                    }
                }
        );
        try {
            latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        assertTrue(connectionDoneB);
    }
}
