package uk.ac.acnh585city.searchmoviesapp;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CinemasToLocation extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public static final String TAG=CinemasToLocation.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinemas_to_location);
        setUpMapIfNeeded();

        mGoogleApiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mLocationRequest=LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10 * 1000).setFastestInterval(1000);

        setUpMapIfNeeded();
    }

   @Override
   protected void onResume(){
       super.onResume();
       setUpMapIfNeeded();
       mGoogleApiClient.connect();
   }

    @Override
    protected void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private void handleNewLocation(Location location){

        Log.d(TAG, location.toString());
        double currentLatitude=location.getLatitude();
        double currentLongitude=location.getLongitude();
        LatLng latLng=new LatLng(currentLatitude,currentLongitude);


        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.50483879999999,-0.11392420000004222)).title("Odeon BFI IMAX Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4068787,-0.031153700000004392)).title("Odeon Beckenham Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5388053,-0.1439129000000321)).title("Odeon Camden Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5143218,-0.1282619999999497)).title("Odeon Covent Garden Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4896547,0.015019899999970221)).title("Odeon Greenwich Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5586914,-0.12267559999997957)).title("Odeon Holloway Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.41162449999999,-0.2998556999999664)).title("Odeon Kingston Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.6283472,-0.0390912999999955)).title("Odeon Lee Valley Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.509709,-0.13054060000001755)).title("Odeon Leicester Square Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5138547,-0.1601177000000007)).title("Odeon Marble Arch Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5095138,-0.13126920000001974)).title("Odeon Panton Street Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4657367,-0.21368140000004132)).title("Odeon Putney Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4586477,-0.305565500000057)).title("Odeon Richmond Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4594968,-0.3045839999999771)).title("Odeon Richmond Studio Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5933613,-0.3045839999999771)).title("Odeon South Woodford Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4320904,-0.12805700000001252)).title("Odeon Streatham Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.509709,-0.13054060000001755)).title("Odeon Studios Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4953976,-0.04696349999994709)).title("Odeon Surrey Quays Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5424507,-0.17451059999996232)).title("Odeon Swiss Cottage Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5182195, -0.1324551000000156)).title("Odeon Tottenham Court Road Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.54515670000001, -0.4755440000000135)).title("Odeon Uxbridge Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5145789, -0.18874040000002879)).title("Odeon Whiteleys Cinema"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.4199242, -0.20514140000000225)).title("Odeon Wimbledon Cinema"));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location==null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }else{
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
                }catch (IntentSender.SendIntentException e){
                e.printStackTrace();
                 }
            } else {
                     Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
                    }
        }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        }

}

