package com.bargainburg.android.API.Responses;

import com.bargainburg.android.API.Model.Merchant;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:11 AM
 */
public class CompanyResponse extends BaseResponse {
    public List<Merchant> companies;
    public Merchant company;
}
