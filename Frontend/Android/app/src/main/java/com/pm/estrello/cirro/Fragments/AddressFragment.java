package com.pm.estrello.cirro.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Objects.City;
import com.pm.estrello.cirro.Objects.State;
import com.pm.estrello.cirro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressFragment extends Fragment implements HTTPConnector.ResponseListener {
    private AppCompatEditText _address1, _address2, _pincode;
    private MaterialButton _nextButton, _backButton;
    private Spinner _stateSpinner, _citySpinner;
    private String TAG_CLASS = AddressFragment.class.getSimpleName();
    private int requestCode;
    private ProgressDialog _progressDialog;
    private ArrayList<State> states;
    private ArrayList<City> cities;
    private State selectedState;
    private City selectedCity;


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
        _backButton = view.findViewById(R.id.addressBackButton);
        _nextButton = view.findViewById(R.id.addressNextButton);
        _citySpinner = view.findViewById(R.id.cityDropDown);
        _stateSpinner = view.findViewById(R.id.stateDropDown);
        initializeViews();
        getStates();
        return view;
    }

    /**
     * Method to initialize the widgets and views.
     */
    private void initializeViews() {
        _progressDialog = new ProgressDialog(getContext());
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        _stateSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedState = states.get(position);
            getCities(selectedState);
        });
        _citySpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = cities.get(position);
        });
        _backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        _nextButton.setOnClickListener(v -> {

        });
    }

    /**
     * Method to create a bundle for sending to the next fragment.
     *
     * @return bundle: containing all the details.
     */
    private Bundle createSendingBundle() {
        Bundle incomingBundle = getArguments();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PHONE_NUMBER, incomingBundle.getString(Constants.PHONE_NUMBER));
        bundle.putString(Constants.EMAIL, incomingBundle.getString(Constants.EMAIL));
        bundle.putString(Constants.FIRST_NAME, incomingBundle.getString(Constants.FIRST_NAME));
        bundle.putString(Constants.LAST_NAME, incomingBundle.getString(Constants.LAST_NAME));
        bundle.putString(Constants.GENDER, incomingBundle.getString(Constants.GENDER));
        bundle.putString(Constants.ROLE, incomingBundle.getString(Constants.ROLE));
        return bundle;
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
}