package br.com.sumone.sumtwitter.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import br.com.sumone.sumtwitter.ProfileActivity;

/**
 * Created by hortoni on 10/08/15.
 */
public class CustomTweetTimelineListAdapter extends TweetTimelineListAdapter implements Constants {

    public CustomTweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        //disable subviews to avoid links are clickable
        if(view instanceof ViewGroup){
            disableViewAndSubViews((ViewGroup) view);
        }

        //enable root view and attach custom listener
        view.setEnabled(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(KEY_USERNAME, ((Tweet)getItem(position)).user.screenName);
                context.startActivity(intent);

            }
        });
        return view;
    }

    //helper method to disable subviews
    private void disableViewAndSubViews(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableViewAndSubViews((ViewGroup) child);
            } else {
                child.setEnabled(false);
                child.setClickable(false);
                child.setLongClickable(false);
            }
        }
    }

}
