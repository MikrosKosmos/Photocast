package com.pm.estrello.cirro.Helpers;

import org.json.JSONObject;

public class ParamsCreator {
    private static String TAG_CLASS = ParamsCreator.class.getSimpleName();

    public static JSONObject getJSONForOTPRequest(String phoneNumber) {
        JSONObject jsonObject = new JSONObject();
        try {

        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return jsonObject;
    }
}
