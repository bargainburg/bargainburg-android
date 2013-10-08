package com.bargainburg.android.Activities;

import android.os.Bundle;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:51 AM
 */
public class CompanyDetailActivity extends RoboSherlockActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }

        getSupportActionBar().setTitle("");
    }
}
