package com.imsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.imsearch.R;
import com.imsearch.models.Contact;


public class ContactFragment extends MasterFragment {

    private ContactFragmentInteractionListener mListener;
    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private String TAG = "Debugging";


    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);

        firstName = (EditText) fragmentView.findViewById(R.id.house);
        lastName = (EditText)fragmentView.findViewById(R.id.street);
        phone = (EditText)fragmentView.findViewById(R.id.phone);

        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContactFragmentInteractionListener) {
            mListener = (ContactFragmentInteractionListener) context;
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

        if(firstName.getText().toString().length() > 0 && lastName.getText().toString().length() > 0 && phone.getText().toString().length() > 0){

            Contact contact = new Contact();
            contact.setFirstName(firstName.getText().toString());
            contact.setLastName(lastName.getText().toString());
            contact.setPhone(phone.getText().toString());

            mListener.onFragmentInteraction(contact);

            return true;
        }

        return true;  // make false to impose fields check
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
    */
    public interface ContactFragmentInteractionListener {
        void onFragmentInteraction(Contact contact);
    }
}
