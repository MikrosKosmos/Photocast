package com.pm.estrello.cirro.Helpers;

import com.pm.estrello.cirro.Objects.Address;
import com.pm.estrello.cirro.Objects.Authentication;

import org.json.JSONObject;

public class ParamsCreator {
    private static String TAG_CLASS = ParamsCreator.class.getSimpleName();

    /**
     * Method to get the JSON for OTP request.
     *
     * @param authentication: The authentication object.
     * @return jsonObject.
     */
    public static JSONObject getJSONForOTPRequest(Authentication authentication) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.PHONE_NUMBER, "+91" + authentication.getPhoneNumber());
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return jsonObject;
    }

    /**
     * Method to get the JSON for OTP verification,
     *
     * @param authentication: The authentication object.
     * @return jsonObject.
     */
    public static JSONObject getJSONForOTPVerification(Authentication authentication) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.PHONE_NUMBER, authentication.getPhoneNumber());
            jsonObject.put(Constants.PHONE_NUMBER, authentication.getOtp());
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return jsonObject;
    }

    /**
     * Method to get the params for Customer Address.
     *
     * @param address:    The address of the customer.
     * @param customerId: The customer id.
     * @return jsonObject.
     */
    public static JSONObject getJSONForCustomerAddress(Address address, int customerId) {
        try {
            JSONObject jsonObject = new JSONObject(address.toString());
            jsonObject.put(Constants.CUSTOMER_ID, customerId);
            return jsonObject;
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return null;
    }
}
