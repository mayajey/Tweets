package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet tweet;
    TextView tvUserName;
    TextView tvBody;
    TextView tvCreatedAt;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        User user = tweet.getUser();
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        String imageUrl = user.getProfileImageUrl();

        Glide.with(this)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 15, 0))
                //.placeholder(placeholderId)
                //.error(placeholderId)
                .into(ivProfileImage);

        tvBody.setText(tweet.getBody());
        tvUserName.setText(user.getScreenName());
        tvCreatedAt.setText(tweet.getCreatedAt());
    }
}
