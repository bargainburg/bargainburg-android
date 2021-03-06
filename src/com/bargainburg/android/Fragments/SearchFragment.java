package com.bargainburg.android.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Search;
import com.bargainburg.android.Activities.CompanyDetailActivity;
import com.bargainburg.android.Adapters.ListAdapterSearch;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CouponEvent;
import com.bargainburg.android.Otto.Events.SearchEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.util.ArrayList;

public class SearchFragment extends RoboSherlockListFragment {

    @InjectResource(R.color.custom_blue)
        private int customBlue;
    @InjectView(R.id.searchText)
    TextView searchText;
    @InjectView(R.id.searchButton)
    TextView searchButton;
    @InjectView(R.id.busy_view)
    FrameLayout busy_view;

    AlertDialog dialog;

    ArrayList<Search> searchResults = new ArrayList<Search>();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View newView = inflater.inflate(R.layout.search_fragment,container,false);
        return newView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    searchResults = new ArrayList<Search>();
                    ListAdapter listAdapter = new ListAdapterSearch(getActivity(), searchResults);
                    setListAdapter(listAdapter);
                }
                if (s.toString().length() > 0) {
                    Intent intent = new Intent(getActivity(), APIService.class);
                    intent.putExtra(APIService.API_CALL, APIService.SEARCH);
                    intent.putExtra(EX.QUERY, s.toString());
                    getActivity().startService(intent);
                    busy_view.setVisibility(View.VISIBLE);
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchText.getText().toString();
                Intent intent = new Intent(getActivity(), APIService.class);
                intent.putExtra(APIService.API_CALL, APIService.SEARCH);
                intent.putExtra(EX.QUERY, query);
                getActivity().startService(intent);
                busy_view.setVisibility(View.VISIBLE);
            }
        });
        //Injection occurs in onViewCreated
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Search searchItem = ((ListAdapterSearch)getListAdapter()).getItem(position);
        if (searchItem.type.equals("coupon")) {
            Intent intent = new Intent(getActivity(), APIService.class);
            intent.putExtra(APIService.API_CALL, APIService.GET_COUPON);
            intent.putExtra(EX.ID, searchItem.id);
            getActivity().startService(intent);
        } else {
            Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
            intent.putExtra(EX.ITEM, new Gson().toJson(searchItem));
            startActivity(intent);
        }
    }

    @Subscribe
    public void searchResults(SearchEvent searchEvent) {
        busy_view.setVisibility(View.GONE);
        if (searchEvent.response.results != null) {
            searchResults = new ArrayList<Search>();
            for (Search search : searchEvent.response.results) {
                searchResults.add(search);
            }
            ListAdapter listAdapter = new ListAdapterSearch(getActivity(), searchResults);
            setListAdapter(listAdapter);
        } else {
            Toast.makeText(getActivity(), "Error retrieving search results, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void getCoupon(CouponEvent couponEvent) {
        if (couponEvent.response.coupon != null) {
            new AlertDialog.Builder(getActivity()).setTitle(couponEvent.response.coupon.name)
                    .setMessage(couponEvent.response.coupon.description)
                    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            dialog = new AlertDialog.Builder(getActivity()).setTitle("Error")
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
