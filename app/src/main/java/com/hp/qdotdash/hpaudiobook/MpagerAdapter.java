package com.hp.qdotdash.hpaudiobook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MpagerAdapter extends FragmentPagerAdapter {

    public MpagerAdapter(FragmentManager fm) {
        super(fm);
    }
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    @Override
    public int getCount() {
        return 7;
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i){
            case 0 : fragment = new Fragment_book1();
                     break;
            case 1 : fragment = new Fragment_book2();
                break;
            case 2 : fragment = new Fragment_book3();
                break;
            case 3 : fragment = new Fragment_book4();
                break;
            case 4 : fragment = new Fragment_book5();
                break;
            case 5 : fragment = new Fragment_book6();
                break;
            case 6 : fragment = new Fragment_book7();
                break;
        }
        return fragment;
    }

}




