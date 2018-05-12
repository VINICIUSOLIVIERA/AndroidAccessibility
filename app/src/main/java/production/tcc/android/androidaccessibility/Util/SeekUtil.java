package production.tcc.android.androidaccessibility.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.R;

public class SeekUtil extends Activity {

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

    public void serialize() {
        this.topic = this.edit_topic.getText().toString();
        this.description = this.edit_description.getText().toString();
        this.lat = Float.parseFloat(activity.getIntent().getExtras().get("lat").toString());
        this.lng = Float.parseFloat(activity.getIntent().getExtras().get("lng").toString());
    }

    public Seek getSeek(){
        return new Seek(null, this.topic, this.description, this.lat, this.lng, null);
    }

}
