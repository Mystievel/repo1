package com.tofersapps.ThePurplePot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

// // Note: if needed - go to the google dev web page and set restrictions on the Places API (go to places API --> Credentials (3rd tab on right), the scroll to bottom. This seemed to fix getting the nearest x hospital, hotel, etc.

public class FindInStoreActivity extends MainActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
	// Defines
	double INCREMENTAL = 0.1;

	// Globals
	GoogleMap mMap;
	private GoogleApiClient client;
	private LocationRequest locationRequest;
	public static final int REQUEST_LOCATION_CODE = 99;
	int PROXIMITY_RADIUS = 10000;
	Object dataTransfer[] = new Object[2];
	GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
	AutocompleteSupportFragment placeAutoComplete;

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

		//if (!Places.isInitialized()) {
			Places.initialize(getApplicationContext(), "AIzaSyDPSfWIKGi6Swv00Y-JTKW6-NTSnkeatCQ");
		//}

		// Useful Link: https://developers.google.com/places/android-sdk/autocomplete#add_an_autocomplete_widget.
		placeAutoComplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoCompleteSearch);
		// Specify the types of place data to return.
		placeAutoComplete.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));
		// Auto-complete listener.
		placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
			@Override
			public void onPlaceSelected(Place place) {
				double latitude = place.getLatLng().latitude;
				double longitude = place.getLatLng().longitude;
				String placeName = place.getName();
				//Log.d("MapsSelPl", "Name: " + placeName + ". Lat: " + latitude + ". Lng: " + longitude );//+ ". Types: " + place.getTypes() + ". Plus Code: " + place.getPlusCode());
				mMap.clear();
				moveMapToLocationWithMarker(mMap, latitude, longitude, placeName);
			}

			@Override
			public void onError(Status status) {
				Toast.makeText(FindInStoreActivity.this, "Error getting map data.", Toast.LENGTH_SHORT).show();
				//Log.d("MapsSelPl", "An error occurred: " + status);
			}
		});

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


	@Override
	public void onMapReady(GoogleMap googleMap) {
		// Save instance to the map object.
		mMap = googleMap;

		// Get the user's most accurate, last known location.
		Location location = getDevicesLastKnownLocation(googleMap);

		// Show nearby dispensaries on the map.
		String url = getUrl(location.getLatitude(), location.getLongitude(), "store");
		dataTransfer[0] = mMap;
		dataTransfer[1] = url;
		getNearbyPlacesData.execute(dataTransfer);
	}


	public void moveMapToLocationWithMarker(GoogleMap googleMap, double latitude, double longitude, String tag) {
		LatLng userLocation = new LatLng(latitude, longitude);
		LatLngBounds mapBounds = new LatLngBounds(new LatLng(latitude - INCREMENTAL, longitude - INCREMENTAL), new LatLng(latitude + INCREMENTAL, longitude + INCREMENTAL));
		googleMap.addMarker(new MarkerOptions().position(userLocation).title(tag));
		googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, 0));
	}


	public void handleNullLocation(GoogleMap googleMap, Location location) {
		if (location != null) {
			//Log.d("nulloc", "non-null location. valid.");
			// Get the users current location.
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			moveMapToLocationWithMarker(googleMap, latitude, longitude,"you");
		} else {
			//Log.d("nulloc", "null location. invalid.");
			moveMapToLocationWithMarker(googleMap,37.399976,-122.058106, "Google Campus");
		}
	}


	public Location getDevicesLastKnownLocation(GoogleMap googleMap) {
		LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
				// Store that we've already asked the user for access and access was granted.
				googleMap.setMyLocationEnabled(true);
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				Location location = mLocationManager.getLastKnownLocation(provider);
				//Log.d("LastDeviceLocation", String.format("Provider: %s. Location: %s", provider, location));

				if (location == null) {
					continue; // Advance to next loop iteration.
				}
				if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
					//Log.d("LastDeviceLocation", String.format("Best last known location so far: %s", location));
					bestLocation = location;
				}
			} else {
				// Access is not granted, ask for permission.
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			}
		}
		//Log.d("LastDeviceLocation", String.format("Best location: %s", bestLocation));

		handleNullLocation(googleMap, bestLocation);
		return bestLocation;
	}


	private String getUrl(double latitude , double longitude , String nearbyPlace) {
		StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
		googlePlaceUrl.append("location=" + latitude + "," + longitude);
		googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
		googlePlaceUrl.append("&type=" + nearbyPlace);
		googlePlaceUrl.append("&sensor=true");
		googlePlaceUrl.append("&key=" + "AIzaSyDPSfWIKGi6Swv00Y-JTKW6-NTSnkeatCQ");
		//Log.d("MapsActivity", "url = " + googlePlaceUrl.toString());
		return googlePlaceUrl.toString();
	}


	@Override
	public void onConnected(@Nullable Bundle bundle) {
		locationRequest = new LocationRequest();
		locationRequest.setInterval(1000);
		locationRequest.setFastestInterval(1000);
		locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
			LocationServices.getFusedLocationProviderClient(this);
		}
	}


	public boolean checkLocationPermission() {
		if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED ) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
				ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
			}
			else {
				ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
			}
			return false;

		} else {
			return true;
		}
	}


	@Override
	public void onConnectionSuspended(int i) {
	}


	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
	}


	@Override
	public void onLocationChanged(Location location) {
	}
}
