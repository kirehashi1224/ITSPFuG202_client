package jp.ac.titech.itpro.sdl.itspfug202;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence[] tabTitles = {"詳細"};

    public DetailFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new DetailInformationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return tabTitles.length;
    }
}
