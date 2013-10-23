package com.bargainburg.android.Otto.Events;

import com.bargainburg.android.API.Responses.SearchResponse;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/23/13
 * Time: 4:35 PM
 */
public class SearchEvent {

    public SearchResponse response;

    public SearchEvent(SearchResponse response) {
        this.response = response;
    }
}
