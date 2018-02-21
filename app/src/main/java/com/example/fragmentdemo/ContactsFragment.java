package com.example.fragmentdemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fragmentdemo.Adapter.CustomListAdapter;
import com.example.fragmentdemo.Model.Video;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ID = "vid";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.contacts_layout,
				container, false);
        final ListView listView = (ListView) contactsLayout.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                Video m = (Video)parent.getAdapter().getItem(position);

                String description = m.getDescription();
                String vid = m.getId();

                // Starting single contact activity
                Intent in = new Intent(getActivity(),
                        SecondActivity.class);
                in.putExtra(TAG_DESCRIPTION, description);
                in.putExtra(TAG_ID, vid);
                startActivity(in);

            }
        });
            //YoutubeList myYoutubeList = new YoutubeList(listView,getActivity());//.execute();
            new YoutubeList(listView,getActivity()).execute();
            //movieList = myYoutubeList.getMovieList();
            return contactsLayout;
        }

    }
