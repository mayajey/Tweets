package com.codepath.apps.restclienttemplate.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 *
 * Note: All models **must extend from** `BaseModel` as shown below.
 * 
 */
public class Tweet implements Parcelable {

	@Override
	public int describeContents() {
		return 0;
	}

	public String body; // tweet body maxChar 140
	public String createdAt; // time created
	public long uid; // database ID
	public User user; // info about the user

	public Tweet() {}

	public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
		Tweet tweet = new Tweet();
		// populate tweet info
		tweet.body = jsonObject.getString("text");
		tweet.uid = jsonObject.getLong("id");
		tweet.createdAt = jsonObject.getString("created_at");
		// TODO Ask for clarification on this line
		tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		return tweet;
	}

	@Override
	public void writeToParcel(android.os.Parcel dest, int flags) {
		dest.writeString(body);
		dest.writeString(createdAt);
		dest.writeLong(uid);
		dest.writeParcelable(user, flags);
	}

	private Tweet(android.os.Parcel in) {
		body = in.readString();
		createdAt = in.readString();
		uid = in.readLong();
		user = in.readParcelable(User.class.getClassLoader());
	}

	public static final Parcelable.Creator<Tweet> CREATOR
			= new Parcelable.Creator<Tweet>() {

		// This simply calls our new constructor (typically private) and
		// passes along the unmarshalled `Parcel`, and then returns the new object!
		@Override
		public Tweet createFromParcel(Parcel in) {
			return new Tweet(in);
		}

		// We just need to copy this and change the type to match our class.
		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};

}
