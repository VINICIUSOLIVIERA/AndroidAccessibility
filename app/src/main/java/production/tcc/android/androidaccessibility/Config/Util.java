package production.tcc.android.androidaccessibility.Config;

import android.app.AlertDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    public void showAlert(Context context, String values, String type, String title, String message) throws JSONException {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

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

}
