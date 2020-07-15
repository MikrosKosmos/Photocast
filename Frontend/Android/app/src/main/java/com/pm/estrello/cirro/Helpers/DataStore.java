package com.pm.estrello.cirro.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStore {
    /**
     * Method to store String value in Shared Preference.
     *
     * @param context: The application Context.
     * @param key:     the key.
     * @param value:   the value.
     */
    public static void storeData(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Method to store Int value.
     *
     * @param context: The application Context.
     * @param key:     the key.
     * @param value:   the value.
     */
    public static void storeData(Context context, String key, int value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Method to store Boolean value.
     *
     * @param context: The application Context.
     * @param key:     the key.
     * @param value:   the value.
     */
    public static void storeData(Context context, String key, boolean value) {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Method to get string data.
     *
     * @param context: the application context.
     * @param key:     the key
     * @return string data.
     */
    public static String getData(Context context, String key) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    /**
     * Method to get int data.
     *
     * @param context: the application context.
     * @param key:     the key
     * @return string data.
     */
    public static int getData(Context context, String key, int isInt) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * Method to get boolean data.
     *
     * @param context: the application context.
     * @param key:     the key
     * @return string data.
     */
    public static boolean getData(Context context, String key, boolean isBoolean) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
