package vn.iotstar.ui_gk.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.iotstar.ui_gk.model.LoginRequest;

public interface APIService {
//    Nguyễn Văn Hùng - 22110338
    @POST("login")
    Call<String> login(@Body LoginRequest loginRequest);

}
