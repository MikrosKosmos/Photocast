package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.R;

import java.util.Objects;

public class BasicDetailsFragment extends Fragment {
    private AppCompatEditText _firstName;
    private AppCompatEditText _lastName;
    private RadioGroup _radioGroup;
    private MaterialButton _nextButton;
    private MaterialButton _backButton;
    private char gender;
    private NavController navController;

    public BasicDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle incomingBundle = requireActivity().getIntent().getExtras();
        navController = new NavController(requireActivity().getApplicationContext());
        String role = incomingBundle.getString(Constants.ROLE);
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);
        _firstName = view.findViewById(R.id.firstName);
        _lastName = view.findViewById(R.id.lastName);
        _radioGroup = view.findViewById(R.id.genderRadioGroup);
        _nextButton = view.findViewById(R.id.nextButtonBasic);
        _backButton = view.findViewById(R.id.backButtonBasic);
        _nextButton.setOnClickListener(v -> {
            if (_radioGroup.getCheckedRadioButtonId() == -1) {
                Messages.toast(getActivity(), "Choose the gender", false);
            } else {
                int radioId = _radioGroup.getCheckedRadioButtonId();
                switch (radioId) {
                    case R.id.radioButtonMale:
                        gender = 'M';
                        break;
                    case R.id.radioButtonFemale:
                        gender = 'F';
                        break;
                }
                if (Utils.isNotEmpty(new AppCompatEditText[]{_firstName, _lastName})) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.FIRST_NAME, _firstName.getText().toString());
                    bundle.putString(Constants.LAST_NAME, _lastName.getText().toString());
                    bundle.putString(Constants.GENDER, gender + "");
                    bundle.putString(Constants.ROLE, role);
                    Navigation.findNavController(view).navigate(R.id.action_basicDetailsFragment_to_communicationDetailsFragment, bundle);
                } else {
                    Messages.toast(getActivity(), "Fill in all the details.", false);
                }
            }
        });
        _backButton.setOnClickListener(v -> {
            requireActivity().finish();
        });
        return view;
    }
}