package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.R;

public class CommunicationDetailsFragment extends Fragment {
    private AppCompatEditText _phoneNumber;
    private AppCompatEditText _emailId;
    private AppCompatImageView _nextButton;
    private MaterialButton _backButton;
    private String TAG_CLASS = CommunicationDetailsFragment.class.getSimpleName();
    private int requestCode;

    public CommunicationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communication_details, container, false);
        Bundle incomingBundle = getArguments();
        _emailId = view.findViewById(R.id.emailId);
        _phoneNumber = view.findViewById(R.id.phoneNumber);
        _nextButton = view.findViewById(R.id.communicationNextButton);
        _backButton = view.findViewById(R.id.communicationBackButton);
        _nextButton.setOnClickListener(v -> {
            if (Utils.isNotEmpty(new AppCompatEditText[]{_phoneNumber})) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PHONE_NUMBER, "+91" + _phoneNumber.getText().toString());
                if (Utils.isNotEmpty(new AppCompatEditText[]{_emailId}))
                    bundle.putString(Constants.EMAIL, _emailId.getText().toString());
                bundle.putString(Constants.FIRST_NAME, incomingBundle.getString(Constants.FIRST_NAME));
                bundle.putString(Constants.LAST_NAME, incomingBundle.getString(Constants.LAST_NAME));
                bundle.putString(Constants.GENDER, incomingBundle.getString(Constants.GENDER));
                bundle.putString(Constants.ROLE, incomingBundle.getString(Constants.ROLE));
                Navigation.findNavController(view).navigate(R.id.action_communicationDetailsFragment_to_addressFragment, bundle);
            } else {
                Messages.toast(getActivity(), "Fill in all the details", false);
            }
        });
        _backButton.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });
        return view;
    }
}