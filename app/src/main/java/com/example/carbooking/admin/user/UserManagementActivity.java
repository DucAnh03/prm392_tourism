package com.example.carbooking.admin.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.User;
import com.example.carbooking.R;
import com.example.carbooking.adapter.UserListAdapter;
import com.example.carbooking.admin.HomePageAdminActivity;
import com.example.carbooking.repository.UserRepository;

import java.util.List;

public class UserManagementActivity extends AppCompatActivity {
    private UserRepository userRepository = null;
    private Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        back = findViewById(R.id.userList_back_to_adminHome);
        userRepository = new UserRepository(this);
        RecyclerView recyclerView = findViewById(R.id.user_list_recyle_view);
        List<User> list = userRepository.getAllUser();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, HomePageAdminActivity.class);
                startActivity(intent);
            }
        });
        UserListAdapter.OnItemClickListener onItemClickListener = new UserListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                User userCLick = list.get(position);
                if (userCLick.getRole_id() == 2) {
                    Toast.makeText(UserManagementActivity.this, "Admin account cannot be banned", Toast.LENGTH_SHORT).show();
                } else {
                    userCLick.setLocked(!userCLick.isLocked());
                    userRepository.updateUser(userCLick);
                    Toast.makeText(UserManagementActivity.this, "Change status successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserManagementActivity.this, UserManagementActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onRemoveClick(int position) {
                User userCLick = list.get(position);
                if (userCLick.getRole_id() == 2) {
                    Toast.makeText(UserManagementActivity.this, "Admin account cannot be removed", Toast.LENGTH_SHORT).show();
                } else {
                    userRepository.deleteUser(userCLick);
                    Toast.makeText(UserManagementActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserManagementActivity.this, UserManagementActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemClick(int position) {
                User userCLick = list.get(position);
                Intent it = new Intent(UserManagementActivity.this, UserDetailsActivity.class);
                it.putExtra("username", userCLick.getUserName());
                it.putExtra("email", userCLick.getEmail());
                it.putExtra("phone", userCLick.getPhoneNumber());
                it.putExtra("address", userCLick.getAddress());
                it.putExtra("role", userCLick.getRole_id());
                it.putExtra("image", userCLick.getAvatar());
                startActivity(it);
            }
        };
        UserListAdapter userListAdapter = new UserListAdapter(list, this, onItemClickListener);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}