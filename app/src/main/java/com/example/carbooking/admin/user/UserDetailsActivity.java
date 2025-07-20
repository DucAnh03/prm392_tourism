package com.example.carbooking.admin.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbooking.R;
import com.example.carbooking.repository.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

public class UserDetailsActivity extends AppCompatActivity {
    TextInputLayout inpEmail, inpPhone, inpUser, inpAddress, inpRole;
    Button btnReset;
    UserRepository repo = null;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        inpEmail = findViewById(R.id.UserManagement_details_email);
        inpPhone = findViewById(R.id.UserManagement_details_phone);
        inpUser = findViewById(R.id.UserManagement_details_username);
        inpAddress = findViewById(R.id.UserManagement_details_address);
        inpRole = findViewById(R.id.UserManagement_details_role);
        image = findViewById(R.id.UserManagement_details_image);
        btnReset = findViewById(R.id.UserManagement_details_btn_back);
        Intent intent = getIntent();
        String emailView = intent.getStringExtra("email");
        String phoneView = intent.getStringExtra("phone");
        String userView = intent.getStringExtra("username");
        String addressView = intent.getStringExtra("address");
        int role = intent.getIntExtra("role", 0);
        String roleView = "";
        if (role == 1) {
            roleView = "user";
        } else {
            roleView = "admin";
        }
        inpEmail.getEditText().setText(emailView);
        inpPhone.getEditText().setText(phoneView);
        inpUser.getEditText().setText(userView);
        inpAddress.getEditText().setText(addressView);
        inpRole.getEditText().setText(roleView);
        String imageUriString = intent.getStringExtra("image");
        if (imageUriString == null) {
            image.setImageResource(R.drawable.ic_user);
        } else {
            if (imageUriString.isEmpty()) {
                image.setImageResource(R.drawable.ic_user);
            } else {
                image.setImageURI(Uri.parse(imageUriString));
            }
        }
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}