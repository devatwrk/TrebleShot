package com.genonbeta.TrebleShot.callback;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.genonbeta.TrebleShot.fragment.MapFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class HttpRequestUtilsZerox {

    private RequestQueue requestQueue;
    private Context mContext;
    JsonObjectRequest jsonObjectRequest;

    public HttpRequestUtilsZerox(Context mContext) {
        this.mContext = mContext;
        this.requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();

    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public JsonObjectRequest executeBackendAPICall(String url, int httpMethodNum, JSONObject requestBody) {
        //Request.Method.GET
         jsonObjectRequest = new JsonObjectRequest
                (httpMethodNum, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(" Request made successfully");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(" Request failed to execute ");

                    }
                });

// Access the RequestQueue through your singleton class.

        return jsonObjectRequest;
    }

}
