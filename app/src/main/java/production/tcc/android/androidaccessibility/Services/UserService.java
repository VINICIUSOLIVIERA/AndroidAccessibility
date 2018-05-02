package production.tcc.android.androidaccessibility.Services;

import java.util.List;

import production.tcc.android.androidaccessibility.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users")
    public Call<List<User>> all();

    @POST("user")
    public Call<User> create(@Body User user);

    @PUT("user/{id}")
    public Call<User> edit(@Body User user, @Path("id") Long id);

    @GET("user/{id}")
    public Call<User> find(@Path("id") int id);

}
