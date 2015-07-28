package com.mymedisyn.android.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Set;

public class SharedPrefrenceStorage {

    public static SharedPreferences initialize(Context context){
        return context.getSharedPreferences("Guardian", Context.MODE_PRIVATE);
    }

      public static void StoreVerificationCode(Context context, String code)
    {

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("verification_code", code);
        editor.commit();

    }

    public static String GetVerificationCode(Context context) {

        return initialize(context).getString("verification_code", null);
    }



    public static void StorePosition(Context context, String position)
    {
        Log.w("ScreenPosition", "Screen Position " + position);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("position", position);
        editor.commit();

    }

    public static String GetPosition(Context context) {

        return initialize(context).getString("position", "SplashActivity");
    }


    public static void StoreRegId(Context context, String gcmId) {

        Log.w("AuthToken", "AthToken " + gcmId);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("gcmId", gcmId);
        editor.commit();

    }

    public static String GetRegId(Context context) {

        return initialize(context).getString("gcmId", null);
    }

    public static void StoreAuthToken(Context context, String token) {

        Log.w("AuthToken", "AthToken " + token);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("token", token);
        editor.commit();

    }

    public static String GetAuthToken(Context context) {

        return initialize(context).getString("token", null);
    }

    /* *********** Store Profile Information ************** */

    public static void StoreUserId(Context context, String uid) {

        Log.w("UserId", "Id " + uid);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("uid", uid);
        editor.commit();

    }
    public static String GetUserId(Context context) {

        return initialize(context).getString("uid", null);
    }

    public static void StoreUserMobile(Context context, String mobile) {

        Log.w("Mobile", "Mobile " + mobile);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("mobile", mobile);
        editor.commit();

    }
    public static String GetUserMobile(Context context) {

        return initialize(context).getString("mobile", null);
    }

    public static void StoreUserName(Context context, String name) {

        Log.w("Name", "Name " + name);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("name", name);
        editor.commit();

    }
    public static String GetUserName(Context context) {

        return initialize(context).getString("name", null);
    }

    public static void StoreUserGender(Context context, String gender) {

        Log.w("Gender", "Gender " + gender);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("gender", gender);
        editor.commit();

    }
    public static String GetUserGender(Context context) {

        return initialize(context).getString("gender", null);
    }

    public static void StoreUserEmail(Context context, String email) {

        Log.w("Email", "Email " + email);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("email", email);
        editor.commit();

    }
    public static String GetUserEmail(Context context) {

        return initialize(context).getString("email", null);
    }

    public static void StoreProfilePic(Context context, String url) {

        Log.w("ProfilePic", "ProfilePic " + url);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("profile_pic_url", url);
        editor.commit();

    }
    public static String GetProfilePic(Context context) {

        return initialize(context).getString("profile_pic_url", null);
    }

    /* ***************************************************  */

    /****************************************************************************/

}
