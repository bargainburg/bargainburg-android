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
    public int id;
    @SerializedName("begin_date")
    public String beginDate;
    @SerializedName("end_date")
    public String endDate;
    public String description;
    public boolean hidden;
    public String name;
    @SerializedName("created_at")
    public String createdDate;
    @SerializedName("updated_at")
    public String updatedDate;
    @SerializedName("category_id")
    public int categoryId;
    @SerializedName("merchant_id")
    public int merchantId;
    @SerializedName("image_file_name")
    public String image;
    @SerializedName("image_content_type")
    public String imageType;
    @SerializedName("image_file_size")
    public String imageFileSize;
    @SerializedName("image_updated_at")
    public String imageUpdatedAt;
}
