package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by mayajey on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    List<Tweet> mTweets;
    Context context;

    // pass Tweets into array
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    // for each row, inflate layout & pass into ViewHolder class
    // only invoked when you need to create a new row (else call onBindViewHolder)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the actual row
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

        // create the ViewHolder
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get tweet by position
        Tweet current = mTweets.get(position);
        // populate view based on this tweet
        holder.tvUserName.setText(current.user.name);
        holder.tvBody.setText(current.body);
        holder.tvCreatedAt.setText(current.createdAt);
        String screenName = "  (@" + current.getUser().getScreenName() + ")";
        holder.tvScreenName.setText(screenName);

        // load profile image URL with Glide
        Glide.with(context)
                .load(current.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // create ViewHolder class/pattern within
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvCreatedAt;
        public ImageButton btnReply;
        // public ImageButton btnRetweet;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            btnReply = (ImageButton) itemView.findViewById(R.id.btnReply);
            itemView.setOnClickListener(this);
            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtnReply();
                }
            });
            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickProfileImage();
                }
            });
        }

        @Override
        public void onClick(View v) {
            // Get position of tweet being clicked, ensure it's valid
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the tweet at that position
                Tweet tweet = mTweets.get(position);
                // Communicate btwn activities -- adapter & showing details
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // use parceler to wrap tweet
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show the activity
                context.startActivity(intent);
            }
        }

        public void onClickBtnReply() {
            // Get position of tweet being clicked, ensure it's valid
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the tweet at that position
                Tweet tweet = mTweets.get(position);
                // Communicate btwn activities -- adapter & showing details
                Intent intent = new Intent(context, ComposeActivity.class);
                // use parceler to wrap tweet
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                intent.putExtra("action", "reply");
                // show the activity -- different from an adapter
                ((AppCompatActivity) context).startActivityForResult(intent, 10);
            }
        }

        public void onClickProfileImage() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the tweet at that position
                Tweet tweet = mTweets.get(position);
                // Communicate btwn activities -- adapter & showing details
                Intent intent = new Intent(context, ProfileActivity.class);
                // use parceler to wrap tweet
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                intent.putExtra("id", "other");
                intent.putExtra("screen_name", tweet.getUser().getScreenName());
                // show the activity -- different from an adapter
                ((AppCompatActivity) context).startActivity(intent);
            }
        }
    }
}