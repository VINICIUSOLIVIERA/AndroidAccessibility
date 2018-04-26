package production.tcc.android.androidaccessibility.Config;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    private Context context;
    // Loading
    private ProgressDialog dialog;

    // Sharend Preferences
    SharedPreferences shared_preferences;

    public Util(Context context){
        this.context = context;
    }

    // Alert
    public void showAlert(String values, String type, String title, String message) throws JSONException {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.context);

        if(values != null){
            JSONObject json = new JSONObject(values);
            json = json.getJSONObject("error");
            JSONArray array = json.names();
            Object object;
            for(int i = 0; i < array.length(); i++){
                object = array.get(i);
                message += json.getString(object.toString())+"\n";
            }
        }
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }

    // Loading
    public void showLoading(String title, String description){
        if(this.dialog == null)
            this.dialog = new ProgressDialog(this.context);

        this.dialog.setTitle(title);
        this.dialog.setMessage(description);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    public void hideLoading(){
        if(this.dialog != null)
            this.dialog.dismiss();
    }

    // Sharend Preferences
    public void setSharendPreferences(String key, String value){
        if(this.shared_preferences == null)
            this.shared_preferences = PreferenceManager.getDefaultSharedPreferences(this.context);

        SharedPreferences.Editor shared_preferences_edit = this.shared_preferences.edit();
        shared_preferences_edit.putString(Constants.PACKAGE_NAME+key, value);
        shared_preferences_edit.apply();
    }

    public String getSharendPreferences(String key){
        if(this.shared_preferences == null)
            this.shared_preferences = PreferenceManager.getDefaultSharedPreferences(this.context);

        return shared_preferences.getString(Constants.PACKAGE_NAME+key, "Valeu not found");
    }

}
