package com.example.carbooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carbooking.Entity.User;
import com.example.carbooking.helpler.SaveImageToStorage;
import com.example.carbooking.repository.UserRepository;
import com.example.carbooking.user.HomeActivity;
import com.google.android.material.textfield.TextInputLayout;

public class EditUser extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    TextInputLayout inpEmail, inpPhone, inpUser, inpAddress, inpRole;
    Button btnUpdate, btnReset, selectImage;
    private SaveImageToStorage saveImageToStorage;
    SharedPreferences preferences;
    UserRepository repo = null;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        saveImageToStorage = new SaveImageToStorage(this);
        inpEmail = findViewById(R.id.email_edit);
        inpPhone = findViewById(R.id.phone_edit);
        inpUser = findViewById(R.id.username_edit);
        inpAddress = findViewById(R.id.address_edit);
        inpRole = findViewById(R.id.role_edit);
        image = findViewById(R.id.image);
        btnUpdate = findViewById(R.id.btn_update);
        btnReset = findViewById(R.id.btn_back);
        selectImage = findViewById(R.id.btn_selectImage);
        repo = new UserRepository(this);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int id = preferences.getInt("userId", 0);
        User user = repo.getUserById(id);
        String emailView = user.getEmail();
        String phoneView = user.getPhoneNumber();
        String userView = user.getUserName();
        String addressView = user.getAddress();
        String roleView = "";
        if (user.getRole_id() == 1) {
            roleView = "user";
        }
        if (emailView != null || phoneView != null || userView != null || addressView != null || roleView != null) {
            inpEmail.getEditText().setText(emailView);
            inpPhone.getEditText().setText(phoneView);
            inpUser.getEditText().setText(userView);
            inpAddress.getEditText().setText(addressView);
            inpRole.getEditText().setText(roleView);
        }
        String imageUriString = user.getAvatar();
        assert imageUriString != null;
        if (imageUriString.isEmpty()) {
            image.setImageResource(R.drawable.ic_user);
        } else {
            image.setImageURI(Uri.parse(imageUriString));
        }
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallrey = new Intent(Intent.ACTION_PICK);
                iGallrey.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallrey, GALLERY_REQ_CODE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = inpEmail.getEditText().getText().toString();
                String phoneValue = inpPhone.getEditText().getText().toString();
                String addressValue = inpAddress.getEditText().getText().toString();
                String imgPath = saveImageToStorage.saveImageFromImageView(image);
                user.setEmail(emailValue);
                user.setPhoneNumber(phoneValue);
                user.setAddress(addressValue);
                user.setAvatar(imgPath);
                repo.updateUser(user);
                Toast.makeText(EditUser.this, "Edit Profile Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditUser.this, EditUser.class);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EditUser.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                image.setImageURI(data.getData());
                System.out.println("data: " + data.getData());
            }
        }
    }
}