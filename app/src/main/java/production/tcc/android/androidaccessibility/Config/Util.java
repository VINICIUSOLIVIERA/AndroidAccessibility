package production.tcc.android.androidaccessibility.Config;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Util {

    private Context context;
    // Loading
    private ProgressDialog dialog;
    // Sharend Preferences
    SharedPreferences shared_preferences;
    // Alert
    AlertDialog.Builder alert;

    public Util(Context context){
        this.context = context;
    }

    // Alert - Simple
    public void showAlert(String title, String message){
        if(this.alert == null)
            this.alert   = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }

    // Alert - Json
    public void showAlertJson(String values, String title, String message) throws JSONException {
        JSONObject json = new JSONObject(values);
        json = json.getJSONObject("error");
        JSONArray array = json.names();
        Object object;
        for(int i = 0; i < array.length(); i++){
            object = array.get(i);
            message += json.getString(object.toString())+"\n";
        }
        this.showAlert(title, message);
    }

    // Alert - Array
    public void showAlertArray(ArrayList<String> values, String title, String message){
        for(String row : values){
            message += row+"\n";
        }
        this.showAlert(title, message);
    }

    // Loading - Show
    public void showLoading(String title, String description){
        if(this.dialog == null)
            this.dialog = new ProgressDialog(this.context);

        this.dialog.setTitle(title);
        this.dialog.setMessage(description);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    // Loading - Hide
    public void hideLoading(){
        if(this.dialog != null)
            this.dialog.dismiss();
    }

    // Sharend Preferences - Set values
    public void setSharendPreferences(String key, String value){
        if(this.shared_preferences == null)
            this.shared_preferences = PreferenceManager.getDefaultSharedPreferences(this.context);

        SharedPreferences.Editor shared_preferences_edit = this.shared_preferences.edit();
        shared_preferences_edit.putString(Constants.PACKAGE_NAME+key, value);
        shared_preferences_edit.apply();
    }

    // Sharend Preferences - Get values
    public String getSharendPreferences(String key){
        if(this.shared_preferences == null)
            this.shared_preferences = PreferenceManager.getDefaultSharedPreferences(this.context);

        return shared_preferences.getString(Constants.PACKAGE_NAME+key, "Valeu not found");
    }

    // Sharend Preferences - Remove values
    public void destroySharendPreferences(String key){
        if(this.shared_preferences == null)
            this.shared_preferences = PreferenceManager.getDefaultSharedPreferences(this.context);

        SharedPreferences.Editor edit = this.shared_preferences.edit();
        edit.remove(Constants.PACKAGE_NAME+key);
        edit.apply();
    }

}
