package com.example.carbooking.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.R;
import com.example.carbooking.repository.VehicleRepository;
import java.util.List;

public class ListVehicleActivity extends AppCompatActivity {

    private VehicleListAdapter adapter;
    private static final int REQUEST_EDIT_VEHICLE = 1001;
    private VehicleRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_vehicle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.vehicle_list_recycler_view);
        Button btnBack = findViewById(R.id.admin_list_vehicle_back);
        Button btnAdd = findViewById(R.id.btn_add_vehicle);
        repo = new VehicleRepository(this);
        List<Vehicle> vehicleList = repo.getAllVehicle();
        adapter = new VehicleListAdapter(vehicleList, new VehicleListAdapter.OnVehicleActionListener() {
            @Override
            public void onEdit(Vehicle vehicle) {
                Intent intent = new Intent(ListVehicleActivity.this, EditVehicleActivity.class);
                intent.putExtra("vehicleId", vehicle.getId());
                startActivityForResult(intent, REQUEST_EDIT_VEHICLE);
            }
            @Override
            public void onDelete(Vehicle vehicle) {
                repo.deleteVehicle(vehicle);
                List<Vehicle> updatedList = repo.getAllVehicle();
                adapter.setVehicleList(updatedList);
                Toast.makeText(ListVehicleActivity.this, "Deleted: " + vehicle.getVehicleName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ListVehicleActivity.this, AddVehicleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_VEHICLE && resultCode == RESULT_OK) {
            List<Vehicle> updatedList = repo.getAllVehicle();
            adapter.setVehicleList(updatedList);
        }
    }
}