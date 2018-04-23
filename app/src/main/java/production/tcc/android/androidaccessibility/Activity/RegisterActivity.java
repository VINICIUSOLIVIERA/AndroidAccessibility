package production.tcc.android.androidaccessibility.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private Util u = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // SET ADAPTER DEFICIENCY
        String[] array_deficiency = getResources().getStringArray(R.array.array_deficiency);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array_deficiency);
        Spinner spinner_deficiency = findViewById(R.id.form_register_deficiency);
        spinner_deficiency.setAdapter(adapter);

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
                this.saveUser();
                break;
            default:
                break;
        }
        return true;
    }

    public boolean saveUser(){

        UserUtil user_util = new UserUtil(RegisterActivity.this);

        EditText edit_name = (EditText) findViewById(R.id.form_register_name);
        String name = edit_name.getText().toString();

        EditText edit_user = (EditText) findViewById(R.id.form_register_user);
        String user_name = edit_user.getText().toString();

        EditText edit_password = (EditText) findViewById(R.id.form_register_password);
        String password = edit_password.getText().toString();

        EditText edit_date = (EditText) findViewById(R.id.form_register_date);
        String date = edit_date.getText().toString();

        RadioGroup radio_check = (RadioGroup) findViewById(R.id.form_register_gender);
        int radio_index = radio_check.getCheckedRadioButtonId();
        RadioButton selected_radio = (RadioButton) findViewById(radio_index);
        String gender = selected_radio.getText().toString();

        Spinner edit_deficiency = (Spinner) findViewById(R.id.form_register_deficiency);
        String deficiency = edit_deficiency.getSelectedItem().toString();

        EditText edit_cep = (EditText) findViewById(R.id.form_register_cep);
        String cep = edit_cep.getText().toString();

        EditText edit_address = (EditText) findViewById(R.id.form_register_address);
        String address = edit_address.getText().toString();

        EditText edit_email = (EditText) findViewById(R.id.form_register_email);
        String email = edit_email.getText().toString();

        Long id = Long.parseLong("5");

        User user = new User(null, name, user_name, password, date, gender, deficiency, cep, address, email);

        user_util.serialize();
        Log.d("UTILCUSTOM", user_util.toString());

        Retrofit retrofit = new RetrofitConfig().init();
        UserService service = retrofit.create(UserService.class);
        Call<User> call = service.edit(user, id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response != null){
                    try {
                        u.showAlert(RegisterActivity.this, response.errorBody().string(), "error", "Erro ao salvar Usuário", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Code....
            }
        });

        return true;
    }

}
