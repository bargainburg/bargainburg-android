package com.bargainburg.android.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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

    @Subscribe
    public void getCompanies(CompaniesEvent companiesEvent) {
        if (companiesEvent.response.companies != null) {
            busy_view.setVisibility(View.GONE);
            dialog.dismiss();
            companies = new ArrayList<Merchant>();
            Log.d("API", "success!" + companiesEvent.response.companies.get(0).name);
            for (Merchant company : companiesEvent.response.companies) {
                Log.d("API", company.name);
                companies.add(company);
            }
            ListAdapter listAdapter = new ListAdapterCompanies(getActivity(), companies);
            setListAdapter(listAdapter);
        } else {
            dialog = new AlertDialog.Builder(getActivity()).setTitle("Error")
                    .setMessage("It seems there was an error retrieving the list of companies! Would you like to load them again?")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), APIService.class);
                            intent.putExtra(APIService.API_CALL, APIService.GET_CATEGORIES);
                            getActivity().startService(intent);
                            Intent nintent = new Intent(getActivity(), APIService.class);
                            nintent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES);
                            getActivity().startService(nintent);
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
