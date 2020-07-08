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

public class OtherDetailsFragment extends Fragment {
    private Spinner _vendorType, _identityType;
    private AppCompatEditText _idDocNumber, _companyName;
    private MaterialButton _nextButton, _backButton;

    public OtherDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_details, container, false);
        _vendorType = view.findViewById(R.id.vendorTypeSpinner);
        _identityType = view.findViewById(R.id.documentTypeSpinner);
        _idDocNumber = view.findViewById(R.id.identificationDocumentNumber);
        _companyName = view.findViewById(R.id.companyName);
        _nextButton = view.findViewById(R.id.otherDetailsNext);
        _backButton = view.findViewById(R.id.otherDetailsBack);
        return view;
    }
}