package com.bargainburg.android.API;

import android.util.Log;
import com.bargainburg.android.API.Model.Category;
import com.bargainburg.android.API.Model.Coupon;
import com.bargainburg.android.API.Model.Merchant;
import com.bargainburg.android.API.Model.Search;
import com.bargainburg.android.API.Responses.*;
import com.bargainburg.android.Data.Datastore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 6:24 PM
 */
public class BargainBurgApi {

    public static final String BACKEND_URL_CATEGORIES = "http://api.dev.bargainburg.co/v1/categories/";
    public static final String BACKEND_URL_COMPANIES = "http://api.dev.bargainburg.co/v1/merchants/";
    public static final String SEARCH_URL = "http://api.bargainburg.co/v1/search?query=";
    public static final String MERCHANTS = "merchants";
    public static final String EXPAND_COUPONS = "?expand_coupons=1";
    public static final String COUPON_URL = "http://api.dev.bargainburg.co/v1/coupons/";

    private Datastore mDataStore;
    private OkHttpClient mOkHttpClient;
    private static Gson mGson = new Gson();

    @Inject
    public BargainBurgApi(OkHttpClient client) {
        mOkHttpClient = client;
    }

    private <T> T get(String url, Type type) throws IOException {
        Log.d("API", url);
        URL getUrl = new URL(url);
        HttpURLConnection connection = mOkHttpClient.open(getUrl);
        InputStream in = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                in = connection.getErrorStream();
            } else if (connection.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new Exception("internal server error");
            } else if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Unexpected HTTP response: "
                        + connection.getResponseCode() + " " + connection.getResponseMessage());
            } else {
                in = connection.getInputStream();
            }
            InputStreamReader reader = new InputStreamReader(in);
            return mGson.fromJson(reader, type);
        } catch (Exception e) {
            Log.e("api_get", url, e);
            return null;
        } finally {
            if (in != null) in.close();
        }
    }

    public SearchResponse search(String query) throws Exception {
        String url = SEARCH_URL + URLEncoder.encode(query, "UTF-8");
        Type type = new TypeToken<List<Search>>() {
        }.getType();
        SearchResponse response = new SearchResponse();
        response.results = get(url, type);
        return response;
    }

    public CategoryResponse getCategories() throws Exception {
        String url = BACKEND_URL_CATEGORIES;
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        Log.d("API", url);
        CategoryResponse response = new CategoryResponse();
        response.categories = get(url, type);
        return response;
    }

    public CompaniesResponse getCompaniesForCategory(int id) throws Exception {
        String url = BACKEND_URL_CATEGORIES + id + "/" + MERCHANTS;
        Log.d("API", url);
        Type type = new TypeToken<List<Merchant>>() {
        }.getType();
        CompaniesResponse response = new CompaniesResponse();
        response.companies = get(url, type);
        return response;
    }

    public CompaniesResponse getCompanies() throws Exception {
        String url = BACKEND_URL_COMPANIES;
        Type type = new TypeToken<List<Merchant>>() {
        }.getType();
        CompaniesResponse response = new CompaniesResponse();
        response.companies = get(url, type);
        return response;
    }

    public CompanyResponse getCompanyWithCoupons(int id) throws Exception {
        String url = BACKEND_URL_COMPANIES + "" + id + EXPAND_COUPONS;
        CompanyResponse response = new CompanyResponse();
        response.company = get(url, Merchant.class);
        return response;
    }

    public CouponResponse getCoupon(int id) throws Exception {
        String url = COUPON_URL + "" + id;
        CouponResponse response = new CouponResponse();
        response.coupon = get(url, Coupon.class);
        return response;
    }
}
