package com.pm.estrello.cirro.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Messages {
    /**
     * Method to log messages.
     *
     * @param TAG: The tag.
     * @param msg: The message.
     */
    public static void log(String TAG, String msg) {
        Log.d(TAG, msg);
    }

    /**
     * Method to show a toast.
     *
     * @param context: The context for the toast.
     * @param msg:     the message of the toast.
     * @param isLong:  true for long duration else false.
     */
    public static void toast(Context context, String msg, boolean isLong) {
        if (isLong)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
