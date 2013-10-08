package com.bargainburg.android.Activities;

import android.os.Bundle;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockListActivity;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:58 AM
 */
public class CategoryListActivity extends RoboSherlockListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        getSupportActionBar().setTitle(savedInstanceState.getString(EX.CATEGORY));
        
    }
}
