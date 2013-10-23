package com.bargainburg.android.Otto.Events;

import com.bargainburg.android.API.Responses.CompaniesResponse;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:13 AM
 */
public class CompaniesEvent {
    public CompaniesResponse response;

    public CompaniesEvent(CompaniesResponse response) {
        this.response = response;
    }
}
