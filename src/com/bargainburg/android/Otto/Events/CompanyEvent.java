package com.bargainburg.android.Otto.Events;

import com.bargainburg.android.API.Responses.CompanyResponse;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/8/13
 * Time: 10:13 AM
 */
public class CompanyEvent {
    public CompanyResponse response;

    public CompanyEvent(CompanyResponse response) {
        this.response = response;
    }
}
