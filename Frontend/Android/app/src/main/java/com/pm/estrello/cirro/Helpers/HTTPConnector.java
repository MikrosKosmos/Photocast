package com.pm.estrello.cirro.Helpers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pm.estrello.cirro.Objects.SingleTon;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HTTPConnector {
    private String TAG_CLASS = HTTPConnector.class.getSimpleName();
    private Context context;
    private String queryURL;
    private ResponseListener responseListener;
    private String jwToken;

    public HTTPConnector(Context context, String queryURL, String jwToken, ResponseListener responseListener) {
        this.context = context;
        this.queryURL = queryURL;
        this.jwToken = jwToken;
        this.responseListener = responseListener;
    }

    /**
     * Method to make the HTTP POST Request.
     *
     * @param TAG: The Tag for the Request.
     */
    public void makeQuery(String TAG, JSONObject postData) {
        JsonObjectRequest request = new JsonObjectRequest(queryURL, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (responseListener != null) {
                    responseListener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onErrorResponse(error);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.API_KEY_HEADER, Constants.API_KEY_VALUE);
                params.put(Constants.API_JWT_TOKEN_KEY, jwToken);
                return params;
            }
        };
        request.setTag(TAG);
        SingleTon.getInstance(context.getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Method to make a HTTP GET Request.
     *
     * @param TAG: The Request TAG.
     */
    public void makeQuery(String TAG) {
        JsonObjectRequest request = new JsonObjectRequest(queryURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (responseListener != null) {
                    responseListener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onErrorResponse(error);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.API_KEY_HEADER, Constants.API_KEY_VALUE);
                params.put(Constants.API_JWT_TOKEN_KEY, jwToken);
                return params;
            }
        };
        request.setTag(TAG);
        SingleTon.getInstance(context.getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Method to make a PUT Request.
     *
     * @param postData: The post body.
     * @param isPut:    true to make a out request.
     */
    public void makeQuery(JSONObject postData, boolean isPut) {
        if (isPut) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, queryURL, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (responseListener != null) {
                        responseListener.onResponse(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (responseListener != null) {
                        responseListener.onErrorResponse(error);
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constants.API_KEY_HEADER, Constants.API_KEY_VALUE);
                    params.put(Constants.API_JWT_TOKEN_KEY, jwToken);
                    return params;
                }
            };
            SingleTon.getInstance(context).addToRequestQueue(request);
        }
    }

    public interface ResponseListener {
        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);
    }
}
