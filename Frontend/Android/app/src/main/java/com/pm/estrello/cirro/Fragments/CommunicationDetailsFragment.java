package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.R;

public class CommunicationDetailsFragment extends Fragment {
    private AppCompatEditText _phoneNumber;
    private AppCompatEditText _emailId;
    private MaterialButton _nextButton;
    private MaterialButton _backButton;

    public CommunicationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communication_details, container, false);
        _emailId = view.findViewById(R.id.emailId);
        _phoneNumber = view.findViewById(R.id.phoneNumber);
        _nextButton = view.findViewById(R.id.communicationNextButton);
        _backButton = view.findViewById(R.id.communicationBackButton);
        return view;
    }
}