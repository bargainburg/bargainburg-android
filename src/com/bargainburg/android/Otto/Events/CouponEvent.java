package com.bargainburg.android.Otto.Events;

import com.bargainburg.android.API.Responses.CouponResponse;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/23/13
 * Time: 10:58 PM
 */
public class CouponEvent {

    public CouponResponse response;

    public CouponEvent(CouponResponse response) {
        this.response = response;
    }
}
