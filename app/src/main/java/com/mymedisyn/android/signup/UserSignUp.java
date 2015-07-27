package com.mymedisyn.android.signup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import java.util.regex.Pattern;

public class UserSignUp extends Activity {

    private FrameLayout finish;
    private TextView genderSelector;
    private EditText fullName, email;
    private Context context = UserSignUp.this;

    private boolean profileComplete = false;

    // Dialog variables

    private Dialog dialog;
    private Button dialogButton;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup_screen);

        initUI();


        // Preparing Error Dialog

        dialog = CommonApplication.GetDialogInstance(UserSignUp.this, (short) 0, "Internet is not available");

        dialogButton = (Button)dialog.findViewById(R.id.yes);
        dialogButton.setText("Retry");

        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

     public void initUI()
    {
        email = (EditText)findViewById(R.id.user_email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() < 1)
                {
                    profileComplete = false;
                }
                  else {
                    validator();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fullName = (EditText)findViewById(R.id.user_fullname);
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                  if(s.length() < 1)
                {
                   profileComplete = false;
                }
                   else
                  {
                    validator();
                  }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        genderSelector = (TextView)findViewById(R.id.user_gender);
        genderSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.gender_list);
                final TextView txtlstFemale = (TextView) dialog.findViewById(R.id.txtlstFemale);
                dialog.show();
                txtlstFemale.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)

                    {
                        genderSelector.setText("F");
                        validator();
                        dialog.dismiss();
                    }
                });
                final TextView txtlstMale = (TextView) dialog.findViewById(R.id.txtlstMale);
                txtlstMale.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)

                    {
                        genderSelector.setText("M");
                        validator();
                        dialog.dismiss();
                    }
                });
                final TextView txtlstOther = (TextView) dialog.findViewById(R.id.txtlstOther);
                txtlstOther.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)

                    {
                        genderSelector.setText("Other");
                        validator();
                        dialog.dismiss();
                    }
                });
            }
        });

        finish = (FrameLayout)findViewById(R.id.bottom_layout);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(profileComplete)
                {
                    //move to the next screen

                    progressDialog = ProgressDialog.show(context, "Signing In", "Registering User, Please wait....", true);

                    signUp(fullName.getText().toString(), email.getText().toString(), genderSelector.getText().toString(), "");

                }
                    else
                 {
                     Toast.makeText(context, "Profile is not complete yet", Toast.LENGTH_SHORT).show();
                 }
            }
        });


    }

    public void signUp(final String name, final String email, final String gender, String refrrelCode)
    {
        // Create the JSON

        JSONObject postData = new JSONObject();

        try
        {
            postData.put("name", name);
            postData.put("email", email);
            postData.put("gender", gender);

        }
        catch (JSONException jsonError)
        {
            CommonApplication.PrintJsonError("SignUp Json Error :- ", jsonError);
        }

        // Send the request

        if(CommonApplication.IsInternetConnected(context))
        {
            Volley.newRequestQueue(context).add(new JsonObjectRequest(Request.Method.PUT, CommonApplication.CommonUrl+"user", postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.w(CommonApplication.CommonServerResponse, "UserSigup :- " + jsonObject.toString());

                    try {

                          if(jsonObject.getString("status").equals("success"))
                        {

                            SharedPrefrenceStorage.StoreUserName(context, name);
                            SharedPrefrenceStorage.StoreUserEmail(context, email);
                            SharedPrefrenceStorage.StoreUserGender(context, gender);

                             if(progressDialog != null)
                            {
                                progressDialog.dismiss();
                            }

                            Log.w(CommonApplication.CommonServerResponse, "User profile stored cloud/locally");

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

                      if(progressDialog != null)
                    {
                        progressDialog.dismiss();
                    }

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return CommonApplication.commonHttpHeaders(context);
                }

            });
        }

           else
        {
            dialog.show();
        }

    }


    public void validator()
    {
          if(Pattern.compile(CommonApplication.USERNAME_PATTERN).matcher(fullName.getText().toString()).matches() && Pattern.compile(CommonApplication.EMAIL_PATTERN).matcher(email.getText()).matches() && !genderSelector.getText().toString().equals("Gender"))
        {
            profileComplete = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
