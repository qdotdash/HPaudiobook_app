package com.hp.qdotdash.hpaudiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class playbackrate extends AppCompatActivity {

    RadioGroup rg;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playbackrate);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rg = findViewById(R.id.radiogroup);
        float playbackrate = new Sp(this).getplaybackrate();

        if(playbackrate==0.25f)
            rg.check(R.id.radiobutton1);
        else if(playbackrate==0.50f)
            rg.check(R.id.radiobutton2);
        else if(playbackrate==0.75f)
            rg.check(R.id.radiobutton3);
        else if(playbackrate==1.0f)
            rg.check(R.id.radiobutton4);
        else if(playbackrate==1.25f)
            rg.check(R.id.radiobutton5);
        else if(playbackrate==1.50f)
            rg.check(R.id.radiobutton6);

    }

    public void rbclick(View view){

        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = findViewById(radiobuttonid);
        rg.check(radiobuttonid);
        if(radiobuttonid==R.id.radiobutton1)
            new Sp(this).putplaybackrate(0.25f);
        else if(radiobuttonid==R.id.radiobutton2)
            new Sp(this).putplaybackrate(0.50f);
        else if(radiobuttonid==R.id.radiobutton3)
            new Sp(this).putplaybackrate(0.75f);
        else if(radiobuttonid==R.id.radiobutton4)
            new Sp(this).putplaybackrate(1.0f);
        else if(radiobuttonid==R.id.radiobutton5)
            new Sp(this).putplaybackrate(1.25f);
        else if(radiobuttonid==R.id.radiobutton6)
            new Sp(this).putplaybackrate(1.50f);

        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        intent.putExtra("playbackspeed", new Sp(getApplicationContext()).getplaybackrate());
        intent.putExtra("pbscp",true);
        startService(intent);


    }

}
