package com.pm.estrello.cirro.Helpers;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatEditText;

import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.Objects.Vendor;

import org.json.JSONObject;

public class Utils {
    private static String TAG_CLASS = Utils.class.getSimpleName();

    /**
     * Method to check whether the edit texts are not empty.
     *
     * @param editTexts: The Edit text to be checked.
     * @return true if none are empty, else false.
     */
    public static boolean isNotEmpty(AppCompatEditText[] editTexts) {
        for (AppCompatEditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to get the user id.
     *
     * @param context: The Application context.
     * @return user id.
     */
    public static int getUserId(Context context) {
        try {
            String role = DataStore.getData(context, Constants.ROLE);
            if (role.equals(Constants.ROLE_CUSTOMER)) {
                Customer customer = new Customer(
                        new JSONObject(DataStore.getData(context, Constants.USER_PROFILE)));
                return customer.getId();
            } else if (role.equals(Constants.ROLE_VENDOR)) {
                Vendor vendor = new Vendor(
                        new JSONObject(DataStore.getData(context, Constants.USER_PROFILE)));
                return vendor.getId();
            }
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return -1;
    }

    /**
     * Method to get the JW Token of the logged in user.
     *
     * @param context: The Application context.
     * @return String JWToken.
     */
    public static String getJWToken(Context context) {
        return DataStore.getData(context, Constants.API_JWT_TOKEN_KEY);
    }
}
