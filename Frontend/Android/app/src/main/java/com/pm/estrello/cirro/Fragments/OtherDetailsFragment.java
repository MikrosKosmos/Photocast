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
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.ParamsCreator;
import com.pm.estrello.cirro.Objects.Address;
import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.R;

import org.json.JSONException;
import org.json.JSONObject;

public class OtherDetailsFragment extends Fragment implements HTTPConnector.ResponseListener {
    private Spinner _vendorType, _identityType;
    private AppCompatEditText _idDocNumber, _companyName;
    private AppCompatImageView _nextButton;
    private ConstraintLayout _layout;
    private String vendorType, documentType;
    private ProgressDialog _progressDialog;
    private String TAG_CLASS = OtherDetailsFragment.class.getSimpleName();
    private int requestCode = 0;

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
        initializeViews();
        getBundles();
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
            if (role != null && role.equals(Constants.ROLE_CUSTOMER)) {
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
        try {
            Customer customer = new Customer(data.getString(Constants.FIRST_NAME),
                    data.getString(Constants.LAST_NAME),
                    data.getString(Constants.EMAIL, ""),
                    data.getString(Constants.PHONE_NUMBER),
                    data.getString(Constants.GENDER));
            String url = Constants.API_URL + "customers";
            HTTPConnector connector = new HTTPConnector(requireContext(), url, "", this);
            connector.makeQuery(TAG_CLASS, new JSONObject(customer.toString()));
            _progressDialog.show();
            requestCode = Constants.CUSTOMER_REGISTER_CODE;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to register the address for the customer.
     */
    private void registerCustomerAddress() {
        try {
            Bundle data = getArguments();
            if (data != null) {
                Customer customer = getCustomerProfile();
                Address address = getCustomerAddressFromArgs(data, customer);
                String url = Constants.API_URL + "customers/address";
                HTTPConnector connector = new HTTPConnector(requireContext(), url,
                        DataStore.getData(requireContext(), Constants.API_JWT_TOKEN_KEY),
                        this);
                connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForCustomerAddress(address,
                        customer.getId()));
                _progressDialog.show();
                requestCode = Constants.CUSTOMER_ADDRESS_CODE;
            }
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(requireContext(), Constants.API_RESPONSE_ERROR, false);
            changeActivity(true);
        }
    }

    /**
     * Method to get the Customer profile data.
     *
     * @return customer object.
     * @throws JSONException:
     */
    private Customer getCustomerProfile() throws JSONException {
        return new Customer(new JSONObject(DataStore.getData(requireContext(), Constants.USER_PROFILE)));
    }

    /**
     * Method to extract the Address information from the Arguments.
     *
     * @param data:    the bundle arguments.
     * @param customer : The customer data.
     * @return address:
     */
    private Address getCustomerAddressFromArgs(Bundle data, Customer customer) {
        if (data != null) {
            return new Address(data.getString(Constants.ADDRESS_1),
                    data.getString(Constants.ADDRESS_2),
                    Integer.parseInt(data.getString(Constants.PINCODE)),
                    data.getInt(Constants.CITY),
                    customer.getId(),
                    data.getDouble(Constants.GPS_LAT),
                    data.getDouble(Constants.GPS_LONG));
        }
        return null;
    }

    /**
     * Method to change the activity.
     *
     * @param isCustomer: true for customer registration, else false.
     */
    private void changeActivity(boolean isCustomer) {
        //TODO:
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {
            if (requestCode == Constants.CUSTOMER_REGISTER_CODE) {
                JSONObject jsonObject = response.getJSONObject(Constants.API_RESPONSE_KEY);
                Customer customer = new Customer(jsonObject.getInt(Constants.ID),
                        jsonObject.getString(Constants.FIRST_NAME),
                        jsonObject.getString(Constants.LAST_NAME),
                        jsonObject.getString(Constants.PHONE_NUMBER),
                        jsonObject.getString(Constants.EMAIL), "", "",
                        0, "", "");
                DataStore.storeData(requireContext(), Constants.API_JWT_TOKEN_KEY,
                        jsonObject.getString(Constants.API_JWT_TOKEN_KEY));
                DataStore.storeData(requireContext(), Constants.USER_PROFILE, customer.toString());
                DataStore.storeData(requireContext(), Constants.IS_LOGGED_IN, true);
                registerCustomerAddress();
            } else if (requestCode == Constants.CUSTOMER_ADDRESS_CODE) {
                JSONObject jsonObject = response.getJSONObject(Constants.API_RESPONSE_KEY);
                int addressId = jsonObject.getInt(Constants.ID);
                Address address = getCustomerAddressFromArgs(getArguments(),
                        getCustomerProfile());
                if (address != null) {
                    address.setAddressId(addressId);
                    DataStore.storeData(requireContext(), Constants.CUSTOMER_ADDRESS, address.toString());
                }
                changeActivity(true);
            }
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