package br.com.sumone.sumtwitter.application;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by hortoni on 05/08/15.
 */

public class SumTwitterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

         TwitterAuthConfig authConfig =
                 new TwitterAuthConfig("zSxGSMEYNegKQF4e3ADfWqwa4", "0F0E8fHfJRaIKeSQuTNDuFqXSoWYZbVPDkgCHIeTqcgx9UTDjP");

         Fabric.with(this, new Twitter(authConfig));
    }
}
