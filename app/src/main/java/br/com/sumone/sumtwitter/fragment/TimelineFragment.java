package br.com.sumone.sumtwitter.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;

import java.util.List;

import br.com.sumone.sumtwitter.MainActivity;
import br.com.sumone.sumtwitter.R;
import br.com.sumone.sumtwitter.util.Constants;
import br.com.sumone.sumtwitter.util.CustomTweetTimelineListAdapter;

/**
 * Created by hortoni on 05/08/15.
 */

public class TimelineFragment extends ListFragment implements Constants{

	private CustomTweetTimelineListAdapter adapter;

	private SwipeRefreshLayout swipeLayout;
	private List<Tweet> tweets;

	public TimelineFragment() {
	}

	public TimelineFragment(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = (LinearLayout) inflater.inflate(R.layout.fragment_timeline, container, false);


		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				((MainActivity) getActivity()).refreshTweets();
			}
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHomeTimeline(tweets);
	}


	private void setHomeTimeline(List<Tweet> tweets) {
		if (tweets != null) {
			final FixedTweetTimeline timeline = new FixedTweetTimeline.Builder()
					.setTweets(tweets)
					.build();
			adapter = new CustomTweetTimelineListAdapter(getActivity(), timeline);
			setListAdapter(adapter);
			if (swipeLayout != null && swipeLayout.isRefreshing()) {
				swipeLayout.setRefreshing(false);
			}
		}
	}
}