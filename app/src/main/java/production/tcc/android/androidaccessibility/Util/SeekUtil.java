package production.tcc.android.androidaccessibility.Util;

import android.app.Activity;
import android.widget.EditText;

import java.util.ArrayList;

import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.R;

public class SeekUtil extends Activity implements UtilInterface{

    private Util util = new Util(null);
    private Activity activity;
    private EditText edit_topic;
    private String topic;
    private EditText edit_description;
    private String description;
    private float lat;
    private float lng;

    public SeekUtil(Activity activity) {
        this.activity = activity;
        this.edit_topic = activity.findViewById(R.id.form_seek_topic);
        this.edit_description = activity.findViewById(R.id.form_seek_description);
    }

    @Override
    public void serialize() {
        this.topic = this.edit_topic.getText().toString();
        this.description = this.edit_description.getText().toString();
        this.lat = Float.parseFloat(activity.getIntent().getExtras().get("lat").toString());
        this.lng = Float.parseFloat(activity.getIntent().getExtras().get("lng").toString());
    }

    @Override
    public ArrayList<String> validation(){
        ArrayList<String> validate = new ArrayList<String>();

        if(this.topic.isEmpty()){
            validate.add("Assunto não informado.");
        }

        if(this.description.isEmpty()){
            validate.add("Descrição não informada.");
        }

        return validate;
    }

    public Seek getSeek(){
        return new Seek(null, this.topic, this.description, this.lat, this.lng, null);
    }

}
