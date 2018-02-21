package com.example.fragmentdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.fragmentdemo.Adapter.CustomListAdapter;
import com.example.fragmentdemo.Model.Video;
import com.temboo.Library.eBay.Shopping.FindProducts;
import com.temboo.Library.eBay.Shopping.FindProducts.*;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valen on 2014/11/15.
 */
public class EbayList extends AsyncTask<Void, Void, String> {

    private List<Video> movieList = new ArrayList<Video>();
    //private TextView textView;
    private ListView listView;
    private Activity myActivity;
    private CustomListAdapter adapter;
    public EbayList(ListView listView,Activity myActivity){
        this.listView = listView;
        this.myActivity = myActivity;
    }
    @Override
    protected String doInBackground(Void... arg0) {
        try {
            TembooSession session = new TembooSession("valen91", "myFirstApp", "ac0d86a3aaba4ec7b7558ce0dd2e3730");

            FindProducts findProductsChoreo = new FindProducts(session);

// Get an InputSet object for the choreo
            FindProductsInputSet findProductsInputs = findProductsChoreo.newInputSet();

// Set inputs
            findProductsInputs.set_HideDuplicateItems("1");
            findProductsInputs.set_QueryKeywords("makeup");
            findProductsInputs.set_ResponseFormat("json");
            findProductsInputs.set_MaxEntries("30");
            findProductsInputs.set_AppID("YANKUNZH-a21a-45d4-859e-2ebbdcd86709");
            findProductsInputs.set_ProductSort("ReviewCount");

// Execute Choreo
            FindProductsResultSet findProductsResults = findProductsChoreo.execute(findProductsInputs);

            // Parse JSON response using org.json
            JSONObject results = new JSONObject(findProductsResults.get_Response());

            // Get items array
            JSONArray items = results.getJSONArray("Product");

            // myArrayList = new ArrayList<String>();

            // Get a list of 30 items
            for (int i=0; i<30; i++) {
                JSONObject item = items.getJSONObject(i);
                Video video = new Video();
                video.setTitle(item.getString("Title"));
                video.setChannelTitle(item.getString("ReviewCount"));
                video.setThumbnailUrl(item.getString("StockPhotoURL"));
                video.setDescription(item.getString("DetailsURL"));

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
