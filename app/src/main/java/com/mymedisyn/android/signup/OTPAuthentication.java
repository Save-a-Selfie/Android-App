package com.mymedisyn.android.signup;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import org.w3c.dom.Text;

import java.util.Map;

public class OTPAuthentication extends Activity {

    private Dialog dialog;
    private Button dialogButton;
    private Context context = OTPAuthentication.this;

    private TextView changeContact, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_authentication);

        initUI();

    }

      public void initUI()
    {

        // Preparing Error Dialog

        dialog = CommonApplication.GetDialogInstance(OTPAuthentication.this, (short) 0, "Internet is not available");
        dialogButton = (Button)dialog.findViewById(R.id.yes);
        dialogButton.setText("Retry");
        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        changeContact = (TextView)findViewById(R.id.change_contact);
        changeContact.setText(Html.fromHtml("<u>Change Contact Number</u>"));
        changeContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MobileVerification.class);
                startActivity(intent);
            }
        });

        help = (TextView)findViewById(R.id.help);
        help.setText(Html.fromHtml("You should be getting a text message shortly. if you don't receive your verification code within the next few minutes, please <u>click here</u>"));
        help.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
            {
                // do something

            }
        });
    }


    public void verify(String mobile, String code)
    {
        // Create the JSON

        JSONObject postData = new JSONObject();

        try
        {
            postData.put("primaryContact", mobile);
            postData.put("verificationCode", code);
        }
        catch (JSONException jsonError)
        {
            CommonApplication.PrintJsonError("Mobile Verification Json Error :- ", jsonError);
        }

        // Send the request

        if(CommonApplication.IsInternetConnected(context))
        {
            Volley.newRequestQueue(context).add(new JsonObjectRequest(Request.Method.POST, CommonApplication.CommonUrl+"user/verify", postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.w(CommonApplication.CommonServerResponse, "OTP Authentication :- " + jsonObject.toString());

                    try {

                          if(jsonObject.getString("status").equals("success"))
                        {
                             if(jsonObject.has("accessCode"))
                            {
                                SharedPrefrenceStorage.StoreAuthToken(context, jsonObject.getString("accessCode"));

                                 if(jsonObject.has("hasProfile") && jsonObject.getString("hasProfile").equals("false"))
                                {
                                      if(jsonObject.getString("hasProfile").equals("false"))
                                    {
                                        Log.w(CommonApplication.CommonServerResponse, "User profile is not present");

                                        SharedPrefrenceStorage.StorePosition(context, "OTPAuthentication");

                                        Intent intent = new Intent(getApplicationContext(), UserSignUp.class);
                                        startActivity(intent);
                                    }
                                       else if(jsonObject.getString("hasProfile").equals("true"))
                                     {
                                         // call the home screen

                                           if(jsonObject.has("user"))
                                         {
                                             JSONObject user = jsonObject.getJSONObject("user");

                                             SharedPrefrenceStorage.StoreUserName(context, user.getString("name"));
                                             SharedPrefrenceStorage.StoreUserEmail(context, user.getString("email"));
                                             SharedPrefrenceStorage.StoreUserGender(context, user.getString("gender"));

                                             Log.w(CommonApplication.CommonServerResponse, "User profile has stored locally");
                                         }


                                     }


                                }
                            }

                        }
                    }
                    catch (JSONException jsonError)
                    {
                        CommonApplication.PrintJsonError("OTP Authentication Json Error :- ", jsonError);
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

    BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

              if(SharedPrefrenceStorage.GetVerificationCode(context) != null)
            {
                Log.w(CommonApplication.CommonServerResponse, "Verification Code "+SharedPrefrenceStorage.GetVerificationCode(context));

                  if(SharedPrefrenceStorage.GetUserMobile(context) != null)
                {
                    verify(SharedPrefrenceStorage.GetUserMobile(context), SharedPrefrenceStorage.GetVerificationCode(context));
                }
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(smsReceiver, new IntentFilter("SMS_Receiver"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsReceiver);

        finish();
    }

}
