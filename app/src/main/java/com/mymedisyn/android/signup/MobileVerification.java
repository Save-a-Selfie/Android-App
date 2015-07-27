package com.mymedisyn.android.signup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mymedisyn.android.R;
import com.mymedisyn.android.common.CommonApplication;
import com.mymedisyn.android.common.SharedPrefrenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MobileVerification extends Activity {

    private Dialog dialog;
    private Button dialogButton;
    private Context context = MobileVerification.this;

    // UI Elements

    private EditText mobileNumber;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_verification_screen);

        initUI();

    }

     public void initUI()
    {
        mobileNumber = (EditText)findViewById(R.id.mobile);

        nextButton = (Button)findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(mobileNumber.getText().length() > 0)
                {
                  verify(mobileNumber.getText().toString());
                }
                  else
                 {
                     Toast.makeText(context, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                 }
            }
        });

        // Preparing Error Dialog

        dialog = CommonApplication.GetDialogInstance(MobileVerification.this, (short)0, "Internet is not available");
        dialogButton = (Button)dialog.findViewById(R.id.yes);
        dialogButton.setText("Retry");
        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

      public void verify(final String mobile)
    {
        // Create the JSON

        JSONObject postData = new JSONObject();

         try
        {
            postData.put("primaryContact", mobile);
        }
           catch (JSONException jsonError)
          {
              CommonApplication.PrintJsonError("Mobile Verification Json Error :- ", jsonError);
          }

        // Send the request

          if(CommonApplication.IsInternetConnected(context))
        {
            Volley.newRequestQueue(context).add(new JsonObjectRequest(Request.Method.POST, CommonApplication.CommonUrl+"user", postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.w(CommonApplication.CommonServerResponse, "MobileVerification :- "+jsonObject.toString());

                    try {
                           if(jsonObject.getString("status").equals("success"))
                        {
                            // Move to the next screen


                            SharedPrefrenceStorage.StorePosition(context, "MobileVerification");

                            SharedPrefrenceStorage.StoreUserMobile(context, mobile);

                            Intent intent = new Intent(getApplicationContext(), OTPAuthentication.class);
                            startActivity(intent);

                        }
                    }
                      catch (JSONException jsonError)
                    {
                        CommonApplication.PrintJsonError("Mobile Verification Json Error :- ", jsonError);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    CommonApplication.commonVolleyLogs(volleyError, "NetworkingErrors");

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return CommonApplication.commonHttpHeaders(context);
                }

            });
        }
            else {
              dialog.show();
          }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
