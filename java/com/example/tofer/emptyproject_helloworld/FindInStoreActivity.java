package com.example.tofer.emptyproject_helloworld;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class FindInStoreActivity extends MainActivity implements OnMapReadyCallback {
	// Globals
	GoogleMap myMap;

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							FindStrainsActivity: onCreate
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_in_store_activity);

		// Content view for the Map Fragment
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
		mapFragment.getMapAsync(this);

		//******************************************************************************************
		// Menu Bar Object - Button Clicked
		// todo: Medium Priority - summarize the code block below into a routine **********************************************************************
		//******************************************************************************************
		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
		btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindInStoreActivity.this, FindStrainsActivity.class));
			}
		}); //**************************************************************************************
		//******************************************************************************************
		// FindStore Page Clicked
		//******************************************************************************************
		Button btnFindStore = findViewById(R.id.btnFindStorePage);
		btnFindStore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindInStoreActivity.this, FindInStoreActivity.class));
			}
		}); //**************************************************************************************
		//******************************************************************************************
		// Support Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindInStoreActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************
		//******************************************************************************************
		// My Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindInStoreActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
	}

	// todo: https://stackoverflow.com/questions/45107806/autocomplete-search-bar-in-google-maps


	// todo: https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
	@Override
	public void onMapReady(GoogleMap googleMap) {
		// Get access from the user to use their location if we haven't already.
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			// Store that we've already asked the user for access and access was granted.
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		} else {
			// Access is not granted, ask for permission.
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
		}

		// Get the user's current location
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// todo: High Priority - Bug fix with https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
		Location location = getDevicesLastKnownLocation();

		Log.d("nulloc", "" + location);
		if (location != null) {
			Log.d("nulloc", "non-null location. valid.");
			// Get the users current location.
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			moveMapToLocationWithMarker(googleMap, latitude, longitude,"Store x");
		} else {
			Log.d("nulloc", "null location. invalid.");
			moveMapToLocationWithMarker(googleMap,37.399976,-122.058106, "Google Campus");
		}
	}

	public void moveMapToLocationWithMarker(GoogleMap googleMap, double latitude, double longitude, String tag) {
		LatLng userLocation = new LatLng(latitude, longitude);
		googleMap.addMarker(new MarkerOptions().position(userLocation).title(tag));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
	}

	private Location getDevicesLastKnownLocation() {
		LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mLocationManager.getLastKnownLocation(provider);
			Log.d("Last Location", String.format("last known location, provider: %s, location: %s", provider, l));

			if (l == null) {
				continue;
			}
			if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
				Log.d("Last Location", String.format("found best last known location: %s", l));
				bestLocation = l;
			}
		}
		if (bestLocation == null) {
			return null;
		}
		return bestLocation;
	}


	// todo: High Priority - Bug fix with https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
/*	public void onMapReady(GoogleMap map) {
		mMap = map;

		// Do other setup activities here too, as described elsewhere in this tutorial.

		// Turn on the My Location layer and the related control on the map.
		updateLocationUI();

		// Get the current location of the device and set the position of the map.
		getDeviceLocation();
	}

	private void getLocationPermission() {
		*//*
		 * Request location permission, so that we can get the location of the
		 * device. The result of the permission request is handled by a callback,
		 * onRequestPermissionsResult.
		 *//*
		if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
				android.Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED) {
			mLocationPermissionGranted = true;
		} else {
			ActivityCompat.requestPermissions(this,
					new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
					PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		mLocationPermissionGranted = false;
		switch (requestCode) {
			case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					mLocationPermissionGranted = true;
				}
			}
		}
		updateLocationUI();
	}

	private void getDeviceLocation() {
		*//*
		 * Get the best and most recent location of the device, which may be null in rare
		 * cases when a location is not available.
		 *//*
		try {
			if (mLocationPermissionGranted) {
				Task locationResult = mFusedLocationProviderClient.getLastLocation();
				locationResult.addOnCompleteListener(this, new OnCompleteListener() {
					@Override
					public void onComplete(@NonNull Task task) {
						if (task.isSuccessful()) {
							// Set the map's camera position to the current location of the device.
							mLastKnownLocation = task.getResult();
							mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
									new LatLng(mLastKnownLocation.getLatitude(),
											mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
						} else {
							Log.d(TAG, "Current location is null. Using defaults.");
							Log.e(TAG, "Exception: %s", task.getException());
							mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
							mMap.getUiSettings().setMyLocationButtonEnabled(false);
						}
					}
				});
			}
		} catch(SecurityException e)  {
			Log.e("Exception: %s", e.getMessage());
		}
	}

	private void showCurrentPlace() {
		if (mMap == null) {
			return;
		}

		if (mLocationPermissionGranted) {
			// Get the likely places - that is, the businesses and other points of interest that
			// are the best match for the device's current location.
			@SuppressWarnings("MissingPermission") final
			Task<PlaceLikelihoodBufferResponse> placeResult =
					mPlaceDetectionClient.getCurrentPlace(null);
			placeResult.addOnCompleteListener
					(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
						@Override
						public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
							if (task.isSuccessful() && task.getResult() != null) {
								PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

								// Set the count, handling cases where less than 5 entries are returned.
								int count;
								if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
									count = likelyPlaces.getCount();
								} else {
									count = M_MAX_ENTRIES;
								}

								int i = 0;
								mLikelyPlaceNames = new String[count];
								mLikelyPlaceAddresses = new String[count];
								mLikelyPlaceAttributions = new String[count];
								mLikelyPlaceLatLngs = new LatLng[count];

								for (PlaceLikelihood placeLikelihood : likelyPlaces) {
									// Build a list of likely places to show the user.
									mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
									mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
											.getAddress();
									mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
											.getAttributions();
									mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

									i++;
									if (i > (count - 1)) {
										break;
									}
								}

								// Release the place likelihood buffer, to avoid memory leaks.
								likelyPlaces.release();

								// Show a dialog offering the user the list of likely places, and add a
								// marker at the selected place.
								openPlacesDialog();

							} else {
								Log.e(TAG, "Exception: %s", task.getException());
							}
						}
					});
		} else {
			// The user has not granted permission.
			Log.i(TAG, "The user did not grant location permission.");

			// Add a default marker, because the user hasn't selected a place.
			mMap.addMarker(new MarkerOptions()
					.title(getString(R.string.default_info_title))
					.position(mDefaultLocation)
					.snippet(getString(R.string.default_info_snippet)));

			// Prompt the user for permission.
			getLocationPermission();
		}
	}

	private void openPlacesDialog() {
		// Ask the user to choose the place where they are now.
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// The "which" argument contains the position of the selected item.
				LatLng markerLatLng = mLikelyPlaceLatLngs[which];
				String markerSnippet = mLikelyPlaceAddresses[which];
				if (mLikelyPlaceAttributions[which] != null) {
					markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
				}

				// Add a marker for the selected place, with an info window
				// showing information about that place.
				mMap.addMarker(new MarkerOptions()
						.title(mLikelyPlaceNames[which])
						.position(markerLatLng)
						.snippet(markerSnippet));

				// Position the map's camera at the location of the marker.
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
						DEFAULT_ZOOM));
			}
		};

		// Display the dialog.
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(R.string.pick_place)
				.setItems(mLikelyPlaceNames, listener)
				.show();
	}*/
}
