package io.bananalabs.dinnerat;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

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
        ClientUsage.requestResturant(
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
        try {
            latch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            Log.e(NetworkTest.class.getSimpleName(), ie.getMessage());
        }

        assertTrue(connectionDone);

    }
}
