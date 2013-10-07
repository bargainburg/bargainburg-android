package com.bargainburg.android;

import com.bargainburg.android.Data.EncryptedPreferencesProvider;
import com.bargainburg.android.Data.EncryptedSharedPreferences;
import com.google.inject.AbstractModule;

/**
 * Generated from archetype
 */
public class RoboGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EncryptedSharedPreferences.class).toProvider(EncryptedPreferencesProvider.class);
    }
}
