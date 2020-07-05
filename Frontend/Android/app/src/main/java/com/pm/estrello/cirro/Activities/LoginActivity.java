package com.pm.estrello.cirro.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.pm.estrello.cirro.Objects.DigitText;
import com.pm.estrello.cirro.R;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout _otpLayout;
    private AppCompatEditText _mobileNumber;
    private DigitText _otp1, _otp2, _otp3, _otp4;
    private AppCompatButton _loginButton;
    private AppCompatButton _signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
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
     * @param editText: The edit text to be checked.
     * @return true: if empty, else false.
     */
    private boolean isNotEmpty(AppCompatEditText editText) {
        return !TextUtils.isEmpty(editText.getText());
    }

    private void changeActivity() {
        //TODO:
    }
}
