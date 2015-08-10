package br.com.sumone.sumtwitter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class ProfileActivity extends BaseActivity {

    private ListView list;
    private TweetTimelineListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setActionBar();
        username = getIntent().getStringExtra("username");
        list = (ListView) findViewById(android.R.id.list);
        setUserTimeline(username);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                setUserTimeline(username);
            }
        });

    }

    public void setUserTimeline(String username) {
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(username)
                .build();
        adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        list.setAdapter(adapter);

        if (swipeLayout != null && swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }
}

