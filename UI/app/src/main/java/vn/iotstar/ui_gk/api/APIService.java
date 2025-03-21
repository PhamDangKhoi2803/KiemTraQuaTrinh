package vn.iotstar.ui_gk.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import vn.iotstar.ui_gk.model.Category;
import retrofit2.http.Path;
import vn.iotstar.ui_gk.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import vn.iotstar.ui_gk.model.Category;
import vn.iotstar.ui_gk.model.Product;
import vn.iotstar.ui_gk.model.RegisterRequest;

public interface APIService {
    //    Nguyễn Văn Hùng - 22110338
    @POST("login")
    Call<String> login(@Body LoginRequest loginRequest);

    //vophuhao
    @GET("/api/categories")
    Call<List<Category>> getAllCate();

    // Trần Tiến Đạt - 22110308
    @GET("/api/product/{id}/{page}")
    Call<List<Product>> getProducts(@Path("id") Long id, @Path("page") int page);


    //Ta Nghia Nhan
    @POST("/register")
    Call<String> Register(@Body RegisterRequest registerRequest);

    //Ta Nghia Nhan
    @PUT("/verify-account")
    Call<String> veriOTP(@Query("email") String email, @Query("otp") String otp);
}
