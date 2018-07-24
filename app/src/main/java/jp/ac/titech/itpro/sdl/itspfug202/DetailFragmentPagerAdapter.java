package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle;

    private CharSequence[] tabTitles = {"詳細", "テスト"};

    public DetailFragmentPagerAdapter(FragmentManager fm, Bundle bundle){
        super(fm);
        this.bundle = bundle;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                DetailInformationFragment detailInformationFragment = new DetailInformationFragment();
                detailInformationFragment.setArguments(bundle);
                return detailInformationFragment;
            case 1:
                TestTabFragment testTabFragment = new TestTabFragment();
                testTabFragment.setArguments(bundle);
                return testTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return tabTitles.length;
    }
}
