package io.bananalabs.dinnerat.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ernesto De los Santos Cordero on 10/22/15.
 */
public class ServerConnection {

    private static ServerConnection sServerConnection;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private ServerConnection(Context context) {
        mContext = context;
//        mRequestQueue = new RequestQueue();
    }

    public static synchronized ServerConnection getInstace(Context context) {
        if (sServerConnection == null) {
            sServerConnection = new ServerConnection(context);
        }
        return sServerConnection;
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (req.getTag() != null)
//            getmRequestQueue().cancelAll(req.getTag());

        getmRequestQueue().add(req);
    }

    public void removeRequestWithTag(@NonNull Object tag) {
        getmRequestQueue().cancelAll(tag);
    }

    public void removeRequestWithTags(@NonNull Object... tags) {
        for (Object tag : tags) {
            removeRequestWithTag(tag);
        }
    }

}
