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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OriginActivity extends FragmentActivity implements OnMapReadyCallback {


    private Location location;
    private String key;
    private GoogleMap migoogleMap;
    private EditText addressEditText;
    private Button confirmDestination;
    private Button ubicacionActual;
    private Button searchDestination;
    private LinearLayout loading;
    private SupportMapFragment mapFragment;
    private OriginPresenterInterface presenter;
    private Button confirmOrigin;
    private TextView addressTextView;
    private EditText destinationEditText;
    private Button requestRide;

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
        loading = (LinearLayout) findViewById(R.id.loading);
        confirmOrigin = (Button) findViewById(R.id.confirmOrigin);
        addressTextView = (TextView) findViewById(R.id.addressTextView);

        presenter = new OriginPresenter(this);

        presenter.requestLocationUpdates();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        migoogleMap = googleMap;
        migoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void addMarker(LatLng location) {
        migoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        migoogleMap.addMarker(new MarkerOptions().position(location));
    }

    public void showAddress(String address) {
        addressTextView.setText(address);
    }

    public void hideConfirmOrigin() {
        confirmOrigin.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
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

    public void searchDestination(View view) {
        presenter.searchDestination(getAddress(), this, key);
    }

    public void requestRide(View view) {
        presenter.requestRide();
    }


    public void hideSearchDestination() {
        searchDestination.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        Snackbar.make(addressTextView,message,Snackbar.LENGTH_LONG).show();
    }
}
