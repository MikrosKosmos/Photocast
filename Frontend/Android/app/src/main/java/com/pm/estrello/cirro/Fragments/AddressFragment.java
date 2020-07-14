package com.pm.estrello.cirro.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.Objects.City;
import com.pm.estrello.cirro.Objects.State;
import com.pm.estrello.cirro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressFragment extends Fragment implements HTTPConnector.ResponseListener, LocationListener {
    private AppCompatEditText _address1, _address2, _pincode;
    private AppCompatImageView _nextButton;
    private Spinner _stateSpinner, _citySpinner;
    private AppCompatTextView _lat, _long;
    private String TAG_CLASS = AddressFragment.class.getSimpleName();
    private int requestCode;
    private ProgressDialog _progressDialog;
    private ArrayList<State> states;
    private ArrayList<City> cities;
    private State selectedState = null;
    private City selectedCity = null;
    private double latitude = 0.0, longitude = 0.0;
    private LocationManager locationManager;


    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        _address1 = view.findViewById(R.id.address1);
        _address2 = view.findViewById(R.id.address2);
        _pincode = view.findViewById(R.id.pincode);
        _nextButton = view.findViewById(R.id.addressNextButton);
        _citySpinner = view.findViewById(R.id.cityDropDown);
        _stateSpinner = view.findViewById(R.id.stateDropDown);
        _lat = view.findViewById(R.id.latCoordinateTextView);
        _long = view.findViewById(R.id.longCoordinateTextView);
        initializeViews();
        getStates();
        getLocation(false);
        return view;
    }

    /**
     * Method to initialize the widgets and views.
     */
    private void initializeViews() {
        _progressDialog = new ProgressDialog(getContext());
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        _stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = states.get(position);
                getCities(selectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _nextButton.setOnClickListener(v -> {
            AppCompatEditText[] editTexts = new AppCompatEditText[]{_address2, _address1, _pincode};
            if (Utils.isNotEmpty(editTexts) && selectedCity != null && selectedState != null && latitude > 0 && longitude > 0) {
                Navigation.findNavController(v).navigate(R.id.action_addressFragment_to_otherDetailsFragment, createSendingBundle());
            } else {
                Messages.toast(requireActivity().getApplicationContext(), "Please fill in all the details", false);
                getLocation(true);
            }
        });
    }

    /**
     * Method to fetch the location of the device.
     */
    @SuppressLint("MissingPermission")
    private void getLocation(boolean isWaiting) {
        if (isWaiting) {
            _progressDialog.setTitle(Constants.LOCATING_MESSAGE);
            _progressDialog.show();
        }
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, this);
    }

    /**
     * Method to create a bundle for sending to the next fragment.
     *
     * @return bundle: containing all the details.
     */
    private Bundle createSendingBundle() {
        Bundle incomingBundle = getArguments();
        Bundle bundle = new Bundle();
        if (incomingBundle != null) {
            bundle.putString(Constants.PHONE_NUMBER, incomingBundle.getString(Constants.PHONE_NUMBER));
            bundle.putString(Constants.EMAIL, incomingBundle.getString(Constants.EMAIL));
            bundle.putString(Constants.FIRST_NAME, incomingBundle.getString(Constants.FIRST_NAME));
            bundle.putString(Constants.LAST_NAME, incomingBundle.getString(Constants.LAST_NAME));
            bundle.putString(Constants.GENDER, incomingBundle.getString(Constants.GENDER));
            bundle.putString(Constants.ROLE, incomingBundle.getString(Constants.ROLE));
            bundle.putString(Constants.ADDRESS_1, _address1.getText().toString());
            bundle.putString(Constants.ADDRESS_2, _address2.getText().toString());
            bundle.putString(Constants.PINCODE, _pincode.getText().toString());
            bundle.putInt(Constants.CITY, selectedCity.getCityId());
            bundle.putDouble(Constants.GPS_LAT, latitude);
            bundle.putDouble(Constants.GPS_LONG, longitude);
            return bundle;
        }
        return null;
    }

    /**
     * Method to get the cities for the selected state.
     *
     * @param state: The selected state.
     */
    private void getCities(State state) {
        String url = Constants.API_URL + "city?" + Constants.STATE_ID + "=" + state.getStateId();
        HTTPConnector connector = new HTTPConnector(requireActivity(), url, "", this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
        requestCode = Constants.GET_CITY_CODE;
    }

    /**
     * Method to get the list of states.
     */
    private void getStates() {
        String url = Constants.API_URL + "city/state";
        HTTPConnector connector = new HTTPConnector(getContext(), url, "", this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
        requestCode = Constants.GET_STATE_CODE;
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {
            if (requestCode == Constants.GET_STATE_CODE) {
                JSONArray stateArray = response.getJSONArray(Constants.API_RESPONSE_KEY);
                states = new ArrayList<>();
                for (int i = 0; i < stateArray.length(); i++) {
                    JSONObject oneState = stateArray.getJSONObject(i);
                    states.add(new State(oneState.getInt(Constants.ID), oneState.getString(Constants.STATE_NAME)));
                }
                ArrayAdapter<State> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, states);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _stateSpinner.setAdapter(adapter);
            } else if (requestCode == Constants.GET_CITY_CODE) {
                JSONArray cityArray = response.getJSONArray(Constants.API_RESPONSE_KEY);
                cities = new ArrayList<>();
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject oneCity = cityArray.getJSONObject(i);
                    cities.add(new City(oneCity.getInt(Constants.ID), oneCity.getString(Constants.CITY_NAME)));
                }
                ArrayAdapter<City> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, cities);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _citySpinner.setAdapter(adapter);
            }
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(getContext(), Constants.API_RESPONSE_ERROR, false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        _progressDialog.dismiss();
        Messages.log(TAG_CLASS, error.toString());
        Messages.toast(getContext(), Constants.API_RESPONSE_ERROR, false);
    }

    @Override
    public void onLocationChanged(Location location) {
        _progressDialog.dismiss();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Messages.log(TAG_CLASS, "LATITUDE: " + latitude);
        Messages.log(TAG_CLASS, "LONGITUDE: " + longitude);
        _lat.setText(String.format("Latitude:%s", latitude));
        _long.setText(String.format("Longitude:%s", latitude));
        locationManager.removeUpdates(this);
        locationManager = null;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}