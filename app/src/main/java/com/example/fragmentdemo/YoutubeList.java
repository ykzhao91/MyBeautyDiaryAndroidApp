package com.example.fragmentdemo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fragmentdemo.Adapter.CustomListAdapter;
import com.example.fragmentdemo.Model.Video;
import com.temboo.Library.YouTube.Search.ListSearchResults;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valen on 2014/11/14.
 */

public class YoutubeList extends AsyncTask<Void, Void, String> {

    private List<Video> movieList = new ArrayList<Video>();
    //private TextView textView;
    private ListView listView;
    private Activity myActivity;
    private CustomListAdapter adapter;
    public YoutubeList(ListView listView,Activity myActivity){
        this.listView = listView;
        this.myActivity = myActivity;
    }
    @Override
    protected String doInBackground(Void... arg0) {
        try {
            TembooSession session = new TembooSession("valen91", "myFirstApp", "ac0d86a3aaba4ec7b7558ce0dd2e3730");

            ListSearchResults listSearchResultsChoreo = new ListSearchResults(session);

// Get an InputSet object for the choreo
            ListSearchResults.ListSearchResultsInputSet listSearchResultsInputs = listSearchResultsChoreo.newInputSet();

// Set inputs
            listSearchResultsInputs.set_Query("makeup tutorial");
            listSearchResultsInputs.set_MaxResults("50");

// Execute Choreo
            ListSearchResults.ListSearchResultsResultSet listSearchResultsResults = listSearchResultsChoreo.execute(listSearchResultsInputs);

            // Parse JSON response using org.json
            JSONObject results = new JSONObject(listSearchResultsResults.get_Response());

            // Get items array
            JSONArray items = results.getJSONArray("items");

           // myArrayList = new ArrayList<String>();

            // Get a list of 30 items
            for (int i=0; i<30; i++) {
                JSONObject item = items.getJSONObject(i);

                // Get the snippet object within the first item
                JSONObject vid = item.getJSONObject("id");
                JSONObject snippet = item.getJSONObject("snippet");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject medium = thumbnails.getJSONObject("medium");
                Video video = new Video();
                video.setTitle(snippet.getString("title"));
                video.setChannelTitle(snippet.getString("channelTitle"));
                video.setThumbnailUrl(medium.getString("url"));
                video.setDescription(snippet.getString("description"));
                video.setId(vid.getString("videoId"));

                // Parse the title and description fields
                //String title = snippet.getString("title");
                //String description = snippet.getString("description");
                movieList.add(video);

            }
            // Print title and description
            //return title + " - " + description;
        } catch(Exception e) {
            // if an exception occurred, log it
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(String result) {
        try {
            adapter = new CustomListAdapter(this.myActivity,movieList);
            //ArrayAdapter<String> myAdapter = new
                    //ArrayAdapter<String>(this.mainContext,R.layout.cooltextview, R.id.label,movieList);
            listView.setAdapter(adapter);
        } catch(Exception e) {
            // if an exception occurred, show an error message
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }
}