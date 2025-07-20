package com.example.carbooking.admin.category;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.R;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carbooking.Entity.Category;
import com.example.carbooking.repository.CategoryRepository;
import java.util.List;
import android.content.Intent;

public class ListCategoryActivity extends AppCompatActivity {
    private CategoryListAdapter adapter;
    private static final int REQUEST_EDIT_CATEGORY = 1001;
    private CategoryRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.category_list_recycler_view);
        Button btnBack = findViewById(R.id.admin_list_category_back);
        Button btnAdd = findViewById(R.id.btn_add_category);
        repo = new CategoryRepository(this);
        List<Category> categoryList = repo.getAllCategory();
        adapter = new CategoryListAdapter(categoryList, new CategoryListAdapter.OnCategoryActionListener() {
            @Override
            public void onEdit(Category category) {
                Intent intent = new Intent(ListCategoryActivity.this, EditCategoryActivity.class);
                intent.putExtra("categoryId", category.getId());
                startActivityForResult(intent, REQUEST_EDIT_CATEGORY);
            }
            @Override
            public void onDelete(Category category) {
                repo.deleteById(category.getId());
                List<Category> updatedList = repo.getAllCategory();
                adapter.setCategoryList(updatedList);
                Toast.makeText(ListCategoryActivity.this, "Deleted: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ListCategoryActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_CATEGORY && resultCode == RESULT_OK) {
            List<Category> updatedList = repo.getAllCategory();
            adapter.setCategoryList(updatedList);
        }
    }
}