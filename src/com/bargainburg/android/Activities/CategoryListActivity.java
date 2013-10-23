package com.bargainburg.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.Adapters.ListAdapterCompanies;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CompaniesEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockListActivity;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:58 AM
 */
public class CategoryListActivity extends RoboSherlockListActivity {

    ArrayList<Merchant> companies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_fragment);
        companies = new ArrayList<Merchant>();
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        ListAdapter adapter = new ListAdapterCompanies(this, companies);
        setListAdapter(adapter);
        getSupportActionBar().setTitle(savedInstanceState.getString(EX.CATEGORY));
        int id = savedInstanceState.getInt(EX.ID);
        Intent intent = new Intent(this, APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES_FOR_CATEGORY);
        intent.putExtra(EX.ID, id);
        startService(intent);
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
        Merchant company = ((ListAdapterCompanies)getListAdapter()).getItem(position);
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra(EX.ITEM, new Gson().toJson(company));
        startActivity(intent);
    }

    @Subscribe
    public void getCompanies(CompaniesEvent companiesEvent) {
        if (companiesEvent.response.success) {
        companies = new ArrayList<Merchant>();
        for (Merchant company : companiesEvent.response.companies) {
            companies.add(company);
        }
        ListAdapter listAdapter = new ListAdapterCompanies(this, companies);
        setListAdapter(listAdapter);
        } else {
            Log.d("API", "failure!");
        }
    }
}
