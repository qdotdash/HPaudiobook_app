package com.hpaud.qdotdash.hpaudiobook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class seekvaluesetter extends AppCompatActivity {
    RadioButton rb;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekvaluesetter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rg = findViewById(R.id.radiogroup);
       int seekrate = new Sp(this).getseekrate();

       if(seekrate==10000)
           rg.check(R.id.radiobutton1);
       else if(seekrate==20000)
            rg.check(R.id.radiobutton2);
       else if(seekrate==30000)
           rg.check(R.id.radiobutton3);
       else if(seekrate==60000)
           rg.check(R.id.radiobutton4);
       else if(seekrate==120000)
           rg.check(R.id.radiobutton5);
       else if(seekrate==300000)
           rg.check(R.id.radiobutton6);

    }

    public void rbclick(View view){

        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = findViewById(radiobuttonid);
        rg.check(radiobuttonid);
        if(radiobuttonid==R.id.radiobutton1)
            new Sp(this).putseekrate(10000);
        else if(radiobuttonid==R.id.radiobutton2)
            new Sp(this).putseekrate(20000);
        else if(radiobuttonid==R.id.radiobutton3)
            new Sp(this).putseekrate(30000);
        else if(radiobuttonid==R.id.radiobutton4)
            new Sp(this).putseekrate(60000);
        else if(radiobuttonid==R.id.radiobutton5)
            new Sp(this).putseekrate(120000);
        else if(radiobuttonid==R.id.radiobutton6)
            new Sp(this).putseekrate(300000);

    }
}

