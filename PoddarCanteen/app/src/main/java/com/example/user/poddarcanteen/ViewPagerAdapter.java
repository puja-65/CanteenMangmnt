package com.example.user.poddarcanteen;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new receivedtab();
        } else if (position == 1) {
            return new cookingtab();
        } else return new finishedtab();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
