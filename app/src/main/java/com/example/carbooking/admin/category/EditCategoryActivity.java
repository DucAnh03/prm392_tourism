package com.example.carbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.R;
import com.example.carbooking.repository.CategoryRepository;

public class EditCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edtCategoryName = findViewById(R.id.edt_category_name);
        Button btnSave = findViewById(R.id.btn_saveCategory);
        ImageButton btnBack = findViewById(R.id.back_homepage_admin);
        int categoryId = getIntent().getIntExtra("categoryId", -1);
        CategoryRepository repo = new CategoryRepository(this);
        Category category = repo.getCategory(categoryId);
        if (category != null) {
            edtCategoryName.setText(category.getCategoryName());
        } else {
            Toast.makeText(this, "Category not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        btnSave.setOnClickListener(v -> {
            String newName = edtCategoryName.getText().toString();
            if (newName.trim().isEmpty()) {
                edtCategoryName.setError("Category Name is required");
            } else {
                category.setCategoryName(newName);
                repo.updateCategory(category);
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        btnBack.setOnClickListener(v -> finish());
    }
}