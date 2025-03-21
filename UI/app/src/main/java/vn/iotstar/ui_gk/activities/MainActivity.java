package vn.iotstar.ui_gk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.iotstar.ui_gk.R;
import vn.iotstar.ui_gk.screen.activity.UserMainActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btn_main = findViewById(R.id.button);
        btn_main.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
            startActivity(intent);
        });
    }
}