package com.hp.qdotdash.hpaudiobook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Player extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean toggle_play = false;
    int seekrate;
    SeekBar seekBar;
    TextView currentseektime;
    int seekmax;
    CardView cardView;
    Runnable runnable;
    Handler handler = new Handler();
    int flag_starting_of_activity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        final FloatingActionButton play_pause = findViewById(R.id.play_pause);
        ImageButton forward = findViewById(R.id.forward);
        ImageButton rewind = findViewById(R.id.rewind);
        ImageButton home = findViewById(R.id.home);
        ImageView bookimagedisplay = findViewById(R.id.bookimage);

        String booknames[] = {"Harry Potter and the Sorcerer's stone","Harry Potter and the Chamber of Secrets","Harry Potter and the Prisoner of Azkaban","Harry Potter and the Goblet of Fire","Harry Potter and the Order of the Phoenix","Harry Potter and the Half Blood Prince","Harry Potter and the Deathly Hallows"};



        new Sp(getApplicationContext()).putplayeropen(true);

        seekBar = findViewById(R.id.seekbar);
        seekmax = new Sp(getApplicationContext()).seekbarsize();
        seekBar.setMax(seekmax);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_player);


        TextView bookname = navHeaderView.findViewById(R.id.bookname);
        TextView booktime = navHeaderView.findViewById(R.id.time);

        int bknum = new Sp(this).getbooknumber();

        bookname.setText(booknames[bknum-1]);
        booktime.setText("Duration : " + getinstanttimeinstring(seekmax));


        int[] bookimages = {R.drawable.bk1,R.drawable.bk2,R.drawable.bk3,R.drawable.bk4,R.drawable.bk5,R.drawable.bk6,R.drawable.bk7};

        int[] bgimages = {R.drawable.playerbackground,R.drawable.playerbackground2,R.drawable.playerbackground3,R.drawable.playerbackground4,R.drawable.playerbackground5,R.drawable.playerbackground6,R.drawable.playerbackground7};

        bookimagedisplay.setBackgroundResource(bookimages[bknum-1]);

        CoordinatorLayout background = findViewById(R.id.background);
        //if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
        background.setBackgroundResource(bgimages[bknum-1]);

        cardView = findViewById(R.id.cardview);

        currentseektime = findViewById(R.id.currentseektime);
        if (new Sp(getApplicationContext()).getseekbarvisibility()){
            seekBar.setVisibility(View.VISIBLE);
            currentseektime.setVisibility(View.VISIBLE);
        }
        else {
            seekBar.setVisibility(View.INVISIBLE);
            currentseektime.setVisibility(View.INVISIBLE);
        }

            toggle_play = true;
            play_pause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.pause));
            Intent playmedia = new Intent(Player.this,PlayerService.class);
            playmedia.putExtra("playmedia",1);
            playmedia.putExtra("playbackspeed", getplaybackrate());
            startService(playmedia);
            seekBar.setProgress(new Sp(getApplicationContext()).getseekbarvalue(bknum));
            currentseektime.setText(getinstanttimeinstring(new Sp(getApplicationContext()).getseekbarvalue(bknum)));
            flag_starting_of_activity=1;
            changeseekbar();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Intent seekbardrag = new Intent(Player.this, PlayerService.class);
                    seekbardrag.putExtra("progress",progress);
                    startService(seekbardrag);
                    currentseektime.setText(getinstanttimeinstring(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent forward = new Intent(Player.this, PlayerService.class);
              forward.putExtra("seek",new Sp(getApplicationContext()).getseekrate());
              startService(forward);
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rewind = new Intent(Player.this, PlayerService.class);
                rewind.putExtra("seek",-(new Sp(getApplicationContext()).getseekrate()));
                startService(rewind);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PauseHome = new Intent(Player.this, PlayerService.class);
                PauseHome.putExtra("playmedia",0);
                startService(PauseHome);
                Intent loadhome = new Intent(Player.this, Home.class);
                startActivity(loadhome);
            }
        });



        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle_play){
                    toggle_play = false;
                    play_pause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.play_media));
                    Intent playmedia = new Intent(Player.this,PlayerService.class);
                    playmedia.putExtra("playmedia",0);
                    playmedia.putExtra("saveseekvalue",true);
                    startService(playmedia);
                }
                else{
                        toggle_play = true;
                        play_pause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.pause));
                        Intent playmedia = new Intent(Player.this,PlayerService.class);
                        playmedia.putExtra("playmedia",1);
                        startService(playmedia);
                        changeseekbar();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(getApplicationContext(),"Press Home",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.seekrate) {
            Intent seekrateintent = new Intent(Player.this, seekvaluesetter.class);
            startActivity(seekrateintent);
        }

        else if(id == R.id.playbackspeed){
            Intent pbs = new Intent(getApplicationContext(), playbackrate.class);
            startActivity(pbs);
        }

        else if (id == R.id.seekbarhide) {
            if(new Sp(getApplicationContext()).getseekbarvisibility()){
                seekBar.setVisibility(View.INVISIBLE);
                currentseektime.setVisibility(View.INVISIBLE);
                new Sp(getApplicationContext()).putseekbarvisibility(false);
            }
            else{
                seekBar.setVisibility(View.VISIBLE);
                currentseektime.setVisibility(View.VISIBLE);
                new Sp(getApplicationContext()).putseekbarvisibility(true);
            }

        }

        else if (id == R.id.emergency) {
            Intent emergency = new Intent(Player.this, Emergency.class);
            startActivity(emergency);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeseekbar() {
        if(flag_starting_of_activity==1) {
            seekBar.setProgress(new Sp(getApplicationContext()).getinstanttime());
            currentseektime.setText(getinstanttimeinstring(new Sp(getApplicationContext()).getinstanttime()));
        }
        if(toggle_play){
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeseekbar();
                    }
            };
            handler.postDelayed(runnable,1000);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Sp(getApplicationContext()).putplayeropen(false);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Intent saveseekvalue = new Intent(Player.this, PlayerService.class);
        saveseekvalue.putExtra("saveseekvalue",true);
        startService(saveseekvalue);
        //Toast.makeText(getApplicationContext(), toString().valueOf(mediaPlayer.getCurrentPosition()-2000), Toast.LENGTH_LONG).show();
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

    public float getplaybackrate(){
        return new Sp(getApplicationContext()).getplaybackrate();
    }

}

