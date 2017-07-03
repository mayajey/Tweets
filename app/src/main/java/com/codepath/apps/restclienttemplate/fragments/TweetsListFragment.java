package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by mayajey on 7/3/17.
 */

public class TweetsListFragment extends Fragment {

    private TwitterClient client;
    private TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    // private SwipeRefreshLayout swipeContainer;

    public TweetsListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate array list of tweets
        tweets = new ArrayList<>();

        // construct adaptor from list of tweets
        tweetAdapter = new TweetAdapter(tweets);
    }

    // inflation happens in onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        // find RV
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
//        // Lookup the swipe container view
//        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                tweetAdapter.clear();
//                // populateTimeline();
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);


        // setup RV -- layout manager & setup w adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTweets.setAdapter(tweetAdapter);
        return v;
    }

    public void afterNewTweet(Tweet newTweet) {
        tweets.add(0, newTweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.getLayoutManager().scrollToPosition(0);
    }

    public void addItems(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                Tweet curr = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(curr);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
