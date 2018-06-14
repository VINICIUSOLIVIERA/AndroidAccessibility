package production.tcc.android.androidaccessibility.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.Services.SeekService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class MapHelper extends Fragment implements OnMapReadyCallback, LocationListener {

    protected GoogleMap map;
    protected LatLng locationCurrent;

    // ZOOM
    public int ZOOM_WORLD = 1, // MIN ZOOM
            ZOOM_EARTH = 5,
            ZOOM_CITY = 10,
            ZOOM_STREET = 15,
            ZOOM_BUILDINGS = 20; // MAX ZOOM

    public void moveCamera(LatLng location, float zoom, boolean animate){
        zoom = zoom != 0 ? zoom : 15;
        if(animate)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        else
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    public LatLng userLocationCurrent(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "NOT PERMISSION ACCESS GPS", Toast.LENGTH_LONG).show();
        }
        this.map.setMyLocationEnabled(true);
    }

    /***
     * implements - OnMapReadyCallback
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        this.map = googleMap;
//        LatLng jp = new LatLng(-7.115, -34.86306);
//        this.map.addMarker(new MarkerOptions().position(jp).title("João Pessoa"));
//        this.map.moveCamera(CameraUpdateFactory.newLatLng(jp));

        this.checkPermissionAccessGps();
        locationCurrent = this.userLocationCurrent();
        if(locationCurrent != null){
            this.moveCamera(locationCurrent, 15, true);
        }
        this.loadMarker();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
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

    public  void loadMarker(){
        final Util util = new Util(getContext());
        Long user_id = Long.parseLong(util.getSharendPreferences("id"));
        util.showLoading("Aguarde!", "Carregando os pontos...");

        Retrofit retrofit = new RetrofitConfig().init();
        SeekService service = retrofit.create(SeekService.class);
        Call<List<Seek>> call = service.allByUser(user_id);

        call.enqueue(new Callback<List<Seek>>() {
            @Override
            public void onResponse(Call<List<Seek>> call, Response<List<Seek>> response) {
                util.hideLoading();
                if(response != null) {
                    List<Seek> seeks = response.body();
                    for(Seek s : seeks){
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getTopic()).snippet(s.getDescription());
                        map.addMarker(marker).setTag(s.getId());
                    }
                    if(seeks.size() == 0){
                        util.showAlert("Ops!!!", "Você não tem nenhuma solicitação cadastrada.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Seek>> call, Throwable t) {
                util.hideLoading();
                util.showAlert("Ops!!!", "Algo de errado aconteceu.");
                // Code....
            }
        });
    }

}
