package com.example.toshiba.origin;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toshiba.passenger.R;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class OriginActivity extends FragmentActivity implements OnMapReadyCallback {



    private String key;
    private GoogleMap migoogleMap;
    private Button searchDestination;
    private LinearLayout loading;
    private SupportMapFragment mapFragment;
    private OriginPresenterInterface presenter;
    private Button confirmDestination;
    private TextView addressTextView;
    private EditText destinationEditText;
    private Button requestRide;
    private Button searchOriginStops;
    private Button confirmOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);

        Intent intent = getIntent();
        key = intent.getStringExtra("Uid");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        destinationEditText = (EditText) findViewById(R.id.destinationEditText);
        requestRide = (Button) findViewById(R.id.requestRide);
        searchDestination = (Button) findViewById(R.id.searchDestination);
        searchOriginStops = (Button) findViewById(R.id.searchOriginStops);
        confirmOrigin = (Button) findViewById(R.id.confirmOrigin);
        loading = (LinearLayout) findViewById(R.id.loading);
        confirmDestination = (Button) findViewById(R.id.confirmDest);
        addressTextView = (TextView) findViewById(R.id.addressTextView);

        presenter = new OriginPresenter(this);

        presenter.requestLocationUpdates();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        migoogleMap = googleMap;
        migoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void moveCamera(LatLng location) {
        migoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
    }

    public void searchDestination(View View) {
        presenter.searchDestination(destinationEditText.getText().toString(), key);
    }

    public void confirmDestination(View view) {
        migoogleMap.setOnMarkerDragListener(null);
        presenter.searchForDestinationStops();
    }

    public void addPassengerMarker(Location location) {
        migoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
    }

    public void addStopMarker(String key, GeoLocation location) {
        migoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .position(new LatLng(location.latitude, location.longitude)).title(key).alpha(0.5f));
    }

    public void addOriginStopMarker(GeoLocation location, String key) {
        migoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(new LatLng(location.latitude, location.longitude)).title(key).alpha(0.5f));
    }


    public void setOnClickListener() {
        migoogleMap.setOnMarkerClickListener(onMarkerClickListener);
    }

    private GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.setAlpha(1f);
            presenter.findDestinationSectors(marker);
            return true;
        }
    };

    public void getOriginLocation(View view) {
        presenter.getOriginLocation();
    }

    public void showAddress(String address) {
        addressTextView.setText(address);
    }

    public void confirmOrigin(View view) {
        migoogleMap.setOnMarkerDragListener(null);
        presenter.findOriginStops();
        hideConfirmOrigin();
        showRequestRide();
    }


    public void showSearchDestination() {
        searchDestination.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    public String getAddress() {
        return destinationEditText.getText().toString();
    }

    public void showRequestRide() {
        requestRide.setVisibility(View.VISIBLE);
    }

    public void hideSearchDestination() {
        searchDestination.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        Snackbar.make(addressTextView, message, Snackbar.LENGTH_LONG).show();
    }

    public void showConfirmDestination() {
        confirmDestination.setVisibility(View.VISIBLE);
    }

    public void hideConfirmDestination() {
        confirmDestination.setVisibility(View.GONE);
    }

    public void showDestinationEditText() {
        destinationEditText.setVisibility(View.VISIBLE);
    }

    public void showSearchOriginStops() {
        searchOriginStops.setVisibility(View.VISIBLE);
    }

    public void showConfirmOrigin() {
        confirmOrigin.setVisibility(View.VISIBLE);
    }

    public void hideSearchOriginStops() {
        searchOriginStops.setVisibility(View.GONE);
    }

    public void requestRide(View view){
        presenter.requestRide();
    }

    public void hideConfirmOrigin() {
        confirmOrigin.setVisibility(View.GONE);
    }

    public void setOriginOnClickListener() {
        migoogleMap.setOnMarkerClickListener(onOriginMarkerClickListener);
    }

    private GoogleMap.OnMarkerClickListener onOriginMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.setAlpha(1f);
            presenter.selectedOriginStop(marker.getTitle());
            return true;
        }
    };

    public void setOnDestinationMarkerDragListener() {
        migoogleMap.setOnMarkerDragListener(destinationMarkerDragListener);
    }

    private GoogleMap.OnMarkerDragListener destinationMarkerDragListener = new GoogleMap.OnMarkerDragListener() {
        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {
            migoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            presenter.startDestinationAddressService(marker.getPosition());
        }
    };

    public void setOnOriginMarkerDragListener() {
        migoogleMap.setOnMarkerDragListener(originMarkerDragListener);
    }

    private GoogleMap.OnMarkerDragListener originMarkerDragListener = new GoogleMap.OnMarkerDragListener() {
        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            presenter.startOriginAddressService(marker.getPosition());
        }
    };


}
