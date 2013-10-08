package com.bargainburg.android.API.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseResponse {

    public boolean success;
    public boolean error;
    @SerializedName("error_description")
    public String errorDescription;
}
