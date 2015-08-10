package br.com.sumone.sumtwitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import br.com.sumone.sumtwitter.fragment.MapViewFragment;
import br.com.sumone.sumtwitter.fragment.TimelineFragment;


public class MainActivity extends BaseActivity {

    private List<Tweet> tweets = null;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ProgressDialog barLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        setActionBar();

        if (!isConnected()) {
            Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        }

        refreshTweets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Twitter.logOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Lança um popUp de carregamento com uma mensagem padrão de carregamento, carrega os tweets e constroi os fragmentos.
    *
    */
    public void refreshTweets() {
        barLoading = new ProgressDialog(this);
        barLoading.setMessage(getResources().getString(R.string.loading_tweets));
        barLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barLoading.setIndeterminate(true);
        barLoading.show();
        Twitter.getApiClient().getStatusesService().homeTimeline(50, null, null, null, false, false, false, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                tweets = result.data;
                viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
                tabLayout.setupWithViewPager(viewPager);
                barLoading.dismiss();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loaded_tweets), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void failure(TwitterException e) {
                tweets = null;
                viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
                tabLayout.setupWithViewPager(viewPager);
                barLoading.dismiss();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo == null) ? false : networkInfo.isConnected());
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TimelineFragment(tweets);
                case 1:
                    return new MapViewFragment(tweets);
                default:
                    return new TimelineFragment(tweets);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.timeline);
                case 1:
                    return getResources().getString(R.string.map);
                default:
                    return getResources().getString(R.string.timeline);
            }
        }
    }
}
