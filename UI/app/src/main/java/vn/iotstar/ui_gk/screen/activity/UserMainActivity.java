package vn.iotstar.ui_gk.screen.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.iotstar.ui_gk.R;
import vn.iotstar.ui_gk.screen.fragment.HomeFragment;

public class UserMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment =new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setCurrentFragment(homeFragment);
        bottomNavigationViewSetup();
    }
    private void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
    private void bottomNavigationViewSetup(){
        bottomNavigationView.setSelectedItemId(R.id.itemHome);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.itemHome){
                setCurrentFragment(homeFragment);
            }
            return true;
        });
    }

}