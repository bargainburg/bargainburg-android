package com.bargainburg.android.API.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Coupon {
    int id;
    @SerializedName("begin_date")
    String beginDate;
    @SerializedName("end_date")
    String endDate;
    String description;
    boolean hidden;
    String name;
    @SerializedName("created_at")
    String createdDate;
    @SerializedName("updated_at")
    String updatedDate;
    @SerializedName("category_id")
    int categoryId;
    @SerializedName("merchant_id")
    int merchantId;
    @SerializedName("image_file_name")
    String image;
    @SerializedName("image_content_type")
    String imageType;
    @SerializedName("image_file_size")
    String imageFileSize;
    @SerializedName("image_updated_at")
    String imageUpdatedAt;
}
