package com.bargainburg.android.API;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.bargainburg.android.API.Responses.BaseResponse;
import com.bargainburg.android.API.Responses.CategoryResponse;
import com.bargainburg.android.API.Responses.CompanyResponse;
import com.bargainburg.android.Data.Datastore;
import com.bargainburg.android.Otto.BusProvider;
import com.bargainburg.android.Otto.Events.CategoryEvent;
import com.bargainburg.android.Otto.Events.CompanyEvent;
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
        switch (extras.getInt(API_CALL)) {
            case GET_CATEGORIES:
                getCategories(extras);
                break;
            case GET_COMPANIES:
                getCompanies(extras);
                break;
//            case GET_COUPONS:
//                getCoupons(extras);
//                break;
            default:
                break;
        }
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

    private void getCompanies(Bundle data) {
        BaseResponse response;
        try {
            response = mBargainBurgApi.getCompanies();
            response.success = true;
            response.error = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = new CompanyResponse();
            response.success = false;
            response.error = true;
        }
        BusProvider.getInstance().post(new CompanyEvent((CompanyResponse)response));
    }

}
