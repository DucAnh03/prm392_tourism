package com.example.carbooking.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.R;
import com.example.carbooking.admin.HomePageAdminActivity;
import com.example.carbooking.repository.VehicleRepository;

public class AddVehicleActivity extends AppCompatActivity {
    private EditText edtVehicleName;
    private Button btnSave;
    private VehicleRepository vehicleRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_vehicle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vehicleRepository = new VehicleRepository(this);
        edtVehicleName = findViewById(R.id.edt_vehicle_name);
        btnSave = findViewById(R.id.btn_addVehicle);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleName = edtVehicleName.getText().toString();
                Vehicle vehicle = new Vehicle(vehicleName);
                if(vehicleName.trim().isEmpty()){
                    edtVehicleName.setError("Vehicle name is required");
                }else{
                    vehicleRepository.createVehicle(vehicle);
                    Toast.makeText(AddVehicleActivity.this, "Add vehicle success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddVehicleActivity.this, HomePageAdminActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}