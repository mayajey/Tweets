package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.restclienttemplate.SmartFragmentStatePagerAdapter;

/**
 * Created by mayajey on 7/3/17.
 */

public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // return total number of fragments there are
    @Override
    public int getCount() {
        return 2;
    }

    // return fragment to use depending on position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimelineFragment();
        }
        else if (position == 1) {
            return new MentionsTimelineFragment();
        }
        else return null;
    }

    // return fragment title used for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
