package com.pm.estrello.cirro.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.ParamsCreator;
import com.pm.estrello.cirro.Objects.Authentication;
import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.Objects.DigitText;
import com.pm.estrello.cirro.R;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements HTTPConnector.ResponseListener {
   private LinearLayout _otpLayout;
   private AppCompatEditText _mobileNumber;
   private DigitText _otp1, _otp2, _otp3, _otp4;
   private AppCompatButton _loginButton;
   private AppCompatButton _signUpButton;

   /**
    * Method to parse the OTP from the sms.
    *
    * @param smsText: The sms message.
    * @return otp code, else null.
    */
   private String parseOTP(String smsText) {
      if (smsText.length() > 0 && smsText.contains(":")) {
         return smsText.split(":")[1].trim();
      }
      return null;
   }

   private int requestCode = 0;
   private SmsVerifyCatcher smsVerifyCatcher;

   private String TAG_CLASS = LoginActivity.class.getSimpleName();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      initializeViews();
      _loginButton.setOnClickListener(view -> {
         _loginButton.setText(getResources().getString(R.string.verifyOtp));
         if (requestCode == 0 && isNotEmpty(new AppCompatEditText[]{_mobileNumber})) {
            requestOTP();
         } else if (requestCode == Constants.OTP_VERIFY_CODE &&
                 isNotEmpty(new AppCompatEditText[]{_otp1, _otp2, _otp3, _otp4})) {
            String otpValue = _otp1.getText().toString() + _otp2.getText().toString() +
                    _otp3.getText().toString() + _otp4.getText().toString();
            verifyOTP(otpValue);
         }
      });
      smsVerifyCatcher = new SmsVerifyCatcher(this, message -> {
         String otpValue = parseOTP(message);
         verifyOTP(otpValue);
      });
   }

   @Override
   protected void onStart() {
      super.onStart();
      smsVerifyCatcher.onStart();
   }

   @Override
   protected void onStop() {
      super.onStop();
      smsVerifyCatcher.onStop();
   }

   /**
    * Method to initialize views.
    */
   private void initializeViews() {
      _otpLayout = findViewById(R.id.otpLayout);
      _mobileNumber = findViewById(R.id.phoneNumber);
      _otp1 = findViewById(R.id.otp1);
      _otp2 = findViewById(R.id.otp2);
      _otp3 = findViewById(R.id.otp3);
      _otp4 = findViewById(R.id.otp4);
      _signUpButton = findViewById(R.id.goToSignUp);
      _otpLayout.setVisibility(View.INVISIBLE);
   }

   /**
    * Method to check whether the texts are empty or not.
    *
    * @param editTexts: The edit texts to be checked.
    * @return true: if empty, else false.
    */
   private boolean isNotEmpty(AppCompatEditText[] editTexts) {
      for (AppCompatEditText editText : editTexts) {
         return !TextUtils.isEmpty(editText.getText());
      }
      return false;
   }

   /**
    * Method to change the Activity.
    */
   private void changeActivity() {
      //TODO:
   }

   /**
    * Method to request OTP for user Login.
    */
   private void requestOTP() {
      Authentication authentication = new Authentication(_mobileNumber.getText().toString());
      String url = Constants.API_URL + "auth";
      HTTPConnector connector = new HTTPConnector(this, url, "", this);
      requestCode = Constants.OTP_REQUEST_CODE;
      connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForOTPRequest(authentication));
   }

   /**
    * Method to verify the OTP value.
    *
    * @param otpValue: The otp to be checked.
    */
   private void verifyOTP(String otpValue) {
      Authentication authentication = new Authentication(Integer.parseInt(otpValue));
      String url = Constants.API_URL + "auth";
      HTTPConnector connector = new HTTPConnector(this, url, "", this);
      requestCode = Constants.OTP_VERIFY_CODE;
      connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForOTPVerification(authentication));
   }

   /**
    * Method to parse the JSON Object for the user.
    *
    * @param jsonObject: The object to be parsed.
    */
   private void parseUser(JSONObject jsonObject) {
      try {
         String role = jsonObject.getString(Constants.ROLE);
         if (role.equals(Constants.ROLE_CUSTOMER)) {
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
            //TODO: Set Customer profile.
         } else if (role.equals(Constants.ROLE_VENDOR)) {
            //TODO:Parse Vendor user.
         }
      } catch (Exception e) {
         Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
         Messages.log(TAG_CLASS, e.toString());
      }
   }

   @Override
   public void onResponse(JSONObject response) {
      try {
         if (requestCode == Constants.OTP_REQUEST_CODE) {
            boolean isSuccess = response.getBoolean(Constants.API_RESPONSE_KEY);
            if (isSuccess) {
               Messages.toast(this, "OTP send!", false);
               _otpLayout.setVisibility(View.VISIBLE);
               _mobileNumber.setEnabled(false);
            } else {
               Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
               Messages.log(TAG_CLASS, response.toString());
            }
         } else if (requestCode == Constants.OTP_VERIFY_CODE) {
            int id = response.getInt(Constants.ID);
            if (id > 0) {
               parseUser(response);
            } else {
               _mobileNumber.setEnabled(true);
               _otpLayout.setVisibility(View.INVISIBLE);
               Messages.log(TAG_CLASS, response.toString());
               Messages.toast(this, Constants.INCORRECT_OTP, false);
            }
         }
      } catch (Exception e) {
         Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
         Messages.log(TAG_CLASS, e.toString());
         _otpLayout.setVisibility(View.INVISIBLE);
         _mobileNumber.setEnabled(true);
      }
   }

   @Override
   public void onErrorResponse(VolleyError error) {
      Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
      Messages.log(TAG_CLASS, error.networkResponse.toString());
      _otpLayout.setVisibility(View.INVISIBLE);
      _mobileNumber.setEnabled(true);
   }
}