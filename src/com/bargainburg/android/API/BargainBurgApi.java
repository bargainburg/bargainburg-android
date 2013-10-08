package com.bargainburg.android.API;

import android.util.Log;
import com.bargainburg.android.API.Model.Category;
import com.bargainburg.android.API.Responses.CategoryResponse;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class BargainBurgApi {

    public static final String BACKEND_URL_CATEGORIES = "http://api.bargainburg.co/v1/categories/";

    private Datastore mDataStore;
    private OkHttpClient mOkHttpClient;
    private static Gson mGson = new Gson();

    @Inject
    public BargainBurgApi(OkHttpClient client) {
        mOkHttpClient = client;
    }

    private <T> T get(String url, Type type) throws IOException {
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

    public CategoryResponse getCategories() throws Exception {
        String url = BACKEND_URL_CATEGORIES;
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        Log.d("API", url);
        CategoryResponse response = new CategoryResponse();
        response.categories = get(url, type);
        return response;
    }
}
