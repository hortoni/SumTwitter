package br.com.sumone.sumtwitter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.sumone.sumtwitter.ProfileActivity;
import br.com.sumone.sumtwitter.R;
import br.com.sumone.sumtwitter.util.Constants;

/**
 * Created by hortoni on 05/08/15.
 */

public class MapViewFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener, Constants{

	private GoogleMap map;
	private SupportMapFragment fragment;
	private static View view;
	private List<Tweet> tweets;

	public MapViewFragment() {
	}

	public MapViewFragment(List<Tweet> tweets) {
		this.tweets = tweets;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_map, container, false);
		} catch (InflateException e) {
		}

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}
		map = fragment.getMap();
		map.setOnInfoWindowClickListener(this);

		setUpMap(tweets);

	}

	private void setUpMap(List<Tweet> tweets) {
		if (tweets != null) {
			for (int i = 0; i < tweets.size(); i++) {
				if (tweets.get(i) != null) {
					if (tweets.get(i).place != null) {
						List<Address> addresses = getAddresFromLocation(tweets.get(i));
						addMarkerFromAdress(tweets.get(i), addresses);
					}
				}
			}
		}
	}

	private void addMarkerFromAdress(Tweet tweet, List<Address> addresses) {
		ImageLoader imageLoader = ImageLoader.getInstance();

		if (addresses.get(0) != null) {
			Address address = addresses.get(0);

			final MarkerOptions marker = new MarkerOptions();
			final String title = tweet.user.screenName;
			final String snippet = tweet.text;
			final double longitude = address.getLongitude();
			final double latitude = address.getLatitude();

			imageLoader.loadImage(tweet.user.profileImageUrl, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					marker.position(new LatLng(latitude, longitude));
					marker.title(title);
					marker.snippet(snippet);
					marker.icon(BitmapDescriptorFactory.fromBitmap(loadedImage));
					map.addMarker(marker);
				}
			});
		}
	}

	private List<Address> getAddresFromLocation(Tweet tweet) {
		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
		String location = tweet.place.fullName;
		List<Address> addresses = null;
		try {
            addresses = geocoder.getFromLocationName(location , 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return addresses;
	}


	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		intent.putExtra(KEY_USERNAME, marker.getTitle());
		startActivity(intent);
	}
}
