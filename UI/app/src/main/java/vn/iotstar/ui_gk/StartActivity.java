package vn.iotstar.ui_gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.iotstar.ui_gk.screen.activity.UserMainActivity;

// 22110385 - LeHuynhNhuNguyet
public class StartActivity extends AppCompatActivity {

    TextView startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startBtn = (TextView) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tran Trong Nghia-22110380
                if(isLoggedIn()){
                    //Bat dau LoginActivity
                    startActivity(new Intent(StartActivity.this,LoginActivity.class));
                }
                else{
                    startActivity(new Intent(StartActivity.this, UserMainActivity.class));
                }
            }
        });
    }
    //Tran Trong Nghia-22110380
    private boolean isLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false); // Mặc định là false nếu chưa đăng nhập
    }
}