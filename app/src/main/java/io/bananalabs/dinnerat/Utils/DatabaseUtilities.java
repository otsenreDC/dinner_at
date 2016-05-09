package io.bananalabs.dinnerat.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.bananalabs.dinnerat.R;
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
    public static int totalEntries = 0;

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
                totalEntries = response.totalEntries;
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

    public static ProgressDialog mProgressDialog;
    public static int processed = 0;
    public void storeRestaurants(final List<Restaurant> restaurants, final int page) {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.setMax(totalEntries);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage(mContext.getString(R.string.saving_database));
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                }
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                for (Restaurant restaurant : restaurants) {
                    restaurant.save();
                    publishProgress(1);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                processed += values[0];
                mProgressDialog.incrementProgressBy(values[0]);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(processed >= totalEntries) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                }
            }
        }.execute();

    }

    public void cancelRequest() {
        ServerConnection.getInstace(mContext).removeRequestWithTag(this);
    }
}
