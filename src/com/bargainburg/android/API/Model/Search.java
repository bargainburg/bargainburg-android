package com.bargainburg.android.API.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/23/13
 * Time: 12:58 PM
 */
public class Search {

    public int id;
    public String name;
    public String type;
    public String email;
    public String phone;
    public String hours;
    @SerializedName("price_range")
    public int priceRange;

}
