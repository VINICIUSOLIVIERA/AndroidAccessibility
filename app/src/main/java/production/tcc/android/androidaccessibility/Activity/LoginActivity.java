package production.tcc.android.androidaccessibility.Activity;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import production.tcc.android.androidaccessibility.Config.DataBase;
import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.User;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.LoginService;
import production.tcc.android.androidaccessibility.Util.LoginUtil;
import production.tcc.android.androidaccessibility.Util.UserUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private DataBase dbconfig  = new DataBase(this);
    private Util util          = new Util(this);
    private UserUtil user_util = new UserUtil(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.credentials();

        TextView register = findViewById(R.id.form_login_link_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button send = findViewById(R.id.form_login_btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }


    public void login(){
        final LoginUtil loginUtil = new LoginUtil(LoginActivity.this);
        loginUtil.serialize();

        ArrayList<String> validate = loginUtil.validation();
        if(validate.size() > 0){
            util.showAlertArray(validate, "Erro do Login", "");
        }else{
            util.showLoading("Fazendo login", "Aguarde um momento");
            Retrofit retrofit       = RetrofitConfig.init();
            LoginService service    = retrofit.create(LoginService.class);
            Call<User> call = service.login(loginUtil.getUser(), loginUtil.getPassword());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    util.hideLoading();
                    if(response != null){
                        int code = response.code();
                        if(code == 200){
                            try {
                                User user = response.body();
                                if(user_util.saveDB(LoginActivity.this, user)){
                                    util.setSharendPreferences("id", ""+user.getId());
                                    util.setSharendPreferences("user", user.getUser());
                                    util.setSharendPreferences("password", user.getPassword());
                                    util.setSharendPreferences("token", user.getToken());
                                    util.setSharendPreferences("connected", ""+loginUtil.isConnected());

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    util.showAlert("Erro", "Ops!!! Algum problema aconteceu.");
                                }
                            } catch (Exception e) {
                                util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                                Log.d("Error Catch", e.getMessage());
                            }
                        }else if(code == 401 || code == 404){
                            util.showAlert("Erro", "Credênciais inválidas. Tente novamente.");
                        }else{
                            util.showAlert("Erro", "Erro interno. Tente novamente mais tarde!");
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    util.hideLoading();
                    util.showAlert("Erro", "Verifique sua rede.");
                }
            });
        }
    }

    public void credentials(){
        util.showLoading("Carregando", "Aguarde");
        String id = util.getSharendPreferences("id");
        if(!id.equals("Valeu not found")){
            String connected = util.getSharendPreferences("connected");
            if(connected.equals("true")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        util.hideLoading();
    }
}
