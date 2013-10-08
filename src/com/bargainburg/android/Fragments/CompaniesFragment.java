package com.bargainburg.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.Adapters.ListAdapterCompanies;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CompanyEvent;
import com.bargainburg.android.R;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class CompaniesFragment extends RoboSherlockListFragment {

    ArrayList<Merchant> companies = new ArrayList<Merchant>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_COMPANIES);
        getActivity().startService(intent);
        ListAdapter listAdapter = new ListAdapterCompanies(getActivity(), companies);
        setListAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("API", "resume");
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("API", "pausing");
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
        //Injection occurs in onViewCreated
    }

    @Subscribe
    public void getCompanies(CompanyEvent companyEvent) {
        if (companyEvent.response.success) {
            companies = new ArrayList<Merchant>();
            Log.d("API", "success!" + companyEvent.response.companies.get(0).name);
            for (Merchant company : companyEvent.response.companies) {
                Log.d("API", company.name);
                companies.add(company);
            }
            ListAdapter listAdapter = new ListAdapterCompanies(getActivity(), companies);
            setListAdapter(listAdapter);
        } else {
            Log.d("API", "failure!");
        }
    }
}
