package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.R;

public class BasicDetailsFragment extends Fragment {
    private AppCompatEditText _firstName;
    private AppCompatEditText _lastName;
    private RadioGroup _radioGroup;
    private MaterialButton _nextButton;
    private MaterialButton _backButton;

    public BasicDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);
        _firstName = view.findViewById(R.id.firstName);
        _lastName = view.findViewById(R.id.lastName);
        _radioGroup = view.findViewById(R.id.genderRadioGroup);
        return view;
    }
}