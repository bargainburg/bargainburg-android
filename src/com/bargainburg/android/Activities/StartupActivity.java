package com.bargainburg.android.Activities;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import com.actionbarsherlock.app.ActionBar;
import com.bargainburg.android.Fragments.CategoriesFragment;
import com.bargainburg.android.Fragments.CompaniesFragment;
import com.bargainburg.android.Fragments.SearchFragment;
import com.bargainburg.android.MainApp;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.R;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import roboguice.inject.InjectView;

public class StartupActivity extends RoboSherlockFragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
    @InjectView(R.id.pager)ViewPager pager;
    ActionBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Bargain Burg");
        Log.i(MainApp.TAG, "onCreate");
        setContentView(R.layout.startup_tabs);

        bar = getSupportActionBar();
        bar.addTab(bar.newTab().setText("Categories").setTabListener(this).setTag(CategoriesFragment.class.getName()));
        bar.addTab(bar.newTab().setText("Companies").setTabListener(this).setTag(CompaniesFragment.class.getName()));
        bar.addTab(bar.newTab().setText("Search").setTabListener(this).setTag(SearchFragment.class.getName()));
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setSelectedNavigationItem(0);
        pager.setAdapter(new OakAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
    }

    @Override
    public void onPageSelected(int i) {
        bar.setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    private class OakAdapter extends FragmentPagerAdapter{

        public OakAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return Fragment.instantiate(StartupActivity.this, bar.getTabAt(i).getTag().toString(), null);
        }

        @Override
        public int getCount() {
            return bar.getTabCount();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = this.getWindow();

        // Eliminates color banding
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

}

