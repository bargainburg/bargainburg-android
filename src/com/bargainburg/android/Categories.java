package com.bargainburg.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.bargainburg.android.Adapters.ListAdapterCategories;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;

import java.util.ArrayList;

public class Categories extends RoboSherlockListFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> testListArray = new ArrayList<String>();
        String listTestString = "aString";
        for (int i = 0; i < 10; i++) {
            testListArray.add(listTestString);
            listTestString = listTestString + "" + i;
        }
        ListAdapter listAdapter = new ListAdapterCategories(getActivity(), testListArray);
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.simple_list_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
