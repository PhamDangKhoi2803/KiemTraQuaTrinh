package vn.iotstar.ui_gk.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.iotstar.ui_gk.model.Category;

public interface APIService {

    @GET("/api/categories")
    Call<List<Category>> getAllCate();
}
