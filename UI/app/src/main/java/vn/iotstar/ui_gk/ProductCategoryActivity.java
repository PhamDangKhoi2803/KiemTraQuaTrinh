package vn.iotstar.ui_gk;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ui_gk.adapter.ProductCategoryAdapter;
import vn.iotstar.ui_gk.api.APIService;
import vn.iotstar.ui_gk.api.RetrofitClient;
import vn.iotstar.ui_gk.model.Product;

// Tran Tien Dat - 22110308
public class ProductCategoryActivity extends AppCompatActivity {
    private ProductCategoryAdapter productCategoryAdapter;
    private RecyclerView rcvContainer;
    private List<Product> productList, moreProducts;
    boolean isLoading = false;
    long categoryId;
    String categoryName;
    TextView textView;
    int productCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcvContainer = findViewById(R.id.rcv_container);

        productList = new ArrayList<>();

        categoryId = (long) getIntent().getLongExtra("id",1L);
        categoryName = getIntent().getStringExtra("name");
        getProducts(categoryId);


        initScrollListener();
    }

    private void getProducts(Long id){
        // Goi interface trong API Service
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProducts(id, 0).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productList = response.body();
                    Log.d("Logg", productList.size() + "");
                    productCount = productList.size();
                    textView = findViewById(R.id.txt_category);
                    textView.setText(categoryName + ": " + productCount);
                    productCategoryAdapter = new ProductCategoryAdapter(ProductCategoryActivity.this, productList);
                    rcvContainer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    rcvContainer.setAdapter(productCategoryAdapter);
                    productCategoryAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Log.d("Logg Lỗi product", statusCode + "");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("Call API Fail ", t.getMessage());
            }
        });
    }

    private void initScrollListener() {
        rcvContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged (recyclerView, newState);
            }
            @Override
            public void onScrolled (@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null
                            & linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size() - 1) {
                        //bottom of list!
                        LoadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
    private int page = 0; // Biến theo dõi trang hiện tại

    private void LoadMore() {
        // Thêm phần tử null để hiển thị ProgressBar
        productList.add(null);
        productCategoryAdapter.notifyItemInserted(productList.size() - 1);

        new Handler().postDelayed(() -> {
            // Xóa phần tử loading
            int loadingPosition = productList.size() - 1;
            productList.remove(loadingPosition);
            productCategoryAdapter.notifyItemRemoved(loadingPosition);

            // Tăng page trước khi gọi API
            page++;

            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.getProducts(categoryId, page).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Product> newProducts = response.body();
                        int startPosition = productList.size();
                        productCount += newProducts.size();
                        textView.setText(categoryName + ": " + productCount);
                        productList.addAll(newProducts);
                        productCategoryAdapter.notifyItemRangeInserted(startPosition, newProducts.size());
                        Log.d("Logg", newProducts.size() + " sản phẩm được tải");
                    }
                    isLoading = false;
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    Log.e("API Error", "Không thể tải thêm sản phẩm: " + t.getMessage());
                    isLoading = false;
                }
            });
        }, 2000);
    }

}