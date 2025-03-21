package vn.iotstar.ui_gk.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.iotstar.ui_gk.model.LoginRequest;
import vn.iotstar.ui_gk.model.OtpRequest;
import vn.iotstar.ui_gk.model.RegisterRequest;

public interface APIService {
//    Nguyễn Văn Hùng - 22110338
    @POST("login")
    Call<String> login(@Body LoginRequest loginRequest);
    // Tạ Nghĩa Nhân - 22110388
    @POST("api/register")
    Call<String> register(@Body RegisterRequest registerRequest);
    @POST("api/verify-otp")
    Call<String> verifyOtp(@Body OtpRequest otpRequest);

}
