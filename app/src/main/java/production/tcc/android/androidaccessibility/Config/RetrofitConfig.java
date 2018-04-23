package production.tcc.android.androidaccessibility.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit init(){
        OkHttpClient cliente = new OkHttpClient();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.135:8000/api/v1/").addConverterFactory(GsonConverterFactory.create(gson)).client(cliente).build();
        // http://192.168.0.104/api/v1/ Home
        return retrofit;
    }
}
