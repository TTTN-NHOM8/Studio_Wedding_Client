package com.example.studiowedding.view.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.studiowedding.R;
import com.example.studiowedding.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class Menu_Services_Product extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar2);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navigationView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Menu, R.id.navNotification)
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
        switch (item.getItemId()) {
            case R.id.Menu:
                getSupportActionBar().setTitle("");
                fragment = new HomeFragment();
                break;
            case R.id.Menu1:
                getSupportActionBar().setTitle("");
                fragment = new NoticationFragment();
                break;
            case R.id.Services:
                getSupportActionBar().setTitle("Danh sách Dịch Vụ");
                fragment = new NoticationFragment();
                break;
            case R.id.Product:
                getSupportActionBar().setTitle("Danh sách Sản Phẩm");
                fragment = new NoticationFragment();
                break;
            default:
                return false;
        }
        loadFragment(fragment);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}