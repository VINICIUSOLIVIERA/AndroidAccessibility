package production.tcc.android.androidaccessibility.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.Models.User;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.SeekService;
import production.tcc.android.androidaccessibility.Util.DataBaseUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        LocationListener{

    private Util util = new Util(this);
    private GoogleMap map;
    private MapView mapView;
    private LatLng locationCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCreateSeek();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Map
        mapView = findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync((OnMapReadyCallback) this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {

        } else if (id == R.id.nav_all) {

        } else if (id == R.id.nav_trending) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_data) {
            DataBaseUtil dbutil = new DataBaseUtil(MainActivity.this);
            User user = dbutil.getUser();
            String message = "USER ID: "+user.getId()+"\nUSER NAME: "+user.getName()+"\nUSER PASSWORD: "+user.getPassword()+"\nDATA NASC.: "+user.getDate_birth()+"\nGENDER: "+(user.getGender() == 1 ? "Masculino" : "Feminino")+"\nDEFICIENCY: "+user.getDeficiency()+"\nCEP: "+user.getCep()+"\nADDRESS: "+user.getAddress()+"\nEMAIL: "+user.getEmail();
            util.showAlert("Dados do banco", message);
        } else if (id == R.id.nav_sharend) {
            String message = "ID: "+util.getSharendPreferences("id")+"\nUSER NAME: "+util.getSharendPreferences("user")+"\nPASSWORD: "+ util.getSharendPreferences("password")+"\nTOKEN: "+util.getSharendPreferences("token");
            util.showAlert("Dados do sharend preferences", message);
        }else if (id == R.id.nav_exit) {
            util.showLoading("Saindo", "Aguarde...");
            try{
                util.destroySharendPreferences("id");
                util.destroySharendPreferences("user");
                util.destroySharendPreferences("password");
                util.destroySharendPreferences("token");
                util.destroySharendPreferences("connected");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){
                util.showAlert("Error", e.getMessage());
            }finally {
                util.hideLoading();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        map = googleMap;
        LatLng jp = new LatLng(-7.115, -34.86306);
        map.addMarker(new MarkerOptions().position(jp).title("João Pessoa"));
        map.moveCamera(CameraUpdateFactory.newLatLng(jp));

        this.checkPermissionAccessGps();
        locationCurrent = this.userLocationCurrent();
        if(locationCurrent != null){
            this.moveCamera(locationCurrent, 15, true);
        }
    }

    public LatLng userLocationCurrent(){
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        if (location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    public void checkPermissionAccessGps(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "NOT PERMISSION ACCESS GPS", Toast.LENGTH_LONG).show();
        }
        map.setMyLocationEnabled(true);
    }

    public void moveCamera(LatLng location, float zoom, boolean animate){
        zoom = zoom != 0 ? zoom : 15;
        if(animate)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        else
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

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

    public void changeCreateSeek(){
        LatLng location = this.userLocationCurrent();
        Intent intent = new Intent(this, CreateSeekActivity.class);
        intent.putExtra("lat", location.latitude);
        intent.putExtra("lng", location.longitude);
        startActivity(intent);
    }

    public void addMarker(String title, String description, double lat, double lng){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(title).snippet(description);
        map.addMarker(marker);
    }

}
