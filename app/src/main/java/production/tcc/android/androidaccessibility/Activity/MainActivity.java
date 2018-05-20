package production.tcc.android.androidaccessibility.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Helpers.MapHelper;
import production.tcc.android.androidaccessibility.Models.User;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Util.DataBaseUtil;

public class MainActivity extends MapHelper implements
        NavigationView.OnNavigationItemSelectedListener{

    private Util util = new Util(this);
    private MapView mapView;

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

    public void changeCreateSeek(){
        LatLng location = this.userLocationCurrent();
        Intent intent = new Intent(this, CreateSeekActivity.class);
        intent.putExtra("lat", location.latitude);
        intent.putExtra("lng", location.longitude);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Teste result", Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
