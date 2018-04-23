package production.tcc.android.androidaccessibility.Util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import production.tcc.android.androidaccessibility.R;

public class UserUtil extends Activity{

    private Activity activity;
    private EditText edit_name;
    private String name;
    private EditText edit_user;
    private String user;
    private EditText edit_password;
    private String password;
    private EditText edit_date_birth;
    private String date_birth;
    private RadioGroup edit_gender;
    private int gender;
    private Spinner edit_deficiency;
    private int deficiency;
    private EditText edit_cep;
    private int cep;
    private EditText edit_address;
    private String address;
    private EditText edit_email;
    private String email;

    public UserUtil(Activity activity){
        this.activity        = activity;
        this.edit_name       = activity.findViewById(R.id.form_register_name);
        this.activity        = activity;
        this.edit_name       = activity.findViewById(R.id.form_register_name);
        this.edit_user       = activity.findViewById(R.id.form_register_user);
        this.edit_password   = activity.findViewById(R.id.form_register_password);
        this.edit_date_birth = activity.findViewById(R.id.form_register_date);
        this.edit_gender     = activity.findViewById(R.id.form_register_gender);
        this.edit_deficiency = activity.findViewById(R.id.form_register_deficiency);
        this.edit_cep        = activity.findViewById(R.id.form_register_cep);
        this.edit_address    = activity.findViewById(R.id.form_register_address);
        this.edit_email      = activity.findViewById(R.id.form_register_email);

    }

    public void serialize(){
        this.name       = this.edit_name.getText().toString();
        this.user       = this.edit_user.getText().toString();
        this.password   = this.edit_password.getText().toString();
        this.date_birth = this.edit_date_birth.getText().toString();

        int gender_index = this.edit_gender.getCheckedRadioButtonId();
        Log.d("Values_gender", ""+gender_index);
        RadioButton selected_gender = (RadioButton) this.activity.findViewById(gender_index);
        if(selected_gender.getText().toString().equals("Masculino")){
            this.gender = 1;
        }else if(selected_gender.getText().toString().equals("Feminino")){
            this.gender = 2;
        }else{
            this.gender = 0;
        }

        String deficiency = edit_deficiency.getSelectedItem().toString();
        if(deficiency.equals("Selecione")){
            this.deficiency = 0;
        }else if(deficiency.equals("Não")){
            this.deficiency = 1;
        }else if(deficiency.equals("Física")){
            this.deficiency = 2;
        }else if(deficiency.equals("Mental")){
            this.deficiency = 3;
        }else{
            this.deficiency = 0;
        }

        this.cep     = Integer.parseInt(this.edit_cep.getText().toString());
        this.address = this.edit_address.getText().toString();
        this.email   = this.edit_email.getText().toString();
    }

    @Override
    public String toString() {
        return "UserUtil{" +
                "name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", date_birth='" + date_birth + '\'' +
                ", gender=" + gender +
                ", deficiency=" + deficiency +
                ", cep=" + cep +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
