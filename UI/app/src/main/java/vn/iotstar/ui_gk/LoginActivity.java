package vn.iotstar.ui_gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ui_gk.api.APIService;
import vn.iotstar.ui_gk.api.RetrofitClient;
import vn.iotstar.ui_gk.model.LoginRequest;
import vn.iotstar.ui_gk.screen.activity.UserMainActivity;
import vn.iotstar.ui_gk.screen.fragment.HomeFragment;

//Nguyễn Văn Hùng - 22110338
public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ImageView img_btnLogin;
    private Button bttRegister;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_layout);

        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        img_btnLogin = findViewById(R.id.lg_imageButton);
        bttRegister = findViewById(R.id.btt_Register);

        // Khởi tạo ApiService từ RetrofitClient
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        img_btnLogin.setOnClickListener(view -> login());
        bttRegister.setOnClickListener(view -> goToRegister());
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<String> call = apiService.login(loginRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseText = response.body().trim(); // Loại bỏ khoảng trắng thừa

                    if (responseText.contains("Invalid username or password!!!")) {
                        // Nếu API trả về "Invalid username or password"
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();

                    } else if (responseText.contains("Your account is not verified. Register again!")){
                        // Nếu API trả về "Invalid username or password"
                        Toast.makeText(LoginActivity.this, "Tài khoản chưa được xác thực, vui lòng đăng ký lại!", Toast.LENGTH_SHORT).show();
                    } else{
                        // Nếu API trả về username (tức là đăng nhập thành công)
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", email); // Lưu username vào SharedPreferences
                        editor.putBoolean("active", true);
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Phản hồi không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
