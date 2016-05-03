package io.bananalabs.dinnerat.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by EDC on 4/13/16.
 */
public class FileRequest extends Request<byte[]> {

    private Response.Listener<byte[]> mListener;

    public FileRequest(String url, Response.Listener<byte[]>listener, Response.ErrorListener errorListener) {
        super (Method.GET, url, errorListener);
        this.mListener = listener;

    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(networkResponse.data, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }
}
