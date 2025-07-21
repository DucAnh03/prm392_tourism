package com.example.carbooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbooking.Entity.User;
import com.example.carbooking.repository.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SignUp extends AppCompatActivity {
    TextView txtSignIn;
    TextView txtTitle;
    TextInputLayout inpEmail, inpPhone, inpUser, inpPass, inpRePass, inpAddress;
    Button btnSignUp, btnReset;
    private UserRepository repo = null;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtTitle = findViewById(R.id.tv_title_regis);
        inpEmail = findViewById(R.id.email);
        inpPhone = findViewById(R.id.phone);
        inpUser = findViewById(R.id.username_regis);
        inpPass = findViewById(R.id.password_regis);
        inpRePass = findViewById(R.id.retype_password);
        btnSignUp = findViewById(R.id.btn_signUp);
        txtSignIn = findViewById(R.id.btn_signIn);
        btnReset = findViewById(R.id.btn_reset);
        inpAddress = findViewById(R.id.address);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        repo = new UserRepository(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = inpEmail.getEditText().getText().toString();
                String phoneValue = inpPhone.getEditText().getText().toString();
                String userValue = inpUser.getEditText().getText().toString();
                String passValue = inpPass.getEditText().getText().toString();
                String repassValue = inpRePass.getEditText().getText().toString();
                String address = inpAddress.getEditText().getText().toString();
                if (
                        emailValue.equals("") ||
                                phoneValue.equals("") ||
                                userValue.equals("") ||
                                passValue.equals("") ||
                                repassValue.equals("") ||
                                address.equals("")) {
                    Toast.makeText(SignUp.this, "Data Cannot be Empty. \nData can be Exhausted.", Toast.LENGTH_LONG).show();
                } else {
                    if (passValue.equals(repassValue)) {
                        if (isValid(passValue)) {
                            List<User> list = repo.getAllUser();
                            if (!list.isEmpty()) {
                                for (User item : list) {
                                    if (item.getUserName().equals(userValue)) {
                                        Toast.makeText(SignUp.this, "Username already exists\n!.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            }
                            User user = new User();
                            user.setUserName(userValue);
                            user.setPassword(passValue);
                            user.setAddress(address);
                            user.setAvatar("");
                            user.setLocked(false);
                            user.setPhoneNumber(phoneValue);
                            user.setEmail(emailValue);
                            user.setRole_id(1);
                            repo.createUser(user);
                            Toast.makeText(SignUp.this, "Successful Registration", Toast.LENGTH_LONG).show();
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("userId", user.getId());
                            editor.apply();
                            Intent intent = new Intent(SignUp.this, LoginPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Pass must contain at least 8 chars, having letter, digit", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    public void reset() {
        inpEmail.getEditText().setText(null);
        inpPhone.getEditText().setText(null);
        inpUser.getEditText().setText(null);
        inpPass.getEditText().setText(null);
        inpRePass.getEditText().setText(null);
    }

    public static boolean isValid(String passwordhere) {

        int f1 = 0, f2 = 0;

        if (passwordhere.length() < 8) {
            return false;

        } else {

            for (int p = 0; p < passwordhere.length(); p++) {

                if (Character.isLetter(passwordhere.charAt(p))) {
                    f1 = 1;
                }

            }

            for (int r = 0; r < passwordhere.length(); r++) {

                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }

            }


            if (f1 == 1 && f2 == 1)
                return true;

        }
        return false;
    }
}