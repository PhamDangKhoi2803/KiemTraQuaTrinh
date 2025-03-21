package vn.iotstar.ui_gk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.ui_gk.LoginActivity;
import vn.iotstar.ui_gk.R;
import vn.iotstar.ui_gk.api.APIService;
import vn.iotstar.ui_gk.api.RetrofitClient;
import vn.iotstar.ui_gk.model.OtpRequest;

public class OtpActivity extends AppCompatActivity {
    private EditText edtOtp;
    private Button btnVerify;
    private APIService apiService;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        edtOtp = findViewById(R.id.edtOtp);
        btnVerify = findViewById(R.id.btnVerify);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");


        btnVerify.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = edtOtp.getText().toString().trim();
        Log.d("OTP", "OTP nhập: " + otp);
        Log.d("OTP", "Email: " + email);
        Log.d("OTP", "Password: " + password);
        if (otp.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập OTP!", Toast.LENGTH_SHORT).show();
            return;
        }

        OtpRequest otpRequest = new OtpRequest(email,otp,password);
        apiService.verifyOtp(otpRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OtpActivity.this, "Xác thực thành công! Hãy đăng nhập.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(OtpActivity.this, "OTP không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
