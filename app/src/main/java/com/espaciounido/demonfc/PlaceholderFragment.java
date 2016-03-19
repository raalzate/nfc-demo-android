package com.espaciounido.demonfc;

/**
 * Created by MyMac on 14/03/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public  class PlaceholderFragment extends Fragment implements INfcView {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView textView;
    private ListView listView;
    private List<HashMap<String, String>> data;
    private SimpleAdapter adapter;

    public PlaceholderFragment() {
    }


    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);
        listView = (ListView) rootView.findViewById(R.id.list_view);

        initVars();
        return rootView;
    }

    private void initVars() {
        data = new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(),data,android.R.layout.simple_list_item_1,
                new String[] { "text1" },
                new int[] { android.R.id.text1 });
        listView.setAdapter(adapter);
    }

    @Override
    public void setTitle(String text){
        textView.setText(text);
    }

    @Override
    public void setDataset(String[] dataset) {
        data.clear();
        for(int i = 0; i < dataset.length; i++ ) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("text1", dataset[i]);
            data.add(hashMap);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }


}