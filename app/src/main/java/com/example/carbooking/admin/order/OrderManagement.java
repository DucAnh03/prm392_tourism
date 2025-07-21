package com.example.carbooking.admin.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Order;
import com.example.carbooking.R;
import com.example.carbooking.adapter.AdminOrderAdapter;
import com.example.carbooking.admin.HomePageAdminActivity;
import com.example.carbooking.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminOrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        recyclerView = findViewById(R.id.admin_tour_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderRepository = new OrderRepository(this);
        orderList = orderRepository.getAllOrder();

        orderAdapter = new AdminOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        Button btnBack = findViewById(R.id.btn_admin_backtohome);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(OrderManagement.this, HomePageAdminActivity.class);
            startActivity(intent);
        });

        Button btnConfirm = findViewById(R.id.btn_confirm_order);
        Button btnCancel = findViewById(R.id.btn_cancel_order);

        btnConfirm.setOnClickListener(v -> {
            List<Order> selectedOrders = orderAdapter.getSelectedOrders();
            if (!selectedOrders.isEmpty()) {
                for (Order order : selectedOrders) {
                    if (order.getStatusId() != 1) {
                        order.setStatusId(1); // Đã xác nhận
                        orderRepository.updateOrder(order);
                    }
                }
                Toast.makeText(this, "✔ Đã xác nhận " + selectedOrders.size() + " đơn hàng", Toast.LENGTH_SHORT).show();
                refreshList();
            } else {
                Toast.makeText(this, "Vui lòng chọn ít nhất một đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            List<Order> selectedOrders = orderAdapter.getSelectedOrders();
            if (!selectedOrders.isEmpty()) {
                for (Order order : selectedOrders) {
                    if (order.getStatusId() != 0) {
                        order.setStatusId(0); // Hủy
                        orderRepository.updateOrder(order);
                    }
                }
                Toast.makeText(this, "✖ Đã hủy " + selectedOrders.size() + " đơn hàng", Toast.LENGTH_SHORT).show();
                refreshList();
            } else {
                Toast.makeText(this, "Vui lòng chọn ít nhất một đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        orderList.clear();
        orderList.addAll(orderRepository.getAllOrder());
        orderAdapter.notifyDataSetChanged();
    }
}
