package production.tcc.android.androidaccessibility.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.SeekService;
import production.tcc.android.androidaccessibility.Util.SeekUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateSeekActivity extends AppCompatActivity {

    private Util util = new Util(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seek);

        Bundle extras = getIntent().getExtras();
        TextView text_lat = findViewById(R.id.form_seek_text_lat);
        TextView text_lng = findViewById(R.id.form_seek_text_lng);
        text_lat.setText(text_lat.getText()+" "+extras.get("lat"));
        text_lng.setText(text_lng.getText()+" "+extras.get("lng"));

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
                this.saveSeek();
                break;
            default:
                break;
        }
        return true;
    }

    public void saveSeek(){
        Long user_id = Long.parseLong(util.getSharendPreferences("id"));
        SeekUtil seek_util = new SeekUtil(CreateSeekActivity.this);
        seek_util.serialize();
        Seek seek = seek_util.getSeek();

        Retrofit retrofit = RetrofitConfig.init();
        SeekService service = retrofit.create(SeekService.class);
        Call<Seek> call = service.create(user_id, seek);
        call.enqueue(new Callback<Seek>() {
            @Override
            public void onResponse(Call<Seek> call, Response<Seek> response) {
                util.hideLoading();
                if(response != null){
                    int code = response.code();
                    if(code == 201){
                        finish();
                        util.showAlert("Solicitação", "Solicitação realizada com sucesso.");
                    }else if(code == 406){
                        try {
                            util.showAlertJson(response.errorBody().string(), "Error", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(code == 500){
                        util.showAlert("Ops!!!", "Algo de errado aconteceu. Tente novamente.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Seek> call, Throwable t) {
                Log.d("ErroRequest", t.getMessage());
            }
        });
    }
}
