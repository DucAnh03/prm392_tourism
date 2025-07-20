package com.example.carbooking.repository;

import android.content.Context;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.dao.CategoryDao;
import com.example.carbooking.dao.PRM392RoomDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    public CategoryRepository(Context context){
        categoryDao = PRM392RoomDatabase.getInstance(context).categoryDao();
    }
    public void createCategory(Category category){
        categoryDao.insert(category);
    }

    public void updateCategory(Category category){
        categoryDao.update(category);
    }

    public Category getCategory(int categoryId){
        return categoryDao.select(categoryId);
    }

    public List<Category> getAllCategory(){
        return  categoryDao.selectAll();
    }

    public void deleteById(int categoryId){ categoryDao.delete(categoryId);}
}
