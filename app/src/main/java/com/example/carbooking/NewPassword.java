package com.example.carbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.User;
import com.example.carbooking.repository.UserRepository;

public class NewPassword extends AppCompatActivity {

    private EditText newpass, confirm;
    private Button changepass;
    private TextView login;
    private UserRepository repo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        newpass = findViewById(R.id.newPass);
        confirm = findViewById(R.id.comfirmnewPass);
        changepass = findViewById(R.id.changepassBtn);
        login = findViewById(R.id.btn_login_back_changepass);
        repo = new UserRepository(this);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpassStr = newpass.getText().toString();
                String confirmStr = confirm.getText().toString();
                if (newpassStr.isEmpty() || confirmStr.isEmpty()) {
                    Toast.makeText(NewPassword.this, "Data Cannot be Empty. \nData can be Exhausted.", Toast.LENGTH_LONG).show();
                } else {
                    if (newpassStr.equals(confirmStr)) {
                        if (isValid(newpassStr)) {
                            // Lấy Intent đã khởi tạo NewPassword
                            Intent intent = getIntent();
                            // Lấy dữ liệu từ Intent
                            String username = intent.getStringExtra("username");
                            User user = repo.getUserByUsername(username);
                            user.setPassword(newpassStr);
                            repo.updateUser(user);
                            Toast.makeText(NewPassword.this, "Update successfully", Toast.LENGTH_LONG).show();
                            Intent returnLogin = new Intent(NewPassword.this, LoginPage.class);
                            startActivity(returnLogin);
                        } else {
                            Toast.makeText(getApplicationContext(), "Pass must contain at least 8 chars, having letter, digit", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(NewPassword.this, "Password doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPassword.this, LoginPage.class);
                startActivity(intent);
            }
        });
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