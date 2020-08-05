package com.pm.estrello.cirro.Objects;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleTon {
    private static SingleTon singleTon;
    private Context context;
    private RequestQueue requestQueue;

    private SingleTon(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Method to get the Instance
     *
     * @param context: The Context of the Application.
     * @return singleTon: The SingleTon Object.
     */
    public static synchronized SingleTon getInstance(Context context) {
        if (singleTon == null) {
            singleTon = new SingleTon(context.getApplicationContext());
        }
        return singleTon;
    }

    /**
     * Method to initialize the Request Queue.
     *
     * @return requestQueue: The current request queue.
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    /**
     * Method to add to the Request Queue, with the Default Retry Policy.
     *
     * @param request: The Request<T> which is to be added.
     */
    public void addToRequestQueue(Request request) {
        requestQueue.add(request).setRetryPolicy(new DefaultRetryPolicy());
    }
}
