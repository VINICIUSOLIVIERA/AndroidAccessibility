package production.tcc.android.androidaccessibility.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHelper extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    public GoogleMap map;
    private LatLng locationCurrent;

    public void moveCamera(LatLng location, float zoom, boolean animate){
        zoom = zoom != 0 ? zoom : 15;
        if(animate)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        else
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    public LatLng userLocationCurrent(){
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        if (location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    public void addMarker(String title, String description, double lat, double lng, Object tag){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(title).snippet(description);
        this.map.addMarker(marker).setTag(tag);
    }

    public void checkPermissionAccessGps(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "NOT PERMISSION ACCESS GPS", Toast.LENGTH_LONG).show();
        }
        this.map.setMyLocationEnabled(true);
    }

    /***
     * implements - OnMapReadyCallback
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.map = googleMap;
//        LatLng jp = new LatLng(-7.115, -34.86306);
//        this.map.addMarker(new MarkerOptions().position(jp).title("João Pessoa"));
//        this.map.moveCamera(CameraUpdateFactory.newLatLng(jp));

        this.checkPermissionAccessGps();
        locationCurrent = this.userLocationCurrent();
        if(locationCurrent != null){
            this.moveCamera(locationCurrent, 15, true);
        }
    }
    /***
    * implements - LocationListener
    * */
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
