package com.pm.estrello.cirro.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
   private String TAG_CLASS = SplashScreen.class.getSimpleName();
   private boolean isPermissionGranted = false;
   private boolean isVersionChecked = false;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash);
   }

   /**
    * Method to check the version of the app.
    */
   private void checkVersion() {
      try {
         PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
         String url = Constants.API_URL + "version";
         HTTPConnector connector = new HTTPConnector(this, url, "",
                 new HTTPConnector.ResponseListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       //TODO:
                       isVersionChecked = true;
                       changeActivity();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       //TODO:
                    }
                 });
      } catch (PackageManager.NameNotFoundException e) {
         Messages.log(TAG_CLASS, e.toString());
      }
   }

   private void checkPermission() {
      List<String> permissionsNeeded = new ArrayList<>();
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
              != PackageManager.PERMISSION_GRANTED) {
         permissionsNeeded.add(Manifest.permission.CAMERA);
      }
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
         permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
      }
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {
         permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      }
      if (permissionsNeeded.size() > 0) {
         ActivityCompat.requestPermissions(this,
                 permissionsNeeded.toArray(new String[0]), Constants.PERMISSION_CODE);
      }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      for (int result : grantResults) {
         if (result == PackageManager.PERMISSION_DENIED) {
            checkPermission();
            return;
         }
      }
      isPermissionGranted = true;
      changeActivity();
   }

   /**
    * Method to change the activity.
    */
   private void changeActivity() {
      if (isPermissionGranted && isVersionChecked) {
         Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
         startActivity(intent);
      }
   }
}
