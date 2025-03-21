package vn.iotstar.ui_gk.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.iotstar.ui_gk.model.User;

public interface APIService {
    @POST("sendcode")
    Call<String> sendOtp(@Query("email") String email);

    @POST("register")
    Call<ResponseBody> registerUser(@Body User user, @Query("otp") String otp);
}
