package vn.iotstar.ui_gk.api;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8081/"; // URL của API

    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    //Duong dan API
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
