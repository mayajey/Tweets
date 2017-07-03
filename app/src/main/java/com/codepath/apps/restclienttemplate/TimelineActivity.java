package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class TimelineActivity extends AppCompatActivity {

    private final int COMPOSE_REQUEST_CODE = 10;
    MenuItem miActionProgressItem;
    HomeTimelineFragment homeTimelineFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        // init hometimelinefragment?

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // set adapter for pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));

        // configure tab layout to use pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        homeTimelineFragment = (HomeTimelineFragment) ((TweetsPagerAdapter) vpPager.getAdapter()).getItem(0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        Intent intent = new Intent(this, ComposeActivity.class);
        intent.putExtra("action","compose");
        startActivityForResult(intent, COMPOSE_REQUEST_CODE);
    }

    public void onProfileView(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check request code and result code first
        if (resultCode == requestCode) {
            Tweet newTweet = (Tweet) data.getParcelableExtra("tweet");
            homeTimelineFragment.afterNewTweet(newTweet);
        }
    }
}
