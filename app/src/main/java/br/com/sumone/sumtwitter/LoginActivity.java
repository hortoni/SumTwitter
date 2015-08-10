package br.com.sumone.sumtwitter;

import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import br.com.sumone.sumtwitter.util.Constants;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends BaseActivity implements Constants{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "zSxGSMEYNegKQF4e3ADfWqwa4";
    private static final String TWITTER_SECRET = "0F0E8fHfJRaIKeSQuTNDuFqXSoWYZbVPDkgCHIeTqcgx9UTDjP";

    private TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        if (session != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
