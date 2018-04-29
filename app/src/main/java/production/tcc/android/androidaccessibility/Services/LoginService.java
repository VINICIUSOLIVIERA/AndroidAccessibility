package production.tcc.android.androidaccessibility.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("login")
    public Call<ResponseBody> login(@Field("user") String user, @Field("password") String password);

}
