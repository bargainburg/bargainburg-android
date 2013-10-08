package com.bargainburg.android.API.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    public int id;
    public String name;
    @SerializedName("created_at")
    public String createDate;
    @SerializedName("updated_at")
    public String updatedDate;
}
