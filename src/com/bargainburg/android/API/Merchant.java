package com.bargainburg.android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Merchant {
    int id;
    String name;
    String email;
    String phone;
    String hours;
    String link;
    String description;
    boolean approved;
    @SerializedName("price_range")
    int priceRange;
    @SerializedName("created_at")
    String createDate;
    @SerializedName("updated_at")
    String updatedDate;
    @SerializedName("category_id")
    int categoryId;
    @SerializedName("user_id")
    int userId;
}
