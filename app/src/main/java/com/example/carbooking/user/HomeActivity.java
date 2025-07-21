package com.example.carbooking.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.example.carbooking.EditUser;
import com.example.carbooking.Entity.User;
import com.example.carbooking.LoginPage;
import com.example.carbooking.OrderTour.ListOrder;
import com.example.carbooking.R;
import com.example.carbooking.databinding.ActivityHomeBinding;
import com.example.carbooking.databinding.HeaderHomeNavigationBinding;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.UserRepository;
import com.example.carbooking.user.adapter.TourAdapter;
import com.example.carbooking.user.decorator.GridSpacingItemDecoration;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;

    private SharedPreferences preferences;
    private UserRepository userRepository;
    private TourRepository tourRepository;

    private TourAdapter tourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRepositories();
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tourAdapter.submitList(tourRepository.getAllTour());
    }

    private void initRepositories() {
        userRepository = new UserRepository(this);
        tourRepository = new TourRepository(this);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    private void initialize() {
        // Init Toolbar
        setSupportActionBar(binding.toolbar);

        // Init RecyclerView
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 32, true));
        binding.recyclerView.setAdapter(tourAdapter = new TourAdapter());

        // Init DrawerLayout
        binding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Set user info
        HeaderHomeNavigationBinding headerBinding = HeaderHomeNavigationBinding
                .bind(binding.navView.getHeaderView(0));
        int userId = preferences.getInt("userId", -1);
        User user = userRepository.getUserById(userId);
        if (user != null) {
            Glide.with(this)
                    .load(user.getAvatar())
                    .error(R.drawable.ic_user)
                    .into(headerBinding.imageAvatar);
            headerBinding.textUsername.setText(user.getUserName());
            headerBinding.textEmail.setText(user.getEmail());
        }

        // Set listeners
        headerBinding.viewInfo.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EditUser.class);
            startActivity(intent);
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle log out
        if (menuItem.getItemId() == R.id.action_logout) {
            preferences.edit().clear().apply();
            startActivity(new Intent(this, LoginPage.class));
            finish();
        } else if (menuItem.getItemId() == R.id.action_history) {
            Intent intent = new Intent(this, ListOrder.class);
            startActivity(intent);
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}