package com.hpaud.qdotdash.hpaudiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Emergency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int booknumber = new Sp(getApplicationContext()).getbooknumber();

        Button b1 = findViewById(R.id.backupseek1);
        Button b2 = findViewById(R.id.backupseek2);
        Button b3 = findViewById(R.id.backupseek3);
        Button b4 = findViewById(R.id.backupseek4);
        Button b5 = findViewById(R.id.backupseek5);

        final int[] sv = new int[5];
        String[] svs = new String[5];
        final boolean[] rightvalue = new boolean[5];
        for(int i=0;i<5;i++){
            sv[i] = new Sp(getApplicationContext()).getsavedseekvalues(i,booknumber);
            if(sv[i]==-87){
                svs[i]="Reserved";
                rightvalue[i] = false;
            }
            else {
                svs[i] = getinstanttimeinstring(sv[i]);
                rightvalue[i] = true;
            }
        }

        b1.setText(svs[0]);
        b2.setText(svs[1]);
        b3.setText(svs[2]);
        b4.setText(svs[3]);
        b5.setText(svs[4]);

        final Intent intent = new Intent(Emergency.this, PlayerService.class);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightvalue[0]) {
                    intent.putExtra("jumpto", sv[0]);
                    startService(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Value not arrived",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightvalue[1]) {
                    intent.putExtra("jumpto", sv[1]);
                    startService(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Value not arrived",Toast.LENGTH_SHORT).show();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightvalue[2]) {
                    intent.putExtra("jumpto", sv[2]);
                    startService(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Value not arrived",Toast.LENGTH_SHORT).show();
                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rightvalue[3]) {
                    intent.putExtra("jumpto", sv[3]);
                    startService(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Value not arrived",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightvalue[4]) {
                    intent.putExtra("jumpto", sv[4]);
                    startService(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Value not arrived",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public String getinstanttimeinstring(int instanttime){
        int temp =instanttime;
        int hours = temp/(1000*60*60);
        temp = temp%(1000*60*60);
        int minutes = temp/(1000*60);
        temp = temp%(1000*60);
        int seconds = temp/(1000);
        String shours = toString().valueOf(hours);
        String sminutes = toString().valueOf(minutes);
        String sseconds = toString().valueOf(seconds);
        if(hours<10){
            shours = "0" + toString().valueOf(hours);
        }
        if(minutes<10){
            sminutes = "0" + toString().valueOf(minutes);
        }
        if(seconds<10){
            sseconds = "0" + toString().valueOf(seconds);
        }
        return shours + ":" + sminutes + ":" + sseconds;


    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
