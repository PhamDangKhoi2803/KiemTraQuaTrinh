package vn.iotstar.ui_gk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class otp_ver extends AppCompatActivity {

    EditText eetOtp1,eetOtp2,eetOtp3,eetOtp4,eetOtp5,eetOtp6;
    APIService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String email =  getIntent().getStringExtra("email");
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        eetOtp1=findViewById(R.id.etOtp1);
        eetOtp2=findViewById(R.id.etOtp2);
        eetOtp3=findViewById(R.id.etOtp3);
        eetOtp4=findViewById(R.id.etOtp4);
        eetOtp5=findViewById(R.id.etOtp5);
        eetOtp6=findViewById(R.id.etOtp6);

        String otp=eetOtp1.getText().toString()+
                eetOtp2.getText().toString()+
                eetOtp3.getText().toString()+
                eetOtp4.getText().toString()+
                eetOtp5.getText().toString()+
                eetOtp6.getText().toString();

        otp(email,otp);

    }
    private void otp(String email,String otp) {

        Call<String> call = apiService.veriOTP(email,otp);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseText = response.body().trim(); // Loại bỏ khoảng trắng thừa

                    if (responseText.contains("Invalid OTP. Please check and try again.")) {
                        // Nếu API trả về "Invalid username or password"
                        Toast.makeText(otp_ver.this, "OTP khong dung", Toast.LENGTH_SHORT).show();

                    } else{

                        Toast.makeText(otp_ver.this, "Xac thuc thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(otp_ver.this, otpActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(otp_ver.this, "Phản hồi không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(otp_ver.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_ERROR", "Error: " + t.getMessage());
            }
        });
    }
}