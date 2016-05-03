package io.bananalabs.dinnerat.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ernesto De los Santos Cordero on 10/22/15.
 */
public class GsonRequest<T> extends Request<T> {

    private final String LOG_TAG = GsonRequest.class.getSimpleName();

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    private final Class<T> clazz;
    private final HashMap<String, String> headers;
    private final Response.Listener<T> listener;
    private final Object parameters;


    /**
     * Make a request and return a parsed object from JSON.
     *
     * @param method  Request type
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Object parameters, Class<T> clazz, HashMap<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.parameters = parameters;
        this.listener = listener;
        this.gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'");

    }

    public GsonRequest addDeserializer(@NonNull JsonDeserializer deserializer, Type typeOfT) {
        this.gsonBuilder.registerTypeAdapter(typeOfT, deserializer);
//        this.gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'");
        return this;
    }

    @Override
    public byte[] getBody() {
        if (gson == null) {
//            gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'");
            gson = gsonBuilder.create();
        }

        try {
            return gson.toJson(this.parameters).getBytes(getParamsEncoding());
        } catch (NullPointerException | UnsupportedEncodingException e) {
            Log.d(LOG_TAG, e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (json.charAt(0) == '[') {
                json = String.format("{\"obj\" : %s }", json);
            }

            if (gson == null)
                gson = gsonBuilder.create();

            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException uee) {
            return Response.error(new ParseError(uee));
        } catch (JsonSyntaxException jsr) {
            jsr.printStackTrace();
            return Response.error(new ParseError(jsr));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.d(GsonRequest.class.getSimpleName(), volleyError.getMessage());
        if (volleyError.networkResponse != null)
            if (volleyError.networkResponse.statusCode == 510) {
                return new VolleyError("510: Request error");
            }
        return super.parseNetworkError(volleyError);
    }
}
