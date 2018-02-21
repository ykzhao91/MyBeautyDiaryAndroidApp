package com.example.fragmentdemo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fragmentdemo.Model.Video;

public class NewsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.news_layout, container,
				false);
        ListView listView = (ListView) newsLayout.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                Video m = (Video)parent.getAdapter().getItem(position);

                String URL = m.getDescription();

                // Starting single contact activity
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(in);

            }
        });
        new EbayList(listView,getActivity()).execute();

		return newsLayout;
	}



}
