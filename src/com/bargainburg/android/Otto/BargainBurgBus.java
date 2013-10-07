package com.bargainburg.android.Otto;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public final class BargainBurgBus extends Bus {

    private final Handler mainThread = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }
}
