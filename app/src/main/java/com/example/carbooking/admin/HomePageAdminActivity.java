package com.example.carbooking.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.carbooking.LoginPage;
import com.example.carbooking.R;
import com.example.carbooking.admin.order.OrderManagement;
import com.example.carbooking.admin.tour.AddTourActivity;
import com.example.carbooking.admin.tour.ListTourActivity;
import com.example.carbooking.admin.user.UserManagementActivity;
import com.google.android.material.navigation.NavigationView;

public class HomePageAdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;
    SharedPreferences preferences;
    private CardView cardManageTours;
    private CardView cardManageAddTours;
    private CardView cardManageUser;
    private CardView cardManagerOrder;
    private CardView cardManagerLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page_admin);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        buttonDrawerToggle = findViewById(R.id.button_drawerToggle);
        System.out.println("buttonDrawerToggle : " + buttonDrawerToggle);

        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        System.out.println("drawerLayout : " + drawerLayout);
        navigationView = findViewById(R.id.navigationViewAdmin);
        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        View headerView = navigationView.getHeaderView(0);
        ImageView adminImage = headerView.findViewById(R.id.imgAdmin);
        TextView txtUserName = headerView.findViewById(R.id.txtUserName);

        adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageAdminActivity.this, txtUserName.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_tour_management) {
                    // Toggle submenu visibility
                    menuItem.setChecked(!menuItem.isChecked());
                    if (menuItem.isChecked()) {
                        navigationView.getMenu().setGroupVisible(R.id.nav_tour_management, true);
                    } else {
                        navigationView.getMenu().setGroupVisible(R.id.nav_tour_management, false);
                    }
                    return true;
                }

                if (itemId == R.id.navAddTour) {
                    Intent intent = new Intent(HomePageAdminActivity.this, AddTourActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.navEditTour) {
                    Intent intent = new Intent(HomePageAdminActivity.this, ListTourActivity.class);
                    startActivity(intent);
                    Toast.makeText(HomePageAdminActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navRemoveTour) {
                    Toast.makeText(HomePageAdminActivity.this, "Remove clicked", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navReport) {
                    Toast.makeText(HomePageAdminActivity.this, "Order Management clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomePageAdminActivity.this, OrderManagement.class);
                    startActivity(intent);
                } else if (itemId == R.id.nav_user_management) {
                    Toast.makeText(HomePageAdminActivity.this, "User Management clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomePageAdminActivity.this, UserManagementActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.navLogout) {
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(HomePageAdminActivity.this, LoginPage.class);
                    startActivity(intent);
                }
                drawerLayout.close();


                return false;
            }
        });
        cardManageTours = findViewById(R.id.cardManageToursList);
        cardManageTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdminActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        });
        cardManageAddTours = findViewById(R.id.cardManageAddTours);
        cardManageAddTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdminActivity.this, AddTourActivity.class);
                startActivity(intent);
            }
        });
        cardManagerOrder = findViewById(R.id.cardManageOrder);
        cardManagerOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdminActivity.this, OrderManagement.class);
                startActivity(intent);
            }
        });
        cardManageUser = findViewById(R.id.cardManageUsers);
        cardManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdminActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });

        cardManagerLogout = findViewById(R.id.logOut);
        cardManagerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdminActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayoutAdmin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_items, menu);
        return true;
    }
}