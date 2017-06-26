package com.codepath.apps.restclienttemplate.models;

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
// parcelable here
public class Tweet{

	public String body; // tweet body maxChar 140
	public String createdAt; // time created
	public long uid; // database ID
	public User user; // info about the user

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

}
