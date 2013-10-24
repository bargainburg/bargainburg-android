package com.bargainburg.android.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Coupon;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.Adapters.ListAdapterCoupons;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CompanyEvent;
import com.bargainburg.android.Otto.Events.CouponEvent;
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
    @InjectView(R.id.email_tv)TextView email;
    @InjectView(R.id.hours_tv)TextView hours;
    @InjectView(R.id.price_tv)TextView price;
    Context context;
    AlertDialog dialog;

    ArrayList<Coupon> coupons = new ArrayList<Coupon>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_detail);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        context = this;
        company = new Gson().fromJson(savedInstanceState.getString(EX.ITEM), Merchant.class);
        phoneNumber.setText(company.phone);
        email.setText(company.email);
        hours.setText(company.hours);
        if (company.priceRange == 1) {
            price.setText(EX.PRICE);
        } else if (company.priceRange == 2) {
            price.setText("" + EX.PRICE + EX.PRICE);
        } else if (company.priceRange == 3) {
            price.setText("" + EX.PRICE + EX.PRICE + EX.PRICE);
        } else {
            price.setText("" + EX.PRICE + EX.PRICE + EX.PRICE + EX.PRICE);
        }
        Intent intent = new Intent(this, APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COMPANY_COUPONS);
        intent.putExtra(EX.ID, company.id);
        startService(intent);
        getSupportActionBar().setTitle(company.name);
        phoneNumber.setText(company.phone);
        dialog = new AlertDialog.Builder(this).create();
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COUPON);
        intent.putExtra(EX.ID, coupons.get(position).id);
        startService(intent);
    }

    @Subscribe
    public void getCompany(CompanyEvent companyEvent) {
        if (companyEvent.response.company != null) {
            dialog.dismiss();
            company = companyEvent.response.company;
            Log.d("API", "success!" + company.name);
            coupons = new ArrayList<Coupon>();
            for (Coupon coupon: company.coupons) {
                Log.d("API", coupon.name);
                coupons.add(coupon);
            }
            ListAdapter listAdapter = new ListAdapterCoupons(this, coupons);
            setListAdapter(listAdapter);
        } else {
            dialog = new AlertDialog.Builder(this).setTitle("Error")
                    .setMessage("It seems there was an error retrieving the list of coupons! Would you like to load them again?")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, APIService.class);
                            intent.putExtra(APIService.API_CALL, APIService.GET_COMPANY_COUPONS);
                            intent.putExtra(EX.ID, company.id);
                            startService(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
    }

    @Subscribe
    public void getCoupon(CouponEvent couponEvent) {
        if (couponEvent.response.coupon != null) {
            new AlertDialog.Builder(this).setTitle(couponEvent.response.coupon.name)
                    .setMessage(couponEvent.response.coupon.description)
                    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            dialog = new AlertDialog.Builder(this).setTitle("Error")
                    .setMessage("It seems there was an error retrieving the coupon.")
                    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
    }


}
