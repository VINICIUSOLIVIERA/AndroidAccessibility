package production.tcc.android.androidaccessibility.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.json.JSONException;

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

    private Util u = new Util(this);

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

        u.setSharendPreferences("teste", "Hello World!!!!!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menu_inflater = getMenuInflater();
        menu_inflater.inflate(R.menu.form_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_form_register_save:
//                u.showLoading("Enviando dados", "Dados sendo processados...");
                this.saveUser();
                break;
            default:
                break;
        }
        return true;
    }

    public void saveUser(){

        String value = u.getSharendPreferences("teste");


//        UserUtil user_util = new UserUtil(RegisterActivity.this);
//        user_util.serialize();
//
//        User user = user_util.getUser();
//        Long id = 5L;
//
//        Retrofit retrofit = new RetrofitConfig().init();
//        UserService service = retrofit.create(UserService.class);
//        Call<User> call = service.edit(user, id);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(response != null){
//                    try {
//                        u.hideLoading();
//                        u.showAlert(response.errorBody().string(), "error", "Erro ao salvar Usuário", "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                // Code....
//            }
//        });
    }

}
