package production.tcc.android.androidaccessibility.Services;

import java.util.List;

import production.tcc.android.androidaccessibility.Models.Seek;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SeekService {

    @GET("seeks")
    public Call<List<Seek>> all();

    @GET("seeks/user/{user_id}")
    public Call<List<Seek>> allByUser(@Path("user_id") Long user_id);

    @GET("seek/{seek_id}/user/{user_id}")
    public Call<List<Seek>> specificByUser(@Path("seek_id") Long seek_id, @Path("user_id") Long user_id);

    @POST("seek/user/{user_id}")
    public Call<Seek> create(@Path("user_id") Long user_id, @Body Seek seek);

    @PUT("user/{user_id}/seek/{seek_id}")
    public Call<Seek> edit(@Body Seek seek, @Path("user_id") Long user_id, @Path("seek_id") Long seek_id);

}
