package com.hpaud.qdotdash.hpaudiobook;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class Sp {

    private Context context;
    private SharedPreferences sharedPreferences;

    public Sp(Context context){
        this.context = context;
        getSharedPreference();
    }

    private void getSharedPreference(){
        sharedPreferences = context.getSharedPreferences("Seek_Value_Manipulate", Context.MODE_PRIVATE);
    }

    public void saveseekbarvalue(int seekbarvalue){
        int booknumber = getbooknumber();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekvalue" + toString().valueOf(booknumber),seekbarvalue);
        editor.apply();

        int datanumber = getdatanum();
        if(datanumber!=0) {
            int i;
            if (datanumber > 4)
                i = 4;
            else
                i = datanumber;
            for (; i >= 1; i--) {
                editor.putInt("seekvalue" + toString().valueOf(i) + toString().valueOf(getbooknumber()), sharedPreferences.getInt("seekvalue" + toString().valueOf(i - 1) + toString().valueOf(getbooknumber()), 0));
                editor.apply();
            }
        }
        datanumber=datanumber+1;
        editor.putInt("datanumber",datanumber);
        editor.putInt("seekvalue0" + toString().valueOf(getbooknumber()),seekbarvalue);
        editor.apply();

    }

    public int getsavedseekvalues(int num1, int num2){
        return sharedPreferences.getInt("seekvalue"+toString().valueOf(num1)+toString().valueOf(num2),-87);

    }
    private int getdatanum(){
        return sharedPreferences.getInt("datanumber",0);

    }

    public int getseekbarvalue(int booknum){
        int booknumber = booknum;
        int seekbarvalue = sharedPreferences.getInt("seekvalue" + toString().valueOf(booknumber),0);
        return seekbarvalue;
    }

    public void putseekrate(int seekrate){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekrate",seekrate);
        //Toast.makeText(context, toString().valueOf(seekrate), Toast.LENGTH_SHORT).show();
        editor.apply();
    }

    public int getseekrate(){
        int seekrate = sharedPreferences.getInt("seekrate",20000);
        return seekrate;
    }

    public void putbooknumber(int i){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("booknumber",i);
        editor.apply();
    }

    public int getbooknumber(){
        int booknumber = sharedPreferences.getInt("booknumber",1);
        return booknumber;
    }


    public void setseekbarsize(int seekbarsize){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekbarsize",seekbarsize);
        editor.apply();
    }

    public int seekbarsize(){
        int seekbarsize = sharedPreferences.getInt("seekbarsize",0);
        return seekbarsize;
    }

    public void saveinstanttime(int time){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("instanttime",time);
        editor.apply();
    }

    public int getinstanttime(){
        int instanttime = sharedPreferences.getInt("instanttime",0);
        return instanttime;
    }

    public void putisplaying(boolean isplaying){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isplaying",isplaying);
        editor.apply();
    }

    public boolean getseekbarvisibility() {
        boolean seekbarvisibility = sharedPreferences.getBoolean("seekbarvisibility",true);
        return seekbarvisibility;
    }

    public void putseekbarvisibility(boolean visibility){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("seekbarvisibility",visibility);
        editor.apply();
    }

    public void putplayeropen(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("playeractivity",b);
        editor.apply();
    }

    public boolean getplayeractivityopen() {
        return sharedPreferences.getBoolean("playeractivity",false);
    }

    public void putbookready(int j, boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("bookready"+toString().valueOf(j),b);
        editor.apply();

    }

    public boolean getbookready(int booknumber){
        return sharedPreferences.getBoolean("bookready"+toString().valueOf(booknumber),false);
    }

    public void putplaybackrate(float pbr){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("playbackrate", pbr);
        //Toast.makeText(context, toString().valueOf(seekrate), Toast.LENGTH_SHORT).show();
        editor.apply();
    }

    public float getplaybackrate(){
        float seekrate = sharedPreferences.getFloat("playbackrate",1f);
        return seekrate;
    }


    public boolean getshowwelcome(){
        return sharedPreferences.getBoolean("welcomescreen",true);
    }

    public void putshowwelcome(boolean b){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("welcomescreen",b);
        editor.apply();
    }

    public boolean getsoundtoggle() {
        return sharedPreferences.getBoolean("soundtoggle",true);
    }

    public void putsoundtoggle(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("soundtoggle",b);
        editor.apply();
    }

   /* public void putloadingcomplete(int booknumber){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loadingcomplete"+toString().valueOf(booknumber),true);
        editor.apply();
    }
*/

   /*
    public boolean getaudioloaded() {
        return sharedPreferences.getBoolean("loadingcomplete"+toString().valueOf(getbooknumber()),false);
    }
    */

    /*public void putbuttontoggle(boolean toggle){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("togglevalue",toggle);
        editor.apply();
    }

    public boolean getbuttontoggle(){
        boolean buttontoggle = sharedPreferences.getBoolean("togglevalue",false);
        return buttontoggle;
    }*/
}
