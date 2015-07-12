package net.icepc.vertretungsplan;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public Fragment getItem(int num) {
        switch (num){
            case 0:
                return new todayFragment();
            case 1:
                return new tomorrowFragment();
            case 2:
                return new infoFragment();
            }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Heute";
            case 1:
                return "Morgen";
            case 2:
                return "Infos";
        }
        return null;
    }

}