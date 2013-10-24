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
import com.bargainburg.android.API.Model.Category;
import com.bargainburg.android.Activities.CategoryListActivity;
import com.bargainburg.android.Adapters.ListAdapterCategories;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CategoryEvent;
import com.bargainburg.android.R;
import com.bargainburg.android.Util.EX;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.squareup.otto.Subscribe;
import roboguice.inject.InjectView;

import java.util.ArrayList;

public class CategoriesFragment extends RoboSherlockListFragment {

    ArrayList<Category> categories = new ArrayList<Category>();
    AlertDialog dialog;
    @InjectView (R.id.busy_view)
    FrameLayout busy_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("API", "starting service");
        ListAdapter listAdapter = new ListAdapterCategories(getActivity(), categories);
        setListAdapter(listAdapter);
        dialog = new AlertDialog.Builder(getActivity()).create();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("API", "resume cate");
        if (categories.size() == 0) {
            Intent intent = new Intent(getActivity(), APIService.class);
            intent.putExtra(APIService.API_CALL, APIService.GET_CATEGORIES);
            getActivity().startService(intent);
        }
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("API", "pause cate");
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
        if (categories.size() == 0) {
            busy_view.setVisibility(View.VISIBLE);
        }
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
        if (categoryEvent.response.categories != null) {
            busy_view.setVisibility(View.GONE);
            dialog.dismiss();
            categories = new ArrayList<Category>();
            for (Category category : categoryEvent.response.categories) {
                Log.d("API", category.name);
                categories.add(category);
            }
            ListAdapter listAdapter = new ListAdapterCategories(getActivity(), categories);
            setListAdapter(listAdapter);
        } else {
            dialog = new AlertDialog.Builder(getActivity()).setTitle("Error")
                    .setMessage("It seems there was an error retrieving the list of categories! Would you like to load them again?")
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
