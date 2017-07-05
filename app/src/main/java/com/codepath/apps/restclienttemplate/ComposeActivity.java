package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    EditText etComposeTweet;
    TextView tvCharCount;
    ImageButton btPost;
    String action;
    private final int COMPOSE_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btPost = (ImageButton) findViewById(R.id.btPost);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
        etComposeTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String base = " chars left";
                int len = etComposeTweet.getText().length();
                int countLeft = 140 - len;
                String finalText = String.valueOf(countLeft) + base;
                tvCharCount.setText(finalText);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPost();
            }
        });
        action = getIntent().getStringExtra("action");
    }

    protected void onClickPost() {
        TwitterClient twitterClient = new TwitterClient(this);
        if (action.equals("reply")) {
            Tweet replyTo = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
            String replyID = String.valueOf(replyTo.getUid());
            String replyUsername = replyTo.getUser().getScreenName();
            String fullMessage = "@" + replyUsername + " " + etComposeTweet.getText().toString();
            twitterClient.sendReply(replyID, fullMessage, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("Reply SUCCESS", response.toString());
                    try {
                        Tweet newTweet = Tweet.fromJSON(response);
                        Intent passBack = new Intent();
                        // Pass data back
                        passBack.putExtra("tweet", newTweet);
                        setResult(COMPOSE_REQUEST_CODE, passBack);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("Reply FAILURE", responseString);
                    throwable.printStackTrace();
                }
            });
        }
        else {
            twitterClient.sendTweet(etComposeTweet.getText().toString(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("Post SUCCESS", response.toString());
                    try {
                        Tweet newTweet = Tweet.fromJSON(response);
                        Intent passBack = new Intent();
                        // Pass data back
                        passBack.putExtra("tweet", newTweet);
                        setResult(COMPOSE_REQUEST_CODE, passBack);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("Post FAILURE", responseString);
                    throwable.printStackTrace();
                }
            });
        }
    }
}
