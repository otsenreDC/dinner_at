package io.bananalabs.dinnerat.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Locale;

import io.bananalabs.dinnerat.models.requests.RestaurantRequest;

/**
 * Created by Ernesto De los Santos Cordero on 04/08/16.
 */
public class ClientUsage {

    public static final String RESULT_OK = "ok";
    public static final String RESULT_NOT_OK = "not ok";

    public static final String URL = "https://opentable.herokuapp.com";
    public static final String API_RESTAURANT = "/api/restaurants?name=%s&state=%s";

    public static void connection(Context context, String path, int method, Object request, Object tag, Class<?> responseClass, Response.Listener listener, Response.ErrorListener errorListener) {
        GsonRequest gsonRequest = new GsonRequest<>(method, URL + path, request, responseClass, null, listener, errorListener);
        gsonRequest.setTag(tag);

        ServerConnection.getInstace(context).addToRequestQueue(gsonRequest);
    }

    public static void requestResturant (Context context, RestaurantRequest request, Object tag, Response.Listener<RestaurantRequest.RestaurantResponse> listener, Response.ErrorListener errorListener) {
        connection(
                context,
                String.format(Locale.US, API_RESTAURANT, request.name, request.state),
                Request.Method.GET,
                null,
                tag,
                RestaurantRequest.RestaurantResponse.class,
                listener,
                errorListener);
    }

    private static HashMap<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");

        return headers;
    }

    private static HashMap<String, String> createHeaders(@NonNull String accessToken) {
        HashMap<String, String> headers = createHeaders();
        headers.put("Authorization", "Bearer " + accessToken);
        return headers;
    }
}