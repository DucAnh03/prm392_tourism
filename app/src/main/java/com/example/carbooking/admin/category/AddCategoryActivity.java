package com.example.carbooking.admin.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.R;
import com.example.carbooking.admin.HomePageAdminActivity;
import com.example.carbooking.repository.CategoryRepository;

public class AddCategoryActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    Button btnSave;
    private CategoryRepository categoryRepository = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        categoryRepository = new CategoryRepository(this);
        edtCategoryName = findViewById(R.id.edt_category_name);
        btnSave = findViewById(R.id.btn_addCategory);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = edtCategoryName.getText().toString();
                if(categoryName.trim().isEmpty()){
                     edtCategoryName.setError("Category Name is required");
                }else{
                    Category category = new Category(categoryName);
                    categoryRepository.createCategory(category);
                    Toast.makeText(AddCategoryActivity.this, "Add category successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCategoryActivity.this, HomePageAdminActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}