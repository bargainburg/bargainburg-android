package com.bargainburg.android.Otto.Events;

import com.bargainburg.android.API.Responses.CategoryResponse;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 6:50 PM
 */
public class CategoryEvent {

    public CategoryResponse response;

    public CategoryEvent(CategoryResponse response) {
        this.response = response;
    }
}
