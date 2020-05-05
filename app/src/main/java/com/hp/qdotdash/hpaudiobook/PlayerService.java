package com.hp.qdotdash.hpaudiobook;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.IOException;

public class PlayerService extends Service implements AudioManager.OnAudioFocusChangeListener {

    private AudioManager mAudioManager;

    private Handler hd = new Handler();
    private Runnable rn;

    String[] strings = {"https://tinyurl.com/y9x2fgjw",
                        "https://tinyurl.com/y9t8hx4q",
                        "https://tinyurl.com/y83boefz",
                        "https://tinyurl.com/yb2fd569",
                        "https://tinyurl.com/yb4kckce",
                        "https://tinyurl.com/y9whfjgh",
                        "https://tinyurl.com/yas3zp27"
    };

//    String[] stringslq = {"https://tinyurl.com/qv4o8zf",
//            "https://tinyurl.com/rcwzvu8",
//            "https://tinyurl.com/ufxrs9h",
//            "https://tinyurl.com/tyv3tv2",
//            "https://tinyurl.com/whdqlgy",
//            "https://tinyurl.com/srellwf",
//            "https://tinyurl.com/upj7ytf"
//    };


    MediaPlayer[] mediaPlayers = new MediaPlayer[8];
    boolean[] audioloadingcomplete = new boolean[8];
    Handler handler = new Handler();
    Runnable runnable;
    int bookclickedtoload[] = new int[8];

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        for (int j=1;j<8;j++) {
            new Sp(getApplicationContext()).putbookready(j,false);
            bookclickedtoload[j]=0;
        }


        final int bknum = new Sp(getApplicationContext()).getbooknumber();


        for(int i=1;i<8;i++){
            mediaPlayers[i] = new MediaPlayer();
        }

