package com.hpaud.qdotdash.hpaudiobook;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iitr.kaishu.nsidedprogressbar.NSidedProgressBar;

public class Home extends AppCompatActivity{

    MediaPlayer hpmusic = new MediaPlayer();
    //public variable declarations
    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////view pager
    private ViewPager mPager;
    ////////////////////////////////////////////////////////////////////int layouts
    private int[] layouts = {R.layout.slide1,R.layout.slide2,R.layout.slide3,R.layout.slide4,R.layout.slide5,R.layout.slide6,R.layout.slide7};
    ////////////////////////////////////////////////////////////////////mpageradapter
    private MpagerAdapter mpagerAdapter;
    ////////////////////////////////////////////////////////////////////layouts
    private LinearLayout Dots_layout;
    ////////////////////////////////////////////////////////////////////image view
    private ImageView[] dots;

    ImageButton hpmusicbutton;
    //////////////////////////////////
    //////////////////////////////////Button
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent starts = new Intent(Home.this, PlayerService.class);
        startService(starts);

        //Toast.makeText(getApplicationContext(),toString().valueOf(new Sp(getApplicationContext()).getsoundtoggle()),Toast.LENGTH_LONG).show();
        //Fullscreen make
        //////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////skipping welcome page
       // if(new PreferenceManager(this).checkpreference()){
       //     LoadHome();
        //}
        /////////////////////////////////////////////////////////////////////////////0

        new Sp(getApplicationContext()).putshowwelcome(false);

        hpmusic = MediaPlayer.create(getApplicationContext(), R.raw.hpmusic);
        hpmusic.setVolume(0.5f,0.5f);

        hpmusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                hpmusic.start();
            }
        });
        setContentView(R.layout.activity_home);
        ImageButton home_settings = findViewById(R.id.settings_home);
        hpmusicbutton = findViewById(R.id.musictoggle);

        if(new Sp(getApplicationContext()).getsoundtoggle()){
            hpmusicbutton.setBackgroundResource(R.drawable.sound_on);
            hpmusic.start();
        }
        else {
            hpmusicbutton.setBackgroundResource(R.drawable.sound_off);
            hpmusic.start();
            hpmusic.pause();
        }


        hpmusicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new Sp(getApplicationContext()).getsoundtoggle()){
                    hpmusicbutton.setBackgroundResource(R.drawable.sound_off);
                    hpmusic.start();
                    hpmusic.pause();
                    new Sp(getApplicationContext()).putsoundtoggle(false);
                }
                else {
                    hpmusicbutton.setBackgroundResource(R.drawable.sound_on);
                    hpmusic.start();
                    new Sp(getApplicationContext()).putsoundtoggle(true);
                }

            }
        });


        home_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,"HPAudiobook\n_________________\n\nI found this cool app to listen to Harry Potter audio books with an amazing UI \n\nhttps://drive.google.com/uc?export=download&id=1AuPE0YmzxNOys-QxNKerRYIBkvm0jLyA");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"HPAudiobook");
                startActivity(Intent.createChooser(sharingIntent,"Share app in"));
            }
        });
        //////////////////////////////////////////////////////////////////////////////Initialising views
        //////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////view pager
        mPager = findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mpagerAdapter);
        //////////////////////////////////////////////////////////////////////////////Button
        /////////////////////////////////////////////////////////////////////////////Layouts
        Dots_layout = findViewById(R.id.dotslayout);
        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////creating dots
        createdots(0);
        //////////////////////////////////////////////////////////////////
        NSidedProgressBar nSidedProgressBar = new NSidedProgressBar(this,3);
        nSidedProgressBar.setBaseSpeed(5);


        ////Listeners
        //////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////Next Button listener
        ///////////////////////////////////////////////////////////////////Page change listener
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //////////////////////Page scrolling
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //////////////////////Page selection
            @Override
            public void onPageSelected(int position) {
                //////////////////////create dots at selected position
                createdots(position);
                onChangeTab(position);
            }
            ////////////////////////////////////////////////////////////////////////////
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////


    }



    public void onChangeTab(int position){
        if(position==0){

        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////Full dots creation
    private void createdots(int current_position){
        if(Dots_layout!=null)
            Dots_layout.removeAllViews();

        dots = new ImageView[layouts.length];

        for(int i =0;i<layouts.length;i++){
            dots[i] = new ImageView(this);
            if(i==current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.largedots));
            }
            else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.smalldots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            params.width=30;
            Dots_layout.addView(dots[i],params);
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hpmusic.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(new Sp(getApplicationContext()).getsoundtoggle())
            hpmusic.start();
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
}