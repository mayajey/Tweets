package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
	public static final String REST_URL = "https://api.twitter.com/1.1/";
	public static final String REST_CONSUMER_KEY = "HFI1zJf0Mn4wkfAJYjeh6XWgn";
	public static final String REST_CONSUMER_SECRET = "uFMYCOg5ERqJr8YdDhWW2tD8vPtOlxJBpSDZQKG14RaUug5Oqy";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", "25");
		// Every tweet has value greater than 1; NOTE: useful for pull-to-refresh; want ID greater than current ones!
		params.put("since_id", "1");
		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", "25");
		// Every tweet has value greater than 1; NOTE: useful for pull-to-refresh; want ID greater than current ones!
		params.put("since_id", "1");
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", "25");
		params.put("screen_name", screenName);
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}

	// ?screen_name=twitterdev
	public void getOtherInfo(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json" + "?screen_name=" + screenName);
		client.get(apiUrl, null, handler);
	}

	public void sendTweet(String message, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}

	public void sendRetweet(String uid, AsyncHttpResponseHandler handler) {
		String apiUrl = "https://api.twitter.com/1.1/statuses/retweet/" + uid + ".json";
		RequestParams params = new RequestParams();
		client.post(apiUrl, params, handler);
	}

	public void sendFavorite(String uid, AsyncHttpResponseHandler handler) {
		String apiUrl = REST_URL + "favorites/create.json" + "?id=" + uid;
		RequestParams params = new RequestParams();
		client.post(apiUrl, params, handler);
	}

	public void sendUnfavorite(String uid, AsyncHttpResponseHandler handler) {
		String apiUrl = REST_URL + "favorites/destroy.json" + "?id=" + uid;
		RequestParams params = new RequestParams();
		client.post(apiUrl, params, handler);
	}

	public void sendReply(String replyId, String message, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("in_reply_to_status_id", replyId);
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}
}