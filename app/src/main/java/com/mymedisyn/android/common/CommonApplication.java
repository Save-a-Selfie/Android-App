package com.mymedisyn.android.common;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.android.gms.analytics.HitBuilders;
import com.mymedisyn.android.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikhil on 16/07/15.
 */

  public class CommonApplication extends Application
{

  // Common Variables

  public static String CommonServerResponse = "CommonServerResponse";
  public static String CommonError = "CommonErrors";
  public static String CommonUrl = "http://stage.mymedisyn.com/api/v0/";

 //---------------------------------//

 // Validators

 public static String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

 public static String USERNAME_PATTERN = "^[_A-Za-z]+[[:blank:]]*[_A-Za-z]*$"; // character must start with (A-Z or a-z) and not digits in between

 //---------------------------------//

   public static void PrintJsonError(String tag, JSONException jsonError)
 {
   Log.w(CommonApplication.CommonError, tag+(jsonError.getMessage() != null ? jsonError.getMessage() : "No Message"));
 }



  public static Map<String, String> commonHttpHeaders(Context context){


  HashMap<String, String> params = new HashMap<String, String>();

  params.put("Content-Type", "application/json");

   if(SharedPrefrenceStorage.GetAuthToken(context) != null)
  {
   String accessToken = SharedPrefrenceStorage.GetAuthToken(context);
   params.put("authorization", "bearer " + accessToken);
  }

   return params;
 }

 public static void commonVolleyLogs(VolleyError volleyError, String log)
 {

   if(volleyError.networkResponse != null && volleyError.networkResponse.data != null)
  {
   NetworkResponse response = volleyError.networkResponse;
   String responseData = new String(response.data);


   switch (response.statusCode)
   {
    case 200:{

     Log.w(log, "ResponseCode_200 "+responseData);

    }break;

    case 400 :{

     Log.w(log, "ResponseCode_400 "+responseData);

    }break;
   }


  }

 }

   public static boolean IsInternetConnected(Context context)
 {

   ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
   NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

   return activeNetwork != null && activeNetwork.isConnected();
 }


   public static Dialog GetDialogInstance(Activity activity, short button, String message)
 {

  Dialog dialog = new Dialog(activity);
  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

  Typeface robotoFont = Typeface.createFromAsset(activity.getAssets(), "roboto.ttf");

  TextView heading = null;

  if(button == 0) // Single Button
  {

   dialog.setContentView(R.layout.dialog_single_button);
   heading = (TextView) dialog.findViewById(R.id.message);
  }
  else if(button == 1) // Double Button
  {

   dialog.setContentView(R.layout.dialog_double_button);
   heading = (TextView) dialog.findViewById(R.id.message);
  }



  heading.setTypeface(robotoFont);
  heading.setText(message);

  return dialog;
 }


}
