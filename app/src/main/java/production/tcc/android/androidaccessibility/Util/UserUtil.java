package production.tcc.android.androidaccessibility.Util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import production.tcc.android.androidaccessibility.Config.DataBase;
import production.tcc.android.androidaccessibility.Models.User;
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
        if(activity != null){
            this.activity = activity;
            this.edit_name = activity.findViewById(R.id.form_register_name);
            this.edit_user = activity.findViewById(R.id.form_register_user);
            this.edit_password = activity.findViewById(R.id.form_register_password);
            this.edit_date_birth = activity.findViewById(R.id.form_register_date);
            this.edit_gender = activity.findViewById(R.id.form_register_gender);
            this.edit_deficiency = activity.findViewById(R.id.form_register_deficiency);
            this.edit_cep = activity.findViewById(R.id.form_register_cep);
            this.edit_address = activity.findViewById(R.id.form_register_address);
            this.edit_email = activity.findViewById(R.id.form_register_email);
        }

    }

    public void serialize() {
        this.name = this.edit_name.getText().toString();
        this.user = this.edit_user.getText().toString();
        this.password = this.edit_password.getText().toString();
        this.date_birth = this.edit_date_birth.getText().toString();
        this.gender = getGender();
        this.deficiency = getDeficiency();
        this.cep = getCep();
        this.address = this.edit_address.getText().toString();
        this.email = this.edit_email.getText().toString();
    }

    public int getGender(){
        int gender_index = this.edit_gender.getCheckedRadioButtonId();
        RadioButton selected_gender = (RadioButton) this.activity.findViewById(gender_index);
        return (selected_gender.getText().toString().equals("Masculino") ? 1 : (selected_gender.getText().toString().equals("Feminino") ? 2 : 0 ));
    }

    public int getDeficiency(){
        String deficiency = edit_deficiency.getSelectedItem().toString();
        return (deficiency.equals("Selecione") ? 0 : (deficiency.equals("Não") ? 1 : (deficiency.equals("Física") ? 2 : (deficiency.equals("Mental") ? 3 : 0))));
    }

    public int getCep(){
        return (this.edit_cep.getText().toString().isEmpty() ? 0 : Integer.parseInt(this.edit_cep.getText().toString()));
    }

    public User getUser(){
        return new User(null, this.name, this.user, this.password, this.date_birth, this.gender, this.deficiency, this.cep, this.address, this.email);
    }

    public boolean saveDB(Context context, User user){
        try {
            DataBaseUtil dbutil = new DataBaseUtil(context);

            ContentValues values = new ContentValues();
            values.put("id", 1);
            values.put("user_id", user.getId());
            values.put("name", user.getName());
            values.put("user", user.getUser());
            values.put("password", user.getPassword());
            values.put("date_birth", user.getDate_birth());
            values.put("gender", user.getGender());
            values.put("deficiency", user.getDeficiency());
            values.put("cep", user.getCep());
            values.put("address", user.getAddress());
            values.put("email", user.getEmail());

            dbutil.destroyAll("user");
            dbutil.insert("user", values);

            return true;
        }catch (Exception e){
            Log.d("USER_SAVE_DB", e.getMessage());
            return false;
        }
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
