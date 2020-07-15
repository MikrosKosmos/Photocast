package com.pm.estrello.cirro.Helpers;

public class Constants {
    /**
     * Internal request code.
     */
    public static final int OTP_REQUEST_CODE = 1;
    public static final int OTP_VERIFY_CODE = 2;
    public static final int PERMISSION_CODE = 3;
    public static final int GET_STATE_CODE = 4;
    public static final int GET_CITY_CODE = 5;
    public static final int CUSTOMER_REGISTER_CODE = 6;
    public static final int CUSTOMER_ADDRESS_CODE = 7;

    /**
     * Generic Messages.
     */
    public static final String API_RESPONSE_ERROR = "Ops, Something went wrong!";
    public static final String INCORRECT_OTP = "Incorrect OTP";
    public static final String LOADING_MESSAGE = "Please wait, while we are crunching our servers...";
    public static final String LOCATING_MESSAGE = "Please wait, while we are finding you...";

    /**
     * General Keys.
     */
    public static final String SHARED_PREFERENCE_NAME = "cirroPrefs";
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_PROFILE = "userProfile";
    public static final String CUSTOMER_ADDRESS = "customerAddress";
    public static final String[] VENDOR_TYPES = new String[]{"Vendor Type:", "Freelancer", "Company"};
    public static final String[] DOCUMENT_TYPES = new String[]{"Document Type:", "AADHAAR", "PANCARD"};
    /**
     * API Keys.
     */
    public static final String API_KEY_HEADER = "key";
    public static final String API_JWT_TOKEN_KEY = "jw_token";
    public static final String API_RESPONSE_KEY = "res";
    public static final String API_TOKEN = "ub3QgZ2l2a";
    public static final String API_URL = "https://815eu0hqnd.execute-api.ap-south-1.amazonaws.com/Development/";

    public static final String PHONE_NUMBER = "phone_number";
    public static final String OTP = "otp";
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ROLE = "role";
    public static final String ROLE_VENDOR = "tbl_VendorMaster";
    public static final String ROLE_CUSTOMER = "tbl_CustomerMaster";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";
    public static final String STATUS_NAME = "status_name";
    public static final String STATUS_ID = "status_id";
    public static final String REFERRAL_CODE = "referral_code";
    public static final String USED_REFERRAL_CODE = "used_referral_code";

    public static final String CUSTOMER_ID = "customer_id";

    public static final String CITY_NAME = "city_name";
    public static final String STATE_ID = "state_id";
    public static final String STATE_NAME = "state_name";

    public static final String ADDRESS_1 = "address_1";
    public static final String ADDRESS_2 = "address_2";
    public static final String CITY = "city";
    public static final String PINCODE = "pincode";
    public static final String GPS_LAT = "gps_lat";
    public static final String GPS_LONG = "gps_long";
}
