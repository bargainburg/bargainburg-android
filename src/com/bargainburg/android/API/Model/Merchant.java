package com.bargainburg.android.API.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Merchant {
    public int id;
    public String name;
    public String email;
    public String phone;
    public String hours;
    public String link;
    public String description;
    public boolean approved;
    @SerializedName("price_range")
    public int priceRange;
    @SerializedName("created_at")
    public String createDate;
    @SerializedName("updated_at")
    public String updatedDate;
    @SerializedName("category_id")
    public int categoryId;
    @SerializedName("user_id")
    public int userId;
    public ArrayList<Coupon> coupons;
}
