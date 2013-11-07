package com.bargainburg.android.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.Activities.CompanyDetailActivity;
import com.bargainburg.android.Adapters.ListAdapterCompanies;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CompaniesEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import roboguice.inject.InjectView;

import java.util.ArrayList;

public class CompaniesFragment extends RoboSherlockListFragment {

    ArrayList<Merchant> companies = new ArrayList<Merchant>();
    AlertDialog dialog;
    @InjectView(R.id.busy_view)
    FrameLayout busy_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter listAdapter = new ListAdapterCompanies(getActivity(), companies);
        setListAdapter(listAdapter);
        dialog = new AlertDialog.Builder(getActivity()).create();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (companies.size() == 0) {
            Intent intent = new Intent(getActivity(), APIService.class);
            intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES);
            getActivity().startService(intent);
        }
        Log.d("API", "resume comp");
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("API", "pausing comp");
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.simple_list_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        busy_view.setVisibility(View.VISIBLE);
        //Injection occurs in onViewCreated
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Merchant company = ((ListAdapterCompanies)getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
        intent.putExtra(EX.ITEM, new Gson().toJson(company));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.refresh:
                busy_view.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getActivity(), APIService.class);
                intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES);
                getActivity().startService(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void getCompanies(CompaniesEvent companiesEvent) {
        busy_view.setVisibility(View.GONE);
        companies = new ArrayList<Merchant>();
        if (companiesEvent.response.companies != null && companiesEvent.response.companies.size() > 0) {
            dialog.dismiss();
            for (Merchant company : companiesEvent.response.companies) {
                Log.d("API", company.name);
                companies.add(company);
            }
        } else {
        }
        ListAdapter listAdapter = new ListAdapterCompanies(getActivity(), companies);
        setListAdapter(listAdapter);
    }
}
