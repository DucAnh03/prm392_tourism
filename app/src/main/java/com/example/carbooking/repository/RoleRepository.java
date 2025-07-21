package com.example.carbooking.repository;

import android.content.Context;

import com.example.carbooking.Entity.Role;
import com.example.carbooking.dao.PRM392RoomDatabase;
import com.example.carbooking.dao.RoleDao;

import java.util.List;

public class RoleRepository {
    private RoleDao roleDao;
    public  RoleRepository(Context context){
        roleDao = PRM392RoomDatabase.getInstance(context).roleDao();
    }
    public void createRole(Role role){
        roleDao.insert(role);
    }

    public void updateRole(Role role){
        roleDao.update(role);
    }

    public Role getRole(int roleId){
        return  roleDao.select(roleId);
    }

    public List<Role> getAllRole(){
        return  roleDao.selectAll();
    }
}
