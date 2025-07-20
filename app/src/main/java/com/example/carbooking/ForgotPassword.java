package com.example.carbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.User;
import com.example.carbooking.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {

    private boolean otpSent = false;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final EditText usernameEt = findViewById(R.id.usernameEt);
        final EditText mobileEt = findViewById(R.id.mobileEt);
        final EditText otpEt = findViewById(R.id.otpEt);
        final Button actionBtn = findViewById(R.id.actionBtn);
        final TextView txtLogin = findViewById(R.id.btn_login_back);
        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserRepository repo = new UserRepository(this);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginPage.class);
                startActivity(intent);
            }
        });
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getMobile = mobileEt.getText().toString();
                final String getUsername = usernameEt.getText().toString();
                //list.stream().anyMatch();
                if (otpSent) {
                    final String getOtp = otpEt.getText().toString();
                    if (id.isEmpty()) {
                        Toast.makeText(ForgotPassword.this, "Unable to verify OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(id, getOtp);
                        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser userDetails = task.getResult().getUser();
                                    Toast.makeText(ForgotPassword.this, "Verified", Toast.LENGTH_SHORT).show();
                                    // Tạo một Intent để chuyển sang NewPassword
                                    Intent intent = new Intent(ForgotPassword.this,NewPassword.class);
                                    // Đặt dữ liệu vào Intent
                                    intent.putExtra("username",getUsername);
                                    // Bắt đầu SecondActivity
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    if (getMobile.equals("") || getUsername.equals("")) {
                        Toast.makeText(ForgotPassword.this, "Please fill all data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<User> list = repo.getAllUser();
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            if ((list.get(i).getUserName().equals(getUsername)) && (list.get(i).getPhoneNumber().equals(getMobile))) {
                                break;
                            } else {
                                if (i == (list.size() - 1)) {
                                    Toast.makeText(ForgotPassword.this, "Incorrect Username or Phone", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    } else {
                        Toast.makeText(ForgotPassword.this, "Have no user element", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber("+84" + getMobile)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(ForgotPassword.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(ForgotPassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(ForgotPassword.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    otpEt.setVisibility(View.VISIBLE);
                                    actionBtn.setText("Verify OTP");
                                    id = s;
                                    otpSent = true;
                                }
                            }).build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });
    }
}