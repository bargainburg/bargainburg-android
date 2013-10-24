package com.bargainburg.android.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
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
import roboguice.inject.InjectView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:58 AM
 */
public class CategoryListActivity extends RoboSherlockListActivity {

    ArrayList<Merchant> companies;
    Context context;
    AlertDialog dialog;
    int id;

    @InjectView (R.id.nothing_available_text)
    TextView nothingAvailable;

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
        id = savedInstanceState.getInt(EX.ID);
        context = this;
        Intent intent = new Intent(this, APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES_FOR_CATEGORY);
        intent.putExtra(EX.ID, id);
        startService(intent);
        dialog = new AlertDialog.Builder(this).create();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Merchant company = ((ListAdapterCompanies) getListAdapter()).getItem(position);
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra(EX.ITEM, new Gson().toJson(company));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void getCompanies(CompaniesEvent companiesEvent) {
        if (companiesEvent.response.companies != null) {
            getListView().setVisibility(View.VISIBLE);
            nothingAvailable.setVisibility(View.GONE);
            companies = new ArrayList<Merchant>();
            for (Merchant company : companiesEvent.response.companies) {
                companies.add(company);
            }
            if (companies.size() == 0) {
                getListView().setVisibility(View.GONE);
                nothingAvailable.setVisibility(View.VISIBLE);
            }
            ListAdapter listAdapter = new ListAdapterCompanies(this, companies);
            setListAdapter(listAdapter);
        } else {
            dialog = new AlertDialog.Builder(this).setTitle("Error")
                    .setMessage("It seems there was an error retrieving the list of companies! Would you like to load them again?")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, APIService.class);
                            intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES_FOR_CATEGORY);
                            intent.putExtra(EX.ID, id);
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
}