        mediaPlayers[bknum].setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayers[bknum].setDataSource(strings[sevenbooktoggle(bknum)]);//////////////////////////////
            mediaPlayers[bknum].prepareAsync();
            bookclickedtoload[bknum]=1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayers[bknum].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(!new Sp(getApplicationContext()).getplayeractivityopen())
                    Toast.makeText(getApplicationContext(),"Book " + toString().valueOf(bknum) + " is ready",Toast.LENGTH_SHORT).show();
                audioloadingcomplete[bknum]=true;
                new Sp(getApplicationContext()).putbookready(bknum,true);
                mediaPlayers[bknum].seekTo(getseekpoint(bknum)-3000);
            }
        });

    }

    public void loadbook(final int bookclick) throws IOException {
        mediaPlayers[bookclick].setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayers[bookclick].setDataSource(strings[sevenbooktoggle(bookclick)]);
        mediaPlayers[bookclick].prepareAsync();
        bookclickedtoload[bookclick]=1;

        mediaPlayers[bookclick].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int finalK = bookclick;
                if(!new Sp(getApplicationContext()).getplayeractivityopen())
                    Toast.makeText(getApplicationContext(), "Book " + toString().valueOf(finalK) + " is ready", Toast.LENGTH_SHORT).show();
                audioloadingcomplete[finalK] = true;
                new Sp(getApplicationContext()).putbookready(finalK,true);
                mediaPlayers[finalK].seekTo(getseekpoint(finalK) - 3000);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       if(intent!=null) {
           boolean ifloaded = intent.getBooleanExtra("ifloaded",false);
           if(ifloaded){
               if(audioloadingcomplete[getbooknum()]){
                   saveseekmax();
               }
               else{
                   Toast.makeText(getApplicationContext(), "Loading in a minute", Toast.LENGTH_SHORT).show();
               }
           }
           int playmedia = intent.getIntExtra("playmedia",-1);
           if(playmedia!=-1){
                if(playmedia==0)
                    pausemedia();
                else {
                    playmedia();
                    constantseek();
                }
            }

            boolean saveseekvalue = intent.getBooleanExtra("saveseekvalue",false);
            if(saveseekvalue){
                new Sp(getApplicationContext()).saveseekbarvalue(mediaPlayers[getbooknum()].getCurrentPosition());

            }

            int seekvalue = intent.getIntExtra("seek",-1);
            if(seekvalue!=-1){
                seekmedia(seekvalue);

            }

            boolean ifgetprogress = intent.getBooleanExtra("getprogress",false);
            if (ifgetprogress){
                int mpcurrentpos = mediaPlayers[getbooknum()].getCurrentPosition();
                new Sp(getApplicationContext()).saveinstanttime(mpcurrentpos);
            }

            boolean isplaying = intent.getBooleanExtra("isplaying",false);
            if(isplaying){
                boolean ip = mediaPlayers[getbooknum()].isPlaying();
                new Sp(getApplicationContext()).putisplaying(ip);
            }

            int seekbardrag = intent.getIntExtra("progress",-231);
            if(seekbardrag!=-231){
                mediaPlayers[getbooknum()].seekTo(seekbardrag);
                new Sp(getApplicationContext()).saveinstanttime(seekbardrag);
            }

           int jumpto = intent.getIntExtra("jumpto", -45);
           if(jumpto!=-45){
               mediaPlayers[getbooknum()].seekTo(jumpto-2000);
           }

           int bookload = intent.getIntExtra("booktoloadonclick",-32);
           if(bookload!=-32&&bookclickedtoload[bookload]==0){
               try {
                   loadbook(bookload);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           float playbackspeedchange = intent.getFloatExtra("playbackspeed", -432f);
           if(playbackspeedchange!=-432f){
               mediaPlayers[getbooknum()].setPlaybackParams(mediaPlayers[getbooknum()].getPlaybackParams().setSpeed(playbackspeedchange));
           }

        }
        return START_NOT_STICKY;
    }

    public void playmedia(){
        if(audioloadingcomplete[getbooknum()]){
            mediaPlayers[getbooknum()].start();
            mediaPlayers[getbooknum()].seekTo(mediaPlayers[getbooknum()].getCurrentPosition()-3000);
        }
        else {
            Toast.makeText(getApplicationContext(),"Preparing to stream",Toast.LENGTH_SHORT).show();
        }

    }

    public void constantseek(){
        int seekposition = mediaPlayers[getbooknum()].getCurrentPosition();
        new Sp(getApplicationContext()).saveinstanttime(seekposition);
        if(mediaPlayers[getbooknum()].isPlaying()){
            rn = new Runnable() {
                @Override
                public void run() {
                    constantseek();
                }
            };
            hd.postDelayed(rn, 1000);
        }
    }

    public void pausemedia(){
        mediaPlayers[getbooknum()].pause();
    }

    public int getbooknum(){
        int booknumber = new Sp(getApplicationContext()).getbooknumber();
        return booknumber;
    }


    public int getseekpoint(int booknum){
        return new Sp(getApplicationContext()).getseekbarvalue(booknum);
    }


    public void seekmedia(int seekvalue){
        MediaPlayer mp = mediaPlayers[getbooknum()];
        if(audioloadingcomplete[getbooknum()]) {
            if (mp.isPlaying()) {
                mp.seekTo(mp.getCurrentPosition() + seekvalue);
            }
            else {
                Toast.makeText(getApplicationContext(),"Media Paused",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Preparing to stream",Toast.LENGTH_SHORT).show();
        }
    }

    public int sevenbooktoggle(int booknumber){
        int arrayposition = booknumber%8;
        return arrayposition-1;
    }

    public void saveseekmax(){
        int seekbarsize = mediaPlayers[getbooknum()].getDuration();
        new Sp(getApplicationContext()).setseekbarsize(seekbarsize);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int j=1;j<8;j++) {
            new Sp(getApplicationContext()).putbookready(j,false);
        }
        mAudioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            pausemedia();
        }
    }
}


