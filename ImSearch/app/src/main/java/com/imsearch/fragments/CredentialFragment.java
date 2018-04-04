package com.imsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.imsearch.R;
import com.imsearch.models.Credential;


public class CredentialFragment extends MasterFragment {

    private CredentialFragmentInteractionListener mListener;
    private EditText email;
    private EditText password;
    private String TAG = "Debugging";


    public CredentialFragment() {
        // Required empty public constructor
    }

    public static CredentialFragment newInstance() {
        CredentialFragment fragment = new CredentialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_credential, container, false);

        email = (EditText) fragmentView.findViewById(R.id.email);
        password = (EditText)fragmentView.findViewById(R.id.password);

        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CredentialFragmentInteractionListener) {
            mListener = (CredentialFragmentInteractionListener) context;
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

        if(email.getText().toString().length() > 0 && password.getText().toString().length() > 0){

            Credential credential = new Credential();
            credential.setEmail(email.getText().toString());
            credential.setPassword(password.getText().toString());

            mListener.onFragmentInteraction(credential);

            return true;
        }

        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
    */
    public interface CredentialFragmentInteractionListener {
        void onFragmentInteraction(Credential credential);
    }
}
