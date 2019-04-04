package com.tofersapps.ThePurplePot;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
	private String googlePlacesData;
	private GoogleMap mMap;
	String url;

	@Override
	protected String doInBackground(Object... objects) {
		mMap = (GoogleMap)objects[0];
		url = (String)objects[1];

		DownloadURL downloadURL = new DownloadURL();
		try {
			googlePlacesData = downloadURL.readUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return googlePlacesData;
	}


	@Override
	protected void onPostExecute(String s) {
		List<HashMap<String, String>> nearbyPlaceList;
		DataParser parser = new DataParser();
		nearbyPlaceList = parser.parse(s);
		Log.d("onPostExecute","called onPostExecute()");
		showNearbyPlaces(nearbyPlaceList);
	}


	// establishment, store, point_of_interest = 3 key terms used.
	// todo: Medium Priority: Perhaps switch to apple maps, google maps isn't powerful enough.
	// todo: Medium Priority: Determine below & can we somehow search for all 3 terms at once or do the 3 searches and then compare the result, make sure all 3 are the same place.
	// todo: Medium Priority: Maybe need to lookup: "How to perform a GENERAL search" using googlemaps'
	private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
		for(int i = 0; i < nearbyPlaceList.size(); i++) {
			MarkerOptions markerOptions = new MarkerOptions();
			HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

			String placeName = googlePlace.get("place_name");
			double lat = Double.parseDouble(googlePlace.get("lat"));
			double lng = Double.parseDouble(googlePlace.get("lng"));
			Log.d("showNearbyPlaces","name: " + placeName);

			LatLng latLng = new LatLng(lat, lng);
			markerOptions.position(latLng);
			markerOptions.title(placeName);
			markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

			mMap.addMarker(markerOptions);
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
		}
	}
}
