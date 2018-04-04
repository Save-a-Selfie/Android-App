package com.imsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.imsearch.R;
import com.imsearch.models.Address;

public class AddressFragment extends MasterFragment {

    private AddressFragmentInteractionListener mListener;
    private EditText house;
    private EditText street;
    private EditText city;
    private EditText county;
    private EditText country;
    private String TAG = "Debugging";


    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_address, container, false);

        house = (EditText) fragmentView.findViewById(R.id.house);
        street = (EditText)fragmentView.findViewById(R.id.street);
        city = (EditText)fragmentView.findViewById(R.id.city);
        county = (EditText)fragmentView.findViewById(R.id.county);
        country = (EditText)fragmentView.findViewById(R.id.country);


        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddressFragmentInteractionListener) {
            mListener = (AddressFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddressFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Destroyed");
    }

    @Override
    public boolean isAllFieldsPopulated() {
        Address address = new Address();

        if(house.getText().toString().length() > 0 && street.getText().toString().length() > 0 && city.getText().toString().length() > 0 && county.getText().toString().length() > 0 && country.getText().toString().length() > 0){


            address.setHouse(house.getText().toString());
            address.setStreet(street.getText().toString());
            address.setCity(city.getText().toString());
            address.setCounty(county.getText().toString());
            address.setCountry(country.getText().toString());

            mListener.onFragmentInteraction(address);

            return true;
        }

        mListener.onFragmentInteraction(address);

        return true; // make false to impose fields check
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
    */
    public interface AddressFragmentInteractionListener {
        void onFragmentInteraction(Address address);
    }
}
