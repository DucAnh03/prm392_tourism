package com.example.carbooking.OrderTour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.R;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.repository.TourRepository;

import java.util.Date;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderRepository orderRepository;
    private Order order;
    private TourRepository tourRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        tourRepository = new TourRepository(this);
        EditText edtVote = findViewById(R.id.edt_vote);
        Button btnVote = findViewById(R.id.btn_vote);


        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        int tourIdd = intent.getIntExtra("tourId", -1);
        String tourName = intent.getStringExtra("tourName");
        double fee = intent.getDoubleExtra("fee", 1);
        int statusId = intent.getIntExtra("statusId", 1);
        String userName = intent.getStringExtra("userName");
        int numPer = intent.getIntExtra("numPer", 1);
        Date orderDay = (Date) intent.getSerializableExtra("orderDay");
        int departDay = intent.getIntExtra("departDay", 1);
        String image = intent.getStringExtra("image");
        // Xử lý lấy thông tin chi tiết của order từ orderId
        orderRepository = new OrderRepository(this);
        order = orderRepository.getOrder(orderId);


        TextView tourNamee = findViewById(R.id.textview_tourname_detail);
        tourNamee.setText(tourName);
        TextView feee = findViewById(R.id.textview_TotalFee);
        feee.setText("Total fee: " + String.valueOf(order.getTotalFee()));
        TextView status = findViewById(R.id.textview_Status);
        status.setText("Status: " + (statusId == 1 ? "Completed" : "Pending"));
        TextView userNamee = findViewById(R.id.textView_UserName);
        userNamee.setText(userName);
        TextView numPerr = findViewById(R.id.textView_NumPer);
        numPerr.setText(String.valueOf(numPer));
        TextView orderDayy = findViewById(R.id.textView_OrderDay);
        orderDayy.setText(String.valueOf(orderDay));
        TextView departDayy = findViewById(R.id.textView_DeparDay);
        departDayy.setText(String.valueOf(departDay));
        ImageView imageView = findViewById(R.id.img_tour_detail);
        Glide.with(this)
                .load(image)
                .error(R.drawable.placeholder)
                .into(imageView);
        Button back = findViewById(R.id.btn_backtolistorder);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, ListOrder.class);
                startActivity(intent);
            }
        });
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteInput = edtVote.getText().toString().trim();
                if (!voteInput.isEmpty()) {
                    int voteValue = Integer.parseInt(voteInput);
                    if (voteValue >= 0 && voteValue <= 5) { // Assuming vote range
                        // Update Tour voteNumber and voteScore
                        boolean updateSuccess = tourRepository.updateTourVote(tourIdd, voteValue);
                        if (updateSuccess) {
                            Toast.makeText(OrderDetailActivity.this, "Vote updated successfully", Toast.LENGTH_SHORT).show();
                            // Optionally update UI or navigate back
                        } else {
                            Toast.makeText(OrderDetailActivity.this, "Failed to update vote", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "Vote value must be between 0 and 5", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OrderDetailActivity.this, "Please enter a vote value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
