package com.example.carbooking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.Entity.User;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.UserRepository;
import com.example.carbooking.user.HomeActivity;
import com.example.carbooking.user.TourDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TourBooking extends AppCompatActivity {
    private TourRepository tourRepository = null;
    private UserRepository userRepository = null;
    private OrderRepository orderRepository = null;
    Button addCount, subCount, btnbooKing, btnSelectDate, btnHome;
    int mCount=1;
    TextView txtCount, tvTitle, tvLocationFrom, tvLocationTo, tvTourTime, tvDescription,
            tvTourNumber, tvPricePerPerson, tvVoteScore, tvVoteNumber, tvContactNumber, priceTour, tvStartDate, tvEndDate;
    ImageView imgTour;
    double basePrice = 0.0;
    int dateNumber, userId, tourId;
    SharedPreferences preferences;
    Tour tourList;
    String imageUriString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tourRepository = new TourRepository(this);
        userRepository = new UserRepository(this);
        orderRepository = new OrderRepository(this);
        // Nhận tour ID từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            tourId =  intent.getIntExtra(TourDetailActivity.KEY_TOUR_ID, -1);
        }

        tourList = tourRepository.getTour(tourId);
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int id = preferences.getInt("userId", 0);
        User user = userRepository.getUserById(id);
        userId = user.getId();


        txtCount = findViewById(R.id.txt_count);
        addCount = findViewById(R.id.btn_addCount);
        subCount = findViewById(R.id.btn_subCount);
        tvTitle = findViewById(R.id.tv_Tittle);
        tvLocationFrom = findViewById(R.id.tv_startLocation);
        tvLocationTo = findViewById(R.id.tv_endLocation);
        tvTourTime = findViewById(R.id.tv_tourTime);
        tvDescription = findViewById(R.id.tv_description);
        tvPricePerPerson = findViewById(R.id.tv_price);
        tvVoteScore = findViewById(R.id.tv_voteScore);
        tvVoteNumber = findViewById(R.id.tv_voteNumber);
//        tvContactNumber = findViewById(R.id.tv_contact);
        imgTour = findViewById(R.id.img_tour);
        priceTour = findViewById(R.id.price_tour);
        tvStartDate = findViewById(R.id.tv_startDate);
        tvEndDate = findViewById(R.id.tv_endDate);
        btnSelectDate = findViewById(R.id.btn_selectDate);
        btnbooKing = findViewById(R.id.btn_confirm);
        btnHome = findViewById(R.id.btn_home);

        if (tourList != null) {


            Glide.with(this)
                    .load(tourList.getImage())
                    .error(R.drawable.placeholder)
                    .into(imgTour);

            basePrice = tourList.getPricePerPerson();
            dateNumber = tourList.getDateNumber();

            tvTitle.setText(tourList.getTile());
            tvLocationFrom.setText(tourList.getLocationFrom());
            tvLocationTo.setText(" - " + tourList.getLocationTo());
            tvTourTime.setText(tourList.getTourTime());

            tvDescription.setText(tourList.getDescription());

            tvPricePerPerson.setText(String.format("$%.0f", tourList.getPricePerPerson()));
            tvVoteScore.setText(String.format("%d*", tourList.getVoteScore()));
            tvVoteNumber.setText(String.format("(%d)", tourList.getVotedNumber()));
//            tvContactNumber.setText(tourList.getContactNumber());
            priceTour.setText("0");


            // Example: Glide.with(this).load(firstTour.getImage()).into(imgTour);
        }


        txtCount.setText(Integer.toString(mCount));

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourBooking.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                txtCount.setText(Integer.toString(mCount));
                priceTour.setText(String.format("$%.2f", basePrice * mCount));
            }
        });

        subCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount > 1) {
                    mCount--;
                    txtCount.setText(Integer.toString(mCount));
                    priceTour.setText(String.format("$%.2f", basePrice * mCount));
                }
            }
        });

        btnbooKing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TourBooking.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDates(calendar);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDates(Calendar startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        tvStartDate.setText(sdf.format(startDate.getTime()));
        startDate.add(Calendar.DAY_OF_MONTH, dateNumber);
        tvEndDate.setText(sdf.format(startDate.getTime()));
    }

    private void createOrder() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String startDateStr = tvStartDate.getText().toString();
            String endDateStr = tvEndDate.getText().toString();

            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(this, "Please select a date!", Toast.LENGTH_SHORT).show();
                return;
            }


            Date startDate = sdf.parse(tvStartDate.getText().toString());
            Date endDate = sdf.parse(tvEndDate.getText().toString());
            Date orderDate = new Date();

            Order order = new Order();
            order.setTourId(tourList.getId());
            order.setUserId(userId);
            order.setNumberOfPerson(mCount);
            order.setDepartureDay(startDate);
            order.setTotalFee(basePrice * mCount);
            order.setStatusId(1);
            order.setOrderDate(orderDate);
            order.setEndDate(endDate);

            orderRepository.createOrder(order);

            Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create order!", Toast.LENGTH_SHORT).show();
        }
    }
}