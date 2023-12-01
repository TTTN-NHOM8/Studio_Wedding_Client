package com.example.studiowedding.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.databinding.ActivityMainBinding;
import com.example.studiowedding.view.activity.account.Login;
import com.example.studiowedding.view.fragment.CustomerFragment;
import com.example.studiowedding.view.fragment.EmployeeFragment;
import com.example.studiowedding.view.fragment.HomeFragment;
import com.example.studiowedding.view.fragment.InvoiceFragment;
import com.example.studiowedding.view.fragment.NoticationFragment;
import com.example.studiowedding.view.fragment.ProductAndServicesFragment;
import com.example.studiowedding.view.fragment.ServicesFragment;
import com.example.studiowedding.view.fragment.SettingFragment;
import com.example.studiowedding.view.fragment.StatisticFragment;
import com.example.studiowedding.view.fragment.TaskFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navigationView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navHome, R.id.navNotification)
                .setOpenableLayout(drawer)
                .build();


        binding.navigationView.setNavigationItemSelectedListener(item -> handleNavigationItemClick(item));
        loadFragment(new HomeFragment());
        initToolbar();
    }
    private void initToolbar() {
        setSupportActionBar(binding.toolbar2);
        binding.toolbar2.setNavigationOnClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    private boolean handleNavigationItemClick(MenuItem item) {
        Fragment fragment = null;
        SharedPreferences preferences = getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
        String vaitro = preferences.getString("Vaitro", "");

        switch (item.getItemId()) {
            case R.id.navHome:
                getSupportActionBar().setTitle("Trang chủ");
                fragment = new HomeFragment();
                break;
            case R.id.navTask:
                getSupportActionBar().setTitle("Danh sách công việc");
                fragment = new TaskFragment();
                break;
            case R.id.navSettings:
                getSupportActionBar().setTitle("Cài đặt");
                fragment = new SettingFragment();
                break;
            case R.id.navLogout:
                logout();
                break;
            default:
                if (!"Quan li".equals(vaitro)) {
                    Toast.makeText(this, "Bạn không đủ thẩm quyền", Toast.LENGTH_SHORT).show();
                    return false;
                }
                switch (item.getItemId()) {
                    case R.id.navNotification:
                        getSupportActionBar().setTitle("Danh sách thông báo");
                        fragment = new NoticationFragment();
                        break;
                    case R.id.navEmployee:
                        getSupportActionBar().setTitle("Danh sách nhân viên");
                        fragment = new EmployeeFragment();
                        break;
                    case R.id.navClient:
                        getSupportActionBar().setTitle("Danh sách khách hàng");
                        fragment = new CustomerFragment();
                        break;
                    case R.id.navServices:
                        getSupportActionBar().setTitle("Sản phẩm & Dịch vụ");
                        fragment = new ProductAndServicesFragment();
                        break;
                    case R.id.navInvoice:
                        getSupportActionBar().setTitle("Danh sách hợp đồng");
                        fragment = new InvoiceFragment();
                        break;
                    case R.id.navStatistic:
                        getSupportActionBar().setTitle("Thống kê");
                        fragment = new StatisticFragment();
                        break;
                    default:
                        return false;
                }
                break;
        }

        if (fragment != null) {
            loadFragment(fragment);
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    private void logout() {
        SharedPreferences preferences = getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

}