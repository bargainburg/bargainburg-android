package com.bargainburg.android.Activities;

import android.os.Bundle;
import android.widget.TextView;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.google.gson.Gson;
import roboguice.inject.InjectView;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 4:20 PM
 */
public class CompanyDetailActivity extends RoboSherlockActivity {

    Merchant company;
    @InjectView(R.id.text)TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_detail);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        company = new Gson().fromJson(savedInstanceState.getString(EX.ITEM), Merchant.class);
        getSupportActionBar().setTitle(company.name);
        textView.setText(company.updatedDate);
    }

}
