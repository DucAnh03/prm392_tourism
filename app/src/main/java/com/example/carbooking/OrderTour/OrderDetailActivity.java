package com.example.carbooking.OrderTour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.R;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.repository.TourRepository;

import java.text.SimpleDateFormat;
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
        long orderDayMillis = intent.getLongExtra("orderDay", -1);
        long departDayMillis = intent.getLongExtra("departDay", -1);
        long endDayMillis = intent.getLongExtra("endDay", -1);
        Date orderDay = orderDayMillis != -1 ? new Date(orderDayMillis) : null;
        Date departDay = departDayMillis != -1 ? new Date(departDayMillis) : null;
        Date endDay = endDayMillis != -1 ? new Date(endDayMillis) : null;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        TextView orderDayy = findViewById(R.id.textView_OrderDay);
        orderDayy.setText(orderDay != null ? sdf.format(orderDay) : "N/A");
        TextView departDayy = findViewById(R.id.textView_DeparDay);
        departDayy.setText(departDay != null ? sdf.format(departDay) : "N/A");
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
        Button btnCancel = findViewById(R.id.btn_cancel_order);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date orderDate = order.getOrderDate();
                Date departDate = order.getDepartureDay();
                if (orderDate.before(departDate)) {
                    orderRepository.deleteOrderById(order.getId());
                    Toast.makeText(OrderDetailActivity.this, "Order cancelled successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderDetailActivity.this, ListOrder.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OrderDetailActivity.this, "Cannot cancel: Order date is not before departure date!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Disable vote nếu đã vote hoặc chưa đến ngày về
        Date now = new Date();
        if (order.isVoted() || now.compareTo(order.getEndDate()) <= 0) {
            edtVote.setEnabled(false);
            btnVote.setEnabled(false);
            if (order.isVoted()) {
                edtVote.setText("Đã vote");
            } else {
                edtVote.setText("finish tour");
            }
        }
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();
                if (order.isVoted()) {
                    Toast.makeText(OrderDetailActivity.this, "Bạn đã vote cho order này rồi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (now.compareTo(order.getEndDate()) <= 0) {
                    Toast.makeText(OrderDetailActivity.this, "Chỉ được vote sau khi kết thúc tour!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String voteInput = edtVote.getText().toString().trim();
                int voteValue;
                try {
                    voteValue = Integer.parseInt(voteInput);
                } catch (NumberFormatException e) {
                    Toast.makeText(OrderDetailActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (voteValue >= 0 && voteValue <= 5) {
                    boolean updateSuccess = tourRepository.updateTourVote(tourIdd, voteValue);
                    if (updateSuccess) {
                        order.setVoted(true);
                        orderRepository.updateOrder(order);
                        edtVote.setEnabled(false);
                        btnVote.setEnabled(false);
                        edtVote.setText("Đã vote");
                        Toast.makeText(OrderDetailActivity.this, "Vote thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "Vote thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OrderDetailActivity.this, "Giá trị vote phải từ 0 đến 5!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView endDayy = findViewById(R.id.textView_EndDay);
        endDayy.setText(endDay != null ? sdf.format(endDay) : "N/A");
    }
}
