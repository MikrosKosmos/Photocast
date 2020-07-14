package com.pm.estrello.cirro.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.R;

import org.json.JSONObject;

public class OtherDetailsFragment extends Fragment implements HTTPConnector.ResponseListener {
    private Spinner _vendorType, _identityType;
    private AppCompatEditText _idDocNumber, _companyName;
    private AppCompatImageView _nextButton;
    private ConstraintLayout _layout;
    private String vendorType, documentType;
    private ProgressDialog _progressDialog;
    private String TAG_CLASS = OtherDetailsFragment.class.getSimpleName();

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
        _layout = view.findViewById(R.id.otherDetailsFragmentLayout);
        getBundles();
        initializeViews();
        return view;
    }

    /**
     * Method to initialize the Widgets.
     */
    private void initializeViews() {
        _progressDialog = new ProgressDialog(requireContext());
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        ArrayAdapter<String> vendorTypeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, Constants.VENDOR_TYPES);
        _vendorType.setAdapter(vendorTypeAdapter);
        ArrayAdapter<String> documentTypeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, Constants.DOCUMENT_TYPES);
        _identityType.setAdapter(documentTypeAdapter);
        _vendorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Messages.toast(requireContext(), "Please select a type", false);
                } else {
                    vendorType = Constants.VENDOR_TYPES[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Method to get the incoming bundles.
     */
    private void getBundles() {
        Bundle incomingBundle = getArguments();
        if (incomingBundle != null) {
            String role = incomingBundle.getString(Constants.ROLE);
            if (role.equals(Constants.ROLE_CUSTOMER)) {
                _layout.setVisibility(View.INVISIBLE);
                registerCustomer(incomingBundle);
            }
        }
    }

    /**
     * Method to register a customer.
     *
     * @param data: The data for the customer.
     */
    private void registerCustomer(Bundle data) {
        //TODO: Register a customer.
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {
            //TODO:
        } catch (Exception e) {
            Messages.toast(requireContext(), Constants.API_RESPONSE_ERROR, false);
            Messages.log(TAG_CLASS, e.toString());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        _progressDialog.dismiss();
        Messages.toast(requireContext(), Constants.API_RESPONSE_ERROR, false);
        Messages.log(TAG_CLASS, error.toString());
    }
}