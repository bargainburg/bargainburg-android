package com.bargainburg.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Coupon;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.Adapters.ListAdapterCoupons;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CompanyEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockListActivity;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import roboguice.inject.InjectView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 4:20 PM
 */
public class CompanyDetailActivity extends RoboSherlockListActivity {

    Merchant company;
    @InjectView(R.id.phone_number_tv)TextView phoneNumber;

    ArrayList<Coupon> coupons = new ArrayList<Coupon>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_detail);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        company = new Gson().fromJson(savedInstanceState.getString(EX.ITEM), Merchant.class);
        Intent intent = new Intent(this, APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COMPANY_COUPONS);
        intent.putExtra(EX.ID, company.id);
        startService(intent);
        getSupportActionBar().setTitle(company.name);
        phoneNumber.setText(company.phone);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Subscribe
    public void getCompany(CompanyEvent companyEvent) {
        if (companyEvent.response.success) {
            company = companyEvent.response.company;
            Log.d("API", "success!" + company.name);
            for (Coupon coupon: company.coupons) {
                Log.d("API", coupon.name);
                coupons.add(coupon);
            }
            ListAdapter listAdapter = new ListAdapterCoupons(this, coupons);
            setListAdapter(listAdapter);
        } else {
            Log.d("API", "failure!");
        }
    }


}
