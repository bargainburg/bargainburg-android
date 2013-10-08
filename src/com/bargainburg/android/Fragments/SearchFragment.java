package com.bargainburg.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bargainburg.android.R;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectResource;

public class SearchFragment extends RoboSherlockFragment {

    @InjectResource(R.color.custom_blue)
        private int customBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View newView = inflater.inflate(R.layout.search_fragment,container,false);
        return newView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Injection occurs in onViewCreated
    }
}
