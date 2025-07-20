package com.example.carbooking.admin.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.Entity.User;
import com.example.carbooking.R;
import com.example.carbooking.admin.user.UserDetailsActivity;
import com.example.carbooking.admin.user.UserManagementActivity;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.UserRepository;

import java.util.Date;

public class OrderDetail extends AppCompatActivity {

    private OrderRepository orderRepository;
    private Order order;
    private TourRepository tourRepository;
    private UserRepository userRepository;
    private int tourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_detail);

        tourRepository = new TourRepository(this);
        userRepository = new UserRepository(this);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        int tourId = intent.getIntExtra("tourId", -1);
        double fee = intent.getDoubleExtra("fee", 1);
        int statusId = intent.getIntExtra("statusId", 1);
        int userId = intent.getIntExtra("userId", 1);
        int numPer = intent.getIntExtra("numPer", 1);
        String orderDay = intent.getStringExtra("orderDay");
        String departDay = intent.getStringExtra("departDay");
        String endDay = intent.getStringExtra("endDay");
        Tour tour = tourRepository.getTour(tourId);
        // Xử lý lấy thông tin chi tiết của order từ orderId
        orderRepository = new OrderRepository(this);
        order = orderRepository.getOrder(orderId);

        User user = userRepository.getUserById(userId);

        TextView tourName = findViewById(R.id.admin_name_detail);
        tourName.setText(tour.getTile());
        TextView tv_fee = findViewById(R.id.admin_detail_TotalFee);
        tv_fee.setText("Total fee: " + String.valueOf(order.getTotalFee()));
        TextView status = findViewById(R.id.admin_detail_Status);
        status.setText("Status: " + (statusId == 1 ? "Completed" : "Pending"));
        TextView userName = findViewById(R.id.admin_detail_UserName);
        userName.setText(user.getUserName());
        TextView numPerr = findViewById(R.id.admin_detail_NumPer);
        numPerr.setText(String.valueOf(numPer));
        TextView orderDayy = findViewById(R.id.admin_detail_OrderDay);
        orderDayy.setText(orderDay);
        TextView departDayy = findViewById(R.id.admin_detail_DepartureDay);
        TextView tv_endDay = findViewById(R.id.admin_detail_EndDay);
        tv_endDay.setText(endDay);
        departDayy.setText(departDay);



        ImageView imageView = findViewById(R.id.admin_img_tour_detail);
        Glide.with(this)
                .load(tour.getImage())
                .error(R.drawable.placeholder)
                .into(imageView);
        Button back = findViewById(R.id.btn_backtolistorder);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetail.this, OrderManagement.class);
                startActivity(intent);
            }
        });
    }
}
