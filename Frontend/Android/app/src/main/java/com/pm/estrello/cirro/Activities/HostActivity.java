package com.pm.estrello.cirro.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.R;

public class HostActivity extends AppCompatActivity {
    private BottomNavigationView _navBar;
    private String TAG_CLASS = HostActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        initializeViews();
    }

    /**
     * Method to get the Role of the user.
     */
    private void getRole() {
        String role = DataStore.getData(this, Constants.ROLE);
        if (role.equals(Constants.ROLE_CUSTOMER)) {
            _navBar.getMenu().removeItem(R.id.addImage);
        }
    }

    /**
     * Method to initialize views.
     */
    private void initializeViews() {
        _navBar = findViewById(R.id.bottomNavBar);
        getRole();
    }
}