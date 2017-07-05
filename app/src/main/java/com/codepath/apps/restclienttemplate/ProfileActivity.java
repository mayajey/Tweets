package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        String id = getIntent().getStringExtra("id");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);
        ft.commit();

        client = TwitterApp.getRestClient();

        if (id == null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user;
                    try {
                        user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        else {
            client.getOtherInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user;
                    try {
                        user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
           // Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
           // populateUserHeadline(tweet.getUser());
        }

    }

    public void populateUserHeadline(User user) {
        // set toolbar thing at top to user's name
        getSupportActionBar().setTitle(user.screenName);

        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        // set info
        Glide.with(this)
             .load(user.profileImageUrl)
             .into(ivProfileImage);

        tvUserName.setText(user.getScreenName());
        tvTagline.setText(user.tagLine);
        tvFollowers.setText("Followers: " + String.valueOf(user.followersCount));
        tvFollowing.setText("Following: " + String.valueOf(user.followingCount));
    }
}
