package com.example.fragmentdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.fragmentdemo.Adapter.CustomListAdapter;
import com.example.fragmentdemo.Adapter.FeedListAdapter;
import com.example.fragmentdemo.Model.FeedItem;
import com.example.fragmentdemo.Model.Video;
import com.temboo.Library.Tumblr.Post.RetrievePublishedPosts;
import com.temboo.Library.Tumblr.Post.RetrievePublishedPosts.*;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valen on 2014/11/15.
 */
public class TumblrList extends AsyncTask<Void, Void, String> {

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    //private TextView textView;
    private ListView listView;
    private Activity myActivity;
    private FeedListAdapter adapter;

    public TumblrList(ListView listView, Activity myActivity) {
        this.listView = listView;
        this.myActivity = myActivity;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        try {
            TembooSession session = new TembooSession("valen91", "myFirstApp", "ac0d86a3aaba4ec7b7558ce0dd2e3730");
            RetrievePublishedPosts retrievePublishedPostsChoreo = new RetrievePublishedPosts(session);

// Get an InputSet object for the choreo
            RetrievePublishedPostsInputSet retrievePublishedPostsInputs = retrievePublishedPostsChoreo.newInputSet();

// Set credential to use for execution
            retrievePublishedPostsInputs.setCredential("TumKey");

// Set inputs
            retrievePublishedPostsInputs.set_Format("text");
            retrievePublishedPostsInputs.set_Type("photo");
            retrievePublishedPostsInputs.set_Limit("20");
            retrievePublishedPostsInputs.set_BaseHostname("beautyaesthetic.tumblr.com");

// Execute Choreo
            RetrievePublishedPostsResultSet retrievePublishedPostsResults = retrievePublishedPostsChoreo.execute(retrievePublishedPostsInputs);

            JSONObject results = new JSONObject(retrievePublishedPostsResults.get_Response());

            JSONObject response = results.getJSONObject("response");

            JSONArray items = response.getJSONArray("posts");


            for (int i=0; i<20; i++) {
                JSONObject item = items.getJSONObject(i);
                JSONArray photos = item.getJSONArray("photos");
                JSONObject photo = photos.getJSONObject(0);
                JSONObject profile = photo.getJSONObject("original_size");
                FeedItem feedItem = new FeedItem();
                feedItem.setName(item.getString("slug"));
                feedItem.setTimeStamp(item.getString("timestamp"));
                feedItem.setUrl(item.getString("short_url"));
                feedItem.setStatus(item.getString("caption"));
                feedItem.setProfilePic(profile.getString("url"));
                feedItem.setImage(profile.getString("url"));

                feedItemList.add(feedItem);

            }
        } catch (Exception e) {
            // if an exception occurred, log it
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(String result) {
        try {
            adapter = new FeedListAdapter(this.myActivity, feedItemList);
            //ArrayAdapter<String> myAdapter = new
            //ArrayAdapter<String>(this.mainContext,R.layout.cooltextview, R.id.label,movieList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            // if an exception occurred, show an error message
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }
}
