package production.tcc.android.androidaccessibility.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seek {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("topic")
    @Expose
    private String topic;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("lat")
    @Expose
    private float lat;

    @SerializedName("lng")
    @Expose
    private float lng;

    @SerializedName("user_id")
    @Expose
    private Long user_id;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public Seek(Long id, String topic, String description, float lat, float lng, Long user_id) {
        this.id = id;
        this.topic = topic;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getCreated() {
        return created_at;
    }

    public void setCreated(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return this.topic + " - " +this.description;
    }
}
