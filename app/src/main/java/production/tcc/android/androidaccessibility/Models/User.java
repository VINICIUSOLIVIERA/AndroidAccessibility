package production.tcc.android.androidaccessibility.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    public User(Long id, String name, String user, String password, String date_birth, String gender, String deficiency, String cep, String address, String email) {
        this.id         = id;
        this.name       = name;
        this.user       = user;
        this.password   = password;
        this.date_birth = date_birth;
        this.gender     = gender;
        this.deficiency = deficiency;
        this.cep        = cep;
        this.address    = address;
        this.email      = email;
    }

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("user")
    @Expose
    private String user;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("date_birth")
    @Expose
    private String date_birth;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("deficiency")
    @Expose
    private String deficiency;

    @SerializedName("cep")
    @Expose
    private String cep;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("email")
    @Expose
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getSex() {
        return gender;
    }

    public void setSex(String gender) {
        this.gender = gender;
    }

    public String getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(String deficiency) {
        this.deficiency = deficiency;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", date_birth='" + date_birth + '\'' +
                ", gender='" + gender + '\'' +
                ", deficiency='" + deficiency + '\'' +
                ", cep=" + cep +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
