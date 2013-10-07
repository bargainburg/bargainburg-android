package com.bargainburg.android.Otto;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/7/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public final class BusProvider {

    private static final BargainBurgBus BUS = new BargainBurgBus();

    public static BargainBurgBus getInstance() {
        return BUS;
    }

    private BusProvider() {
        //No instances.
    }
}
