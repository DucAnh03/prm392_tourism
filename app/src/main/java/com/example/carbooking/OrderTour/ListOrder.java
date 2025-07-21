package com.example.carbooking.OrderTour;// ListOrder.java

import static com.example.carbooking.R.layout.activity_tour_histories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Order;
import com.example.carbooking.R;
import com.example.carbooking.adapter.OrderAdapter;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.user.HomeActivity;

import java.util.List;

public class ListOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_tour_histories);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRepository = new OrderRepository(getApplication());
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);


        List<Order> orderList = orderRepository.getOrdersByUserId(userId);

        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        Button back = findViewById(R.id.btn_backtohome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOrder.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
