package com.bargainburg.android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    int id;
    String name;
    @SerializedName("created_at")
    String createDate;
    @SerializedName("updated_at")
    String updatedDate;
}
