package com.pm.estrello.cirro.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
    private char gender;

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
        _nextButton.setOnClickListener(v -> {
            if (_radioGroup.getCheckedRadioButtonId() == -1) {

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
            }
        });
        return view;
    }

    /**
     * Method to check whether the edit texts are not empty.
     *
     * @param editTexts: The Edit text to be checked.
     * @return true if none are empty, else false.
     */
    private boolean isNotEmpty(AppCompatEditText[] editTexts) {
        for (AppCompatEditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }
}