package com.hpaud.qdotdash.hpaudiobook;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Welcomescreen extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AssetManager am = getApplicationContext().getAssets();

        if(new Sp(getApplicationContext()).getshowwelcome()) {
            TextView tv = findViewById(R.id.wlcm);
            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ma.ttf");
            tv.setTypeface(face);


            runnable = new Runnable() {
                @Override
                public void run() {
                    new Sp(getApplicationContext()).putshowwelcome(true);
                    Intent intent = new Intent(Welcomescreen.this, Home.class);
                    startActivity(intent);
                }
            };
            handler.postDelayed(runnable, 2500);
        }
        else{
            Intent intent = new Intent(Welcomescreen.this, Home.class);
            startActivity(intent);
        }


    }
}
