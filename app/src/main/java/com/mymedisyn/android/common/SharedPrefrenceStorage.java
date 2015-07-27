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




    public static void StoreSecurityPrefrence(Context context, String level) {

        Log.w("SecurityLeve", "SecurityLevel " + level);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("security_level", level);
        editor.commit();

    }

    public static String GetSecurityPrefrence(Context context) {

        return initialize(context).getString("security_level", "2");
    }


    public static void StoreMacAdd(Context context, String mac) {

        Log.w("MacAdd", "Mac Add " + mac);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("mac_id", mac);
        editor.commit();

    }

    public static String GetMacAdd(Context context) {

        return initialize(context).getString("mac_id", null);
    }


      public static void StoreDevicePosition(Context context, String state)
    {
        Log.w("DeviceState", "State " + state);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("device_state", null);
        editor.commit();
    }

    public static String GetDevicePosition(Context context){

        return initialize(context).getString("device_state", null);
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

    public static void StoreActivityid(Context context, String id) {

        Log.w("ActvityId", "Activity Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("activity_id", id);
        editor.commit();

    }

    public static String GetActivityId(Context context) {

        return initialize(context).getString("activity_id", null);
    }


    public static void StoreActivityUserid(Context context, String id) {

        Log.w("ActvityId", "Activity Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("activity_id_user", id);
        editor.commit();

    }

    public static String GetActivityUserId(Context context) {

        return initialize(context).getString("activity_id_user", null);
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

    /* ************ Store User, Vinctim & Guardians Coordinates ********************** */


    public static void StoreUserActivity(Context context, String activity)
    {
        Log.w("User", "User " + activity);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("user_activity", activity);
        editor.commit();
    }

    public static String GetUserActivity(Context context)
    {
        return initialize(context).getString("user_activity", null);
    }

    public static void StoreUserCoordinates(Context context, LatLng coord)
    {
        Log.w("User", "User " + coord);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("user_cord", coord.latitude+"@"+coord.longitude);
        editor.commit();
    }

    public static String GetUserCoordinates(Context context)
    {
        return initialize(context).getString("user_cord", null);
    }

    public static void StoreUserAddress(Context context, String address)
    {
        Log.w("User Address", address);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("user_address", address);
        editor.commit();
    }

    public static String GetUserAddress(Context context)
    {
        return initialize(context).getString("user_address", null);
    }


    public static void StoreUserLocality(Context context, String locality)
    {
        Log.w("User locality", locality);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("user_locality", locality);
        editor.commit();
    }

    public static String GetUserLocality(Context context)
    {
        return initialize(context).getString("user_locality", null);
    }


    public static void StoreUserSubLocality(Context context, String locality)
    {
        Log.w("User Address", locality);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("user_sub_locality", locality);
        editor.commit();
    }

    public static String GetUserSubLocality(Context context)
    {
        return initialize(context).getString("user_sub_locality", null);
    }


    public static void StoreSafeWalkSource(Context context, String coord)
    {
        Log.w("SafeWalk", "Destination " + coord);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safe_walk_dest", coord);
        editor.commit();
    }

    public static String GetConnectionStatus(Context context)
    {
        return initialize(context).getString("connection_status", "Connection Unknown");
    }

    public static void StoreConnectionStatus(Context context, String status)
    {

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("connection_status", status);
        editor.commit();
    }

    public static String GetSafeWalkDestination(Context context)
    {
        return initialize(context).getString("safe_walk_dest", null);
    }

    public static void StoreVictimCoordinates(Context context, String coord)
    {
        Log.w("Victim", "Victim " + coord);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("victim_cord", coord);
        editor.commit();
    }

      public static String GetVictimCoordinates(Context context)
    {
        return initialize(context).getString("victim_cord", null);
    }

      public static void StoreGuardianCoordinates(Context context, Set<String> coordList)
    {
        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putStringSet("coordList", coordList);
        editor.commit();
    }

    public static Set<String> GetGuardianCoordinates(Context context)
    {
        return initialize(context).getStringSet("coordList", null);
    }

    public static void StoreRunningActivity(Context context, String name)
    {

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("activity_name", name);
        editor.commit();

    }

    public static String GetRunningActivity(Context context) {

        return initialize(context).getString("activity_name", null);
    }


    //************** SaferWalk Metadata ****************//


    public static void StoreSaferWalkCaseid(Context context, String id) {

        Log.w("safer_walk_id", "SaferWalk Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safer_walk_id", id);
        editor.commit();

    }

    public static String GetSaferWalkCaseid(Context context) {

        return initialize(context).getString("safer_walk_id", null);
    }

    public static void StoreSaferWalkSource(Context context, String id) {

        Log.w("safer_walk_id", "SaferWalk Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safer_walk_source", id);
        editor.commit();

    }

    public static String GetSaferWalkSource(Context context) {

        return initialize(context).getString("safer_walk_source", null);
    }

    public static void StoreSaferWalkDestination(Context context, String id) {

        Log.w("safer_walk_id", "SaferWalk Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safer_walk_destination", id);
        editor.commit();

    }

    public static String GetSaferWalkDestination(Context context) {

        return initialize(context).getString("safer_walk_destination", null);
    }

    public static void StoreSaferWalkInitiator(Context context, String id) {

        Log.w("safer_walk_id", "SaferWalk Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safer_walk_initiator", id);
        editor.commit();

    }

    public static String GetSaferWalkInitiator(Context context) {

        return initialize(context).getString("safer_walk_initiator", null);
    }

    public static void StoreSaferWalkListener(Context context, String id) {

        Log.w("safer_walk_id", "SaferWalk Id " + id);

        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putString("safer_walk_listener", id);
        editor.commit();

    }

    public static String GetSaferWalkListener(Context context) {

        return initialize(context).getString("safer_walk_listener", null);
    }



    //**************************************************//

    /****************************************************************************/

}
