package com.pm.estrello.cirro.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.ParamsCreator;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.Objects.Authentication;
import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.Objects.DigitText;
import com.pm.estrello.cirro.Objects.Vendor;
import com.pm.estrello.cirro.R;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements HTTPConnector.ResponseListener {
    private LinearLayout _otpLayout;
    private AppCompatEditText _mobileNumber;
    private DigitText _otp1, _otp2, _otp3, _otp4;
    private AppCompatButton _loginButton;
    private AppCompatButton _signUpButton;
    private int requestCode = 0;
    private SmsVerifyCatcher smsVerifyCatcher;
    private String TAG_CLASS = LoginActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private List<DigitText> otpDigitTexts;
    private char[] mOTPChars = new char[4];

    /**
     * hides the keyboard from the specified {@code activity}
     *
     * @param activity the activity to hide the keyboard from
     */
    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        _loginButton.setOnClickListener(view -> {
            if (requestCode == 0 & Utils.isNotEmpty(new AppCompatEditText[]{_mobileNumber})) {
                requestOTP(_mobileNumber.getText().toString());
            } else if (requestCode == Constants.OTP_REQUEST_CODE) {
                //Code will still be OTP request before verification.
                String otp = getOTP();
                if (otp.length() == 4) {
                    verifyOtp(otp);
                } else {
                    Messages.toast(this, "Please enter a valid OTP.", false);
                }
            }
        });
    }

    /**
     * Method to initialize the views.
     */
    private void initializeViews() {
        _otpLayout = findViewById(R.id.otpLayout);
        _mobileNumber = findViewById(R.id.phoneNumber);
        _otp1 = findViewById(R.id.otp1);
        _otp2 = findViewById(R.id.otp2);
        _otp3 = findViewById(R.id.otp3);
        _otp4 = findViewById(R.id.otp4);
        _loginButton = findViewById(R.id.loginButton);
        _signUpButton = findViewById(R.id.goToSignUp);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(Constants.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        _otpLayout.setVisibility(View.INVISIBLE);
        otpDigitTexts = new ArrayList<>();
        Collections.addAll(otpDigitTexts, _otp1, _otp2, _otp3, _otp4);
        for (int i = 0; i < 4; i++) {
            final DigitText digit = otpDigitTexts.get(i);
            final int q = i;
            digit.addTextChangedListener(new TextWatcher() {
                int lenbef = 0;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    lenbef = s.length();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (lenbef == 0 && s.length() == 1) {
                        onDigitEntered(q);
                    }
                }
            });

            digit.setOnBackspaceListener(event -> {
                if (digit.getSelectionStart() == 0 && digit.getSelectionEnd() < 1) {
                    onDigitDeleted(q - 1);
                } else {
                    onDigitDeleted(q);
                }
            });
        }
    }

    /**
     * fired when an otp digit is entered at the specified {@code index}
     *
     * @param index the index of the entered digit
     */
    private void onDigitEntered(int index) {
        if (index > -1 && index < 4) {
            mOTPChars[index] = otpDigitTexts.get(index).getText().toString().charAt(0);
            if (index < 3) {
                otpDigitTexts.get(index + 1).requestFocus();
            } else {
                hideKeyboard(this);
            }
        }
    }

    /**
     * fired when an otp digit is deleted from the specified {@code index}
     *
     * @param index the index of the deleted digit
     */
    private void onDigitDeleted(int index) {
        if (index > -1 && index < 4) {
            mOTPChars[index] = Constants.DEFAULT_OTP_CHAR;
            otpDigitTexts.get(index).setText("");
            otpDigitTexts.get(index).requestFocus();
        }
    }

    /**
     * Method to request OTP Code.
     *
     * @param mobileNumber: the mobile number for OTP requests.
     */
    private void requestOTP(String mobileNumber) {
        String url = Constants.API_URL + "auth";
        Authentication authentication = new Authentication(mobileNumber);
        HTTPConnector connector = new HTTPConnector(this, url, "", this);
        requestCode = Constants.OTP_REQUEST_CODE;
        _mobileNumber.setEnabled(false);
        connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForOTPRequest(authentication));
        progressDialog.show();
        _loginButton.setText(getResources().getString(R.string.verifyOtp));
    }

    /**
     * Method to verify the OTP.
     *
     * @param otp: The OTP to be verified.
     */
    private void verifyOtp(String otp) {
        String url = Constants.API_URL + "auth";
        Authentication authentication = new Authentication(_mobileNumber.getText().toString(),
                Integer.parseInt(otp), "");
        HTTPConnector connector = new HTTPConnector(this, url, "", this);
        connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForOTPVerification(authentication));
        requestCode = Constants.OTP_VERIFY_CODE;
        progressDialog.show();
    }

    /**
     * Constructs and returns the OTP from character as String
     *
     * @return string containing the OTP.
     */
    private String getOTP() {
        return new String(mOTPChars);
    }

    /**
     * Method to reset the Activity.
     */
    private void reset() {
        requestCode = 0;
        _mobileNumber.getText().clear();
        _mobileNumber.setEnabled(true);
        _loginButton.setText(getResources().getString(R.string.requestotp));
        _otpLayout.setVisibility(View.INVISIBLE);
        progressDialog.dismiss();
    }

    /**
     * Method to change the activity.
     *
     * @param isCustomer: true for customer, else false.
     */
    private void changeActivity(boolean isCustomer) {
        //TODO:
    }

    /**
     * Method to parse the Vendor profile.
     *
     * @param jsonObject: The json object.
     */
    private void parseVendorProfile(JSONObject jsonObject) {
        try {
            Vendor vendor = new Vendor(jsonObject.getInt(Constants.ID),
                    jsonObject.getString(Constants.FIRST_NAME),
                    jsonObject.getString(Constants.LAST_NAME),
                    jsonObject.getString(Constants.EMAIL),
                    jsonObject.getString(Constants.PHONE_NUMBER),
                    jsonObject.getString(Constants.GENDER),
                    jsonObject.getString(Constants.STATUS_NAME),
                    jsonObject.getInt(Constants.STATUS_ID),
                    jsonObject.getString(Constants.VENDOR_TYPE),
                    jsonObject.getString(Constants.COMPANY_BRAND_NAME),
                    jsonObject.getString(Constants.ADDRESS_1),
                    jsonObject.getString(Constants.ADDRESS_2),
                    0, "",
                    jsonObject.getInt(Constants.PINCODE),
                    jsonObject.getDouble(Constants.GPS_LAT),
                    jsonObject.getDouble(Constants.GPS_LONG), "", "");
            String jwToken = jsonObject.getString(Constants.API_JWT_TOKEN_KEY);
            DataStore.storeData(this, Constants.API_JWT_TOKEN_KEY, jwToken);
            DataStore.storeData(this, Constants.USER_PROFILE, vendor.toString());
            DataStore.storeData(this, Constants.IS_LOGGED_IN, true);
            changeActivity(false);
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
        }

    }

    /**
     * Method to parse the Customer profile.
     *
     * @param jsonObject: The json object to be parsed.
     */
    private void parseCustomerProfile(JSONObject jsonObject) {
        try {
            Customer customer = new Customer(jsonObject.getInt(Constants.ID),
                    jsonObject.getString(Constants.FIRST_NAME),
                    jsonObject.getString(Constants.LAST_NAME),
                    jsonObject.getString(Constants.EMAIL),
                    jsonObject.getString(Constants.PHONE_NUMBER),
                    jsonObject.getString(Constants.GENDER),
                    jsonObject.getString(Constants.STATUS_NAME),
                    jsonObject.getInt(Constants.STATUS_ID),
                    jsonObject.getString(Constants.REFERRAL_CODE),
                    jsonObject.getString(Constants.USED_REFERRAL_CODE));
            String jwToken = jsonObject.getString(Constants.API_JWT_TOKEN_KEY);
            DataStore.storeData(this, Constants.API_JWT_TOKEN_KEY, jwToken);
            DataStore.storeData(this, Constants.USER_PROFILE, customer.toString());
            DataStore.storeData(this, Constants.IS_LOGGED_IN, true);
            changeActivity(true);
        } catch (Exception e) {
            Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
            Messages.log(TAG_CLASS, e.toString());
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        try {
            if (requestCode == Constants.OTP_REQUEST_CODE) {
                boolean isSend = response.getBoolean(Constants.API_RESPONSE_KEY);
                if (isSend) {
                    Messages.toast(this, "OTP Send.", false);
                    _otpLayout.setVisibility(View.VISIBLE);
                } else {
                    reset();
                    Messages.toast(this, Constants.INVALID_MOBILE_NUMBER, false);
                    _mobileNumber.getText().clear();
                }
            } else if (requestCode == Constants.OTP_VERIFY_CODE) {
                JSONObject jsonObject = response.getJSONObject(Constants.API_RESPONSE_KEY);
                String role = jsonObject.getString(Constants.ROLE);
                Messages.log(TAG_CLASS, jsonObject.toString());
                if (role.equals(Constants.ROLE_VENDOR)) {
                    parseVendorProfile(jsonObject);
                } else if (role.equals(Constants.ROLE_CUSTOMER)) {
                    parseCustomerProfile(jsonObject);
                }
            }
        } catch (Exception e) {
            reset();
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        reset();
        progressDialog.dismiss();
        Messages.log(TAG_CLASS, error.toString());
        Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
    }
}