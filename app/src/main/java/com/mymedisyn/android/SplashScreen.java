package com.mymedisyn.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.mymedisyn.android.common.SharedPrefrenceStorage;
import com.mymedisyn.android.signup.MobileVerification;
import com.mymedisyn.android.signup.OTPAuthentication;
import com.mymedisyn.android.signup.UserSignUp;


public class SplashScreen extends Activity {

    private Context myContext = SplashScreen.this;
    private int IntervalTime=0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


    }

    Runnable run = new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                // jump to the next screen depending upon last position

                  switch (SharedPrefrenceStorage.GetPosition(getApplicationContext()))
                {
                    case "SplashScreen":{

                        Intent it = new Intent(myContext, MobileVerification.class);
                        startActivity(it);

                    }break;

                    case "MobileVerification": {

                        Intent it = new Intent(myContext, OTPAuthentication.class);
                        startActivity(it);

                    }break;

                    case "OTPAuthentication":{

                        Intent it = new Intent(myContext, UserSignUp.class);
                        startActivity(it);

                    }break;

                    default:{


                    }

                }


            }
              catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent it = new Intent(myContext, MobileVerification.class);
                startActivity(it);

            }
        }, 3000);

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

}
