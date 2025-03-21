package vn.iotstar.ui_gk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ui_gk.api.APIService;
import vn.iotstar.ui_gk.api.RetrofitClient;
import vn.iotstar.ui_gk.model.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,pass;
    Button btt;

    APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        name= findViewById(R.id.etFullName);
        email= findViewById(R.id.etEmail);
        pass =findViewById(R.id.etPassword);
        btt= findViewById(R.id.btnRegister);
        btt.setOnClickListener(view -> Register());
        String fname = name.getText().toString();
        String email1 = email.getText().toString();
        String password = pass.getText().toString();
        Log.d("haaa", fname+ email1+password);

    }
    private void Register() {
        String fname = name.getText().toString();
        String email1 = email.getText().toString();
        String password = pass.getText().toString();
        Log.d("haaa", fname+ email1+password);

        if (email1.isEmpty() || password.isEmpty() || fname.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest request = new RegisterRequest(fname, password,email1);
        Call<String> call = apiService.Register(request);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseText = response.body().trim(); // Loại bỏ khoảng trắng thừa

                    if (responseText.contains("Username is already in use")) {
                        // Nếu API trả về "Invalid username or password"
                        Toast.makeText(RegisterActivity.this, "Tài khoản da ton tai!", Toast.LENGTH_SHORT).show();

                    } else if (responseText.contains("Email existed!!!")){
                        // Nếu API trả về "Invalid username or password"
                        Toast.makeText(RegisterActivity.this, "Email da ton tai", Toast.LENGTH_SHORT).show();
                    } else{

                        Toast.makeText(RegisterActivity.this, "Đăng ki thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ProductCategoryActivity.class);
                        intent.putExtra("email", email1);
                        startActivity(new Intent(RegisterActivity.this, otpActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Phản hồi không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_ERROR", "Error: " + t.getMessage());
            }
        });
    }



}