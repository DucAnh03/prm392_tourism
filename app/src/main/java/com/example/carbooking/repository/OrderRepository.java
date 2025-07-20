package com.example.carbooking.repository;

import android.content.Context;

import com.example.carbooking.Entity.Order;
import com.example.carbooking.dao.PRM392RoomDatabase;
import com.example.carbooking.dao.OrderDao;

import java.util.List;

public class OrderRepository {
    private OrderDao orderDao;

    public OrderRepository(Context context) {
        orderDao = PRM392RoomDatabase.getInstance(context).orderDao();
    }

    public void createOrder(Order order) {
        orderDao.insert(order);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public Order getOrder(int orderId) {
        return orderDao.select(orderId);
    }

    public List<Order> getAllOrder() {
        return orderDao.selectAll();
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDao.getOrdersByUserId(userId);
    }

}
