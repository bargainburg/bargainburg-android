package com.bargainburg.android.API;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.bargainburg.android.API.Responses.*;
import com.bargainburg.android.Data.Datastore;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CategoryEvent;
import com.bargainburg.android.Otto.Events.CompaniesEvent;
import com.bargainburg.android.Otto.Events.CompanyEvent;
import com.bargainburg.android.Otto.Events.SearchEvent;
import com.bargainburg.android.Util.EX;
import com.google.gson.Gson;
import com.google.inject.Inject;
import roboguice.service.RoboIntentService;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class APIService extends RoboIntentService {

    public static final String API_CALL = "ApiService_ApiCall";

    public static final int GET_CATEGORIES = 0x01;
    public static final int GET_COMPANIES = 0x02;
    public static final int GET_COUPONS = 0x03;
    public static final int GET_COMPANIES_FOR_CATEGORY = 0x04;
    public static final int GET_COMPANY_COUPONS = 0x05;
    public static final int SEARCH = 0x06;

    private Gson mGson = new Gson();

    @Inject BargainBurgApi mBargainBurgApi;
    @Inject Datastore mDatastore;

    public APIService() {
        super("BargainBurgApiService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("API", "handling intent");
        Bundle extras = intent.getExtras();
        int apiCall = extras.getInt(API_CALL, -1);
        int id = 0;
        String query;
        switch (apiCall) {
            case GET_CATEGORIES:
                getCategories(extras);
                break;
            case GET_COMPANIES:
                getCompanies(extras);
                break;
            case GET_COMPANIES_FOR_CATEGORY:
                id = extras.getInt(EX.ID, 1);
                getCompaniesForCategory(id);
                break;
            case GET_COMPANY_COUPONS:
                id = extras.getInt(EX.ID, 1);
                getCompanyWithCoupons(id);
                break;
            case SEARCH:
                query = extras.getString(EX.QUERY);
                search(query);
            default:
                break;
        }
    }

    private void search(String query) {
        BaseResponse response;
        try {
            response = mBargainBurgApi.search(query);
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CategoryResponse();
            response.success = false;
            response.error = true;
        }
        Log.d("API", "posting response : success == " + response.success);
        BusProvider.getInstance().post(new SearchEvent((SearchResponse) response));
    }

    private void getCategories(Bundle data) {
        Log.d("API", "getting categories");
        BaseResponse response;
        try {
            response = mBargainBurgApi.getCategories();
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CategoryResponse();
            response.success = false;
            response.error = true;
        }
        Log.d("API", "posting response : success == " + response.success);
        BusProvider.getInstance().post(new CategoryEvent((CategoryResponse) response));
    }

    private void getCompanyWithCoupons(int id) {
        BaseResponse response;
        try {
            response = mBargainBurgApi.getCompanyWithCoupons(id);
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CompanyResponse();
            response.success = false;
            response.error = true;
        } BusProvider.getInstance().post(new CompanyEvent((CompanyResponse)response));
    }

    private void getCompaniesForCategory(int id) {
        BaseResponse response;
        try {
            response = mBargainBurgApi.getCompaniesForCategory(id);
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CompaniesResponse();
            response.success = false;
            response.error = true;
        } BusProvider.getInstance().post(new CompaniesEvent((CompaniesResponse)response));
    }

    private void getCompanies(Bundle data) {
        BaseResponse response;
        try {
            response = mBargainBurgApi.getCompanies();
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CompaniesResponse();
            response.success = false;
            response.error = true;
        }
        BusProvider.getInstance().post(new CompaniesEvent((CompaniesResponse)response));
    }

}
