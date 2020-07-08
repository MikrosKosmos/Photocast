package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.R;

public class AddressFragment extends Fragment {
    private AppCompatEditText _address1, _address2, _pincode;
    private MaterialButton _nextButton, _backButton;
    private Spinner _stateSpinner, _citySpinner;

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
        return view;
    }
}