package production.tcc.android.androidaccessibility.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.User;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.UserService;
import production.tcc.android.androidaccessibility.Util.UserUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private Util util = new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // SET ADAPTER DEFICIENCY
        String[] array_deficiency = getResources().getStringArray(R.array.array_deficiency);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array_deficiency);
        Spinner spinner_deficiency = findViewById(R.id.form_register_deficiency);
        spinner_deficiency.setAdapter(adapter);

        RadioButton gender = findViewById(R.id.radio_gender_m);
        gender.setChecked(true);

        util.setSharendPreferences("teste", "Hello World!!!!!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menu_inflater = getMenuInflater();
        menu_inflater.inflate(R.menu.button_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_form_save:
                util.showLoading("Enviando dados", "Dados sendo processados...");
                this.saveUser();
                break;
            default:
                break;
        }
        return true;
    }

    public void saveUser(){

        final UserUtil user_util = new UserUtil(RegisterActivity.this);
        user_util.serialize();
        User user = user_util.getUser();

        Retrofit retrofit = new RetrofitConfig().init();
        UserService service = retrofit.create(UserService.class);
        Call<User> call = service.create(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response != null){
                    try {
                        util.hideLoading();
                        int code = response.code();
                        if(code == 201){
                            try {
                                User user = response.body();
                                if(user_util.saveDB(RegisterActivity.this, user)){
                                    util.setSharendPreferences("id", ""+user.getId());
                                    util.setSharendPreferences("user", user.getUser());
                                    util.setSharendPreferences("password", user.getPassword());
                                    util.setSharendPreferences("token", user.getToken());
                                    util.setSharendPreferences("connected", "true");

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    util.showAlert("Erro", "Ops!!! Algum problema aconteceu.");
                                }
                            } catch (Exception e) {
                                util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                                Log.d("Error Catch", e.getMessage());
                            }
                        }else if(code == 406){
                            util.showAlertJson(response.errorBody().string(), "Error", "");
                        }else if(code == 500){
                            util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                        }
                    } catch (Exception e) {
                        util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Code....
            }
        });
    }

}
