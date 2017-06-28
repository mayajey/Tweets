package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet tweet;
    TextView tvUserName;
    TextView tvBody;
    TextView tvCreatedAt;
    ImageView ivProfileImage;
    Button btnUnfavorite;
    ImageButton ibFavorite;

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
        btnUnfavorite = (Button) findViewById(R.id.btnUnfavorite);
        ibFavorite = (ImageButton) findViewById(R.id.ibFavorite);
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

        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavorite();
            }
        });

        btnUnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUnfavorite();
            }
        });
    }

    protected void onClickFavorite() {
        TwitterClient twitterClient = new TwitterClient(this);
        twitterClient.sendFavorite(String.valueOf(tweet.getUid()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Favorite SUCCESS", response.toString());
                try {
                    Tweet newTweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Favorite FAILURE", responseString);
                throwable.printStackTrace();
            }
        });
    }

    protected void onClickUnfavorite() {
        TwitterClient twitterClient = new TwitterClient(this);
        twitterClient.sendUnfavorite(String.valueOf(tweet.getUid()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Unfavorite SUCCESS", response.toString());
                try {
                    Tweet newTweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Unfavorite FAILURE", responseString);
                throwable.printStackTrace();
            }
        });
    }


}
