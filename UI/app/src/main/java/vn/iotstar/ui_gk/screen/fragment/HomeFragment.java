package vn.iotstar.ui_gk.screen.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ui_gk.R;
import vn.iotstar.ui_gk.adapter.CategoryAdapter;
import vn.iotstar.ui_gk.api.APIService;
import vn.iotstar.ui_gk.api.RetrofitClient;
import vn.iotstar.ui_gk.model.Category;
import vn.iotstar.ui_gk.screen.activity.UserMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RetrofitClient RetrofitClient;

    private RecyclerView recyclerView;

    private CategoryAdapter categoryAdapter;

    private List<Category> listCate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //vophuhao
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ RecyclerView
        recyclerView = view.findViewById(R.id.rvCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemViewCacheSize(3);

        // Khởi tạo danh sách rỗng và gán adapter trước
        listCate = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(requireContext(), listCate);
        recyclerView.setAdapter(categoryAdapter);

        // Gọi API để lấy danh mục
        getALlCategory();


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        TextView tv_username = view.findViewById(R.id.tvUserName);
        tv_username.setText("Hi! "+ sharedPreferences.getString("username", "dell co"));
        return view;
    }

    public void getALlCategory() {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<List<Category>> call = apiService.getAllCate();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listCate.addAll(response.body()); // Cập nhật danh sách mới
                    categoryAdapter.notifyDataSetChanged(); // Thông báo RecyclerView cập nhật dữ liệu
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Log.e("Category", "API Call failed: " + t.getMessage());
            }
        });
    }
    //vophuhao
}