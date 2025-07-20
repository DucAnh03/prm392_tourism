package com.example.carbooking.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.R;
import com.example.carbooking.TourBooking;
import com.example.carbooking.databinding.ActivityTourDetailBinding;
import com.example.carbooking.helpler.FormatUtils;
import com.example.carbooking.repository.CategoryRepository;
import com.example.carbooking.repository.VehicleRepository;

public class TourDetailActivity extends AppCompatActivity {
    public static final String KEY_TOUR = "tour";

    private ActivityTourDetailBinding binding;

    Button btnBooking;
    public static final String KEY_TOUR_ID = "tour_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        populateData();

        Tour tour = (Tour) getIntent().getSerializableExtra(KEY_TOUR);
        if (tour == null) return;

        btnBooking = findViewById(R.id.btn_booking);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourDetailActivity.this, TourBooking.class);
                intent.putExtra(KEY_TOUR_ID, tour.getId()); // Truyền tour ID
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void populateData() {
        try {
            Tour tour = (Tour) getIntent().getSerializableExtra(KEY_TOUR);
            if (tour == null) return;

            // Bind data
            Glide.with(this)
                    .load(tour.getImage())
                    .error(R.drawable.placeholder)
                    .into(binding.imageTourDetail);
            binding.textTitle.setText(tour.getTile());
            binding.textPrice.setText(FormatUtils.formatCurrency(tour.getPricePerPerson()) + "đ/");
            binding.textFrom.setText(tour.getLocationFrom());
            binding.textTo.setText(tour.getLocationTo());
            binding.textTourTime.setText(tour.getTourTime());
            binding.textTourDateNumber.setText(tour.getDateNumber() + "");
            binding.textTourSchedule.setText(tour.getTourSchdule());
            binding.textContact.setText(tour.getContactNumber());
            binding.textDescription.setText(tour.getDescription());

            // Bind category and vehicle
            String categoryName = new CategoryRepository(this).getCategory(tour.getCategoryId()).getCategoryName();
            String vehicleName = new VehicleRepository(this).getVehicle(tour.getVehicle()).getVehicleName();
            binding.textCategory.setText(categoryName);
            binding.textVehicle.setText(vehicleName);

            // Set listeners
            binding.iconBack.setOnClickListener(v -> finish());
            binding.iconShare.setOnClickListener(v -> {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, tour.getTile());
                intent.putExtra(android.content.Intent.EXTRA_TEXT, tour.getTile());
                startActivity(Intent.createChooser(intent, "Share Tour to Your Friends"));
            });
            binding.iconFavorite.setOnClickListener(v -> {
                binding.iconFavorite.setImageResource(R.drawable.icon_favorite);
                binding.iconFavorite.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.btnResetColor)));
            });
            binding.textReadMore.setOnClickListener(v -> {
                binding.textDescription.setMaxLines(100);
                v.setVisibility(View.GONE);
            });
        } catch (Exception e) {
            Log.d("TAG", "[TourDetailActivity] Error while populating data: " + e.getMessage());
        }
    }
}