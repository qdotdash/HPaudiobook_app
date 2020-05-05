package com.hp.qdotdash.hpaudiobook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iitr.kaishu.nsidedprogressbar.NSidedProgressBar;

import java.util.Objects;

public class Fragment_book6 extends Fragment {

    View view;
    Handler handler = new Handler();
    Runnable runnable;
    FloatingActionButton fab6;
    NSidedProgressBar nSidedProgressBar;
    boolean nloadingtoggle = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(new Sp(getActivity()).getbooknumber()==6){
            nloadingtoggle=true;
        }
        view = inflater.inflate(R.layout.slide6,null);
        fab6 = view.findViewById(R.id.fab6);
        ifready();
        fab6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                new Sp(getContext()).putbooknumber(6);
                nloadingtoggle = true;
                if(new Sp(getActivity()).getbookready(6)){
                    Intent loadplayer = new Intent(getActivity(), Player.class);
                    startActivity(loadplayer);
                }
                else{
                    Toast.makeText(getActivity(), "Loading in a minute", Toast.LENGTH_SHORT).show();
                }
                Intent ifloadplayer = new Intent(getActivity(), PlayerService.class);
                ifloadplayer.putExtra("ifloaded",true);
                ifloadplayer.putExtra("booktoloadonclick",6);
                Objects.requireNonNull(getActivity()).startService(ifloadplayer);
            }
        });
        return view;

    }

    public void ifready(){
        int flag = 0;
        if(new Sp(getActivity()).getbookready(6)){
            fab6.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.play_media));
            nSidedProgressBar = view.findViewById(R.id.progressbar);
            nSidedProgressBar.setVisibility(View.INVISIBLE);
            nloadingtoggle = false;
            flag = 1;
        }
        if(!nloadingtoggle){
            nSidedProgressBar = view.findViewById(R.id.progressbar);
            nSidedProgressBar.setVisibility(View.INVISIBLE);
        }
        else{
            nSidedProgressBar = view.findViewById(R.id.progressbar);
            nSidedProgressBar.setVisibility(View.VISIBLE);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                ifready();
            }
        };
        if(flag==0)
            handler.postDelayed(runnable,500);
    }
}
