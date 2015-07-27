package com.mymedisyn.android.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.mymedisyn.android.common.SharedPrefrenceStorage;

/**
 * Created by nikhil on 17/07/15.
 */

 public class IncomingSMS extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    String verificationCode = message.substring(message.length() - 5, message.length()-1);

                    SharedPrefrenceStorage.StoreVerificationCode(context, verificationCode);

                    Log.w("SmsReceiver", "senderNum: " + senderNum + "; message:" + message + " verfication code" + verificationCode);

                    context.sendBroadcast(new Intent("SMS_Receiver"));

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}