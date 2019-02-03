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
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


// todo: High Priority - protect API Key


public class FindInStoreActivity extends MainActivity implements OnMapReadyCallback {
	// Globals


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

		// todo: search not working, need to encompass within fragment???
		// Useful Link: https://developers.google.com/places/android-sdk/autocomplete#add_an_autocomplete_widget
		AutocompleteSupportFragment placeAutoComplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoCompleteSearch);
		// Specify the types of place data to return.
		//placeAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME));
		// Auto-complete listener.
		placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
			@Override
			public void onPlaceSelected(Place place) {
				// todo: get information about the selected place
				Log.d("MapsSelPl", "Place selected: " + place.getName() + ", " + place.getId());
			}

			@Override
			public void onError(Status status) {
				// todo: handle the error
				Log.d("MapsSelPl", "An error occurred: " + status);
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
		Location location = getDevicesLastKnownLocation(googleMap);
		handleNullLocation(googleMap, location);
	}


	public void moveMapToLocationWithMarker(GoogleMap googleMap, double latitude, double longitude, String tag) {
		LatLng userLocation = new LatLng(latitude, longitude);
		googleMap.addMarker(new MarkerOptions().position(userLocation).title(tag));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
	}


	public void handleNullLocation(GoogleMap googleMap, Location location) {
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
				Log.d("LastDeviceLocation", String.format("Provider: %s. Location: %s", provider, location));

				if (location == null) {
					continue; // Advance to next loop iteration.
				}
				if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
					Log.d("LastDeviceLocation", String.format("Best last known location: %s", location));
					bestLocation = location;
				}
			} else {
				// Access is not granted, ask for permission.
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			}
		}
		return bestLocation;
	}
}
