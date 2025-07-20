package com.example.carbooking.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.R;
import com.example.carbooking.repository.VehicleRepository;

public class EditVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_vehicle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edtVehicleName = findViewById(R.id.edt_vehicle_name);
        Button btnSave = findViewById(R.id.btn_saveVehicle);
        ImageButton btnBack = findViewById(R.id.back_homepage_admin);
        int vehicleId = getIntent().getIntExtra("vehicleId", -1);
        VehicleRepository repo = new VehicleRepository(this);
        Vehicle vehicle = repo.getVehicle(vehicleId);
        if (vehicle != null) {
            edtVehicleName.setText(vehicle.getVehicleName());
        }
        btnSave.setOnClickListener(v -> {
            String newName = edtVehicleName.getText().toString();
            if (newName.trim().isEmpty()) {
                edtVehicleName.setError("Vehicle Name is required");
            } else {
                vehicle.setVehicleName(newName);
                repo.updateVehicle(vehicle);
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        btnBack.setOnClickListener(v -> finish());
    }
}