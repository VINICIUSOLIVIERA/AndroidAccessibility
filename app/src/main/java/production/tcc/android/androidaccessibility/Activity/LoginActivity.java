package production.tcc.android.androidaccessibility.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.LoginService;
import production.tcc.android.androidaccessibility.Util.LoginUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Util util = new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        String user_id        = util.getSharendPreferences("id");
//        String user_name      = util.getSharendPreferences("user");
//        String user_password  = util.getSharendPreferences("password");
//        String user_token     = util.getSharendPreferences("token");
//        String user_connected = util.getSharendPreferences("connected");
//
//        String message = "ID: "+user_id+"\n"+"Nome: "+user_name+"\n"+"Senha: "+user_password+"\n"+"Token: "+user_token+"\n"+"Conectado: "+user_connected;
//
//        util.showAlert("Dados do usuário", message);

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
            Call<ResponseBody> call = service.login(loginUtil.getUser(), loginUtil.getPassword());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    util.hideLoading();
                    if(response != null){
                        int code = response.code();
                        if(code == 200){
                            try {
                                JSONObject json = new JSONObject(response.body().string());
                                util.setSharendPreferences("id", json.getString("id"));
                                util.setSharendPreferences("user", json.getString("user"));
                                util.setSharendPreferences("password", json.getString("password"));
                                util.setSharendPreferences("token", json.getString("token"));
                                util.setSharendPreferences("connected", ""+loginUtil.isConnected());

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();

                            } catch (Exception e) {
                                util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                                Log.d("Error Catch", e.getMessage());
                            }
                        }else if(code == 401){
                            util.showAlert("Erro", "Credênciais inválidas. Tente novamente.");
                        }else{
                            util.showAlert("Erro", "Erro interno. Tente novamente mais tarde!");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    util.showAlert("Erro", "Verifique sua rede.");
                }
            });
        }
    }
}
