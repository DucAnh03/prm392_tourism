package com.example.carbooking.admin.tour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Tour;
import com.example.carbooking.R;
import com.example.carbooking.adapter.TourListAdapter;
import com.example.carbooking.admin.HomePageAdminActivity;
import com.example.carbooking.repository.TourRepository;

import java.util.List;

public class ListTourActivity extends AppCompatActivity {
    private TourRepository tourRepository = null;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_tour);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tourRepository = new TourRepository(this);
        RecyclerView recyclerView = findViewById(R.id.tour_list_recyle_view);
        List<Tour> tourList = tourRepository.getAllTour();
        TourListAdapter.OnItemClickListener onItemClickListener = new TourListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Tour clickedTour = tourList.get(position);

                // Navigate to EditTourActivity and pass tour ID
                Intent intent = new Intent(ListTourActivity.this, EditTourActivity.class);
                intent.putExtra("tourId", clickedTour.getId());
                startActivity(intent);
                Toast.makeText(ListTourActivity.this, "Edit " + tourList.get(position).getTile(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoveClick(int position) {
                Tour tourBan = tourList.get(position);
                boolean banTour = !tourBan.isAvaliable();
                int id = tourBan.getId();
                tourBan = new Tour(id, tourBan.getTile(), tourBan.getLocationFrom(), tourBan.getLocationTo(), tourBan.getTourTime(), tourBan.getDateNumber(),
                        tourBan.getDescription(), tourBan.getTourSchdule(), tourBan.getPricePerPerson(), tourBan.getVehicle(), tourBan.getCategoryId(),
                        tourBan.getVotedNumber(), tourBan.getVoteScore(), banTour, tourBan.getContactNumber(), tourBan.getImage());
                tourRepository.updateTour(tourBan);
                Toast.makeText(ListTourActivity.this, "Remove success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListTourActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        };

        TourListAdapter tourListAdapter = new TourListAdapter(tourList, this, onItemClickListener);
        recyclerView.setAdapter(tourListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back = findViewById(R.id.admin_list_tour_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTourActivity.this, HomePageAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}