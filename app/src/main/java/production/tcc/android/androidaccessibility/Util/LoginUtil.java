package production.tcc.android.androidaccessibility.Util;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import java.util.ArrayList;

import production.tcc.android.androidaccessibility.R;

public class LoginUtil extends Activity{

    private EditText user_edit;
    private String user;
    private EditText passoword_edit;
    private String password;
    private CheckBox connected_edit;
    private boolean connected;

    public LoginUtil(Activity activity){
        this.user_edit      = activity.findViewById(R.id.form_login_user);
        this.passoword_edit = activity.findViewById(R.id.form_login_password);
        this.connected_edit = activity.findViewById(R.id.form_login_connected);
    }

    public void serialize(){
        this.user      = user_edit.getText().toString();
        this.password  = passoword_edit.getText().toString();
        this.connected = connected_edit.isChecked();
    }

    public ArrayList<String> validation(){
        ArrayList<String> validate = new ArrayList<String>();

        if(this.user.isEmpty()){
            validate.add("Usuário não informado.");
        }

        if(this.password.isEmpty()){
            validate.add("Senha não informada.");
        }else if(!this.password.isEmpty() && this.password.length() < 6){
            validate.add("Senha deve conter no mínimo 6 caracteres.");
        }

        return validate;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isConnected() {
        return this.connected;
    }
}
