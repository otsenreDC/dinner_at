package io.bananalabs.dinnerat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.bananalabs.dinnerat.MainActivity;
import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.models.requests.RestaurantRequest;
import io.bananalabs.dinnerat.network.ClientUsage;
import io.bananalabs.dinnerat.network.ServerConnection;

/**
 * Created by Ernesto De los Santos on 5/7/16.
 */
public class DatabaseUtilities {

    private Context mContext;

    public DatabaseUtilities(Context context) {
        this.mContext = context;
    }

    public boolean isDatabaseLoaded() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sp.getBoolean("database_status", false);
    }

    public void setDatabaseLoaded(boolean status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        sp.edit().putBoolean("database_status", status).apply();
    }

    /**
     *
     * @return true if fetching
     */
    public boolean populateDatabase() {
        boolean fetching = false;
        if ((fetching = !isDatabaseLoaded()))
            requestRestaurants();

        return fetching;
    }

    public static int page = 1;
    public static int perPage = 100;
    public static int entriesCounter = 0;

    private void requestRestaurants() {
        RestaurantRequest request = new RestaurantRequest();
        request.page = page++;
        request.perPage = perPage;
        try {
            ClientUsage.requestRestaurants(
                    this.mContext,
                    request,
                    this,
                    response,
                    error
            );
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

    }

    Response.Listener<RestaurantRequest.RestaurantResponse> response = new Response.Listener<RestaurantRequest.RestaurantResponse>() {
        @Override
        public void onResponse(RestaurantRequest.RestaurantResponse response) {
            if (response != null && response.restaurants != null) {
                entriesCounter += response.restaurants.size();
                storeRestaurants(response.restaurants, page);
                if (entriesCounter < response.totalEntries) {
                    requestRestaurants();
                } else {
                    setDatabaseLoaded(true);
                }
            }
        }
    };

    Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    public void storeRestaurants(final List<Restaurant> restaurants, final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d("ASYNC TASK", "PAGE " + page);
                for (Restaurant restaurant : restaurants)
                    restaurant.save();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(MainActivity.class.getSimpleName(), "DONE" + page);

            }
        }.execute();

    }

    public void cancelRequest() {
        ServerConnection.getInstace(mContext).removeRequestWithTag(this);
    }
}
