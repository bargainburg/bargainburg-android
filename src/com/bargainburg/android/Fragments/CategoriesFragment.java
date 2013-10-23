package com.bargainburg.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.bargainburg.android.API.APIService;
import com.bargainburg.android.API.Model.Category;
import com.bargainburg.android.Activities.CategoryListActivity;
import com.bargainburg.android.Adapters.ListAdapterCategories;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CategoryEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class CategoriesFragment extends RoboSherlockListFragment {

    ArrayList<Category> categories = new ArrayList<Category>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), APIService.class);
        intent.putExtra(APIService.API_CALL, APIService.GET_CATEGORIES);
        getActivity().startService(intent);
        Log.d("API", "starting service");
        ListAdapter listAdapter = new ListAdapterCategories(getActivity(), categories);
        setListAdapter(listAdapter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.simple_list_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), CategoryListActivity.class);
        Category category = ((Category)getListAdapter().getItem(position));
        intent.putExtra(EX.CATEGORY, category.name);
        intent.putExtra(EX.ID, category.id);
        getActivity().startActivity(intent);
    }

    @Subscribe
    public void getCategories(CategoryEvent categoryEvent) {
        if (categoryEvent.response.success) {
            categories = new ArrayList<Category>();
            Log.d("API", "success!" + categoryEvent.response.categories.get(0).name);
            for (Category category : categoryEvent.response.categories) {
                Log.d("API", category.name);
                categories.add(category);
            }
            ListAdapter listAdapter = new ListAdapterCategories(getActivity(), categories);
            setListAdapter(listAdapter);
        } else {
            Log.d("API", "failure!");
        }
    }

}
