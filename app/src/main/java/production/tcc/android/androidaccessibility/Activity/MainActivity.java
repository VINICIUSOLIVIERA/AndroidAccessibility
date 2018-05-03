package production.tcc.android.androidaccessibility.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.User;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Util.DataBaseUtil;
import production.tcc.android.androidaccessibility.Util.LoginUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Util util = new Util(this);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        int id = item.getItemId();

        if (id == R.id.nav_register) {

        } else if (id == R.id.nav_all) {

        } else if (id == R.id.nav_trending) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_exit) {

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
        }else if (id == R.id.nav_data) {
            DataBaseUtil dbutil = new DataBaseUtil(MainActivity.this);
            User user = dbutil.getUser();
            String message = "USER ID: "+user.getId()+"\nUSER NAME: "+user.getName()+"\nUSER PASSWORD: "+user.getPassword()+"\nDATA NASC.: "+user.getDate_birth()+"\nGENDER: "+(user.getGender() == 1 ? "Masculino" : "Feminino")+"\nDEFICIENCY: "+user.getDeficiency()+"\nCEP: "+user.getCep()+"\nADDRESS: "+user.getAddress()+"\nEMAIL: "+user.getEmail();
            util.showAlert("Dados do banco", message);
        }else if (id == R.id.nav_sharend) {
            String message = "ID: "+util.getSharendPreferences("id")+"\nUSER NAME: "+util.getSharendPreferences("user")+"\nPASSWORD: "+ util.getSharendPreferences("password")+"\nTOKEN: "+util.getSharendPreferences("token");
            util.showAlert("Dados do sharend preferences", message);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
