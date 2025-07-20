package com.example.carbooking.admin.vehicle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.R;

import java.util.List;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.VehicleViewHolder> {
    public interface OnVehicleActionListener {
        void onEdit(Vehicle vehicle);
        void onDelete(Vehicle vehicle);
    }

    private List<Vehicle> vehicleList;
    private final OnVehicleActionListener listener;

    public VehicleListAdapter(List<Vehicle> vehicleList, OnVehicleActionListener listener) {
        this.vehicleList = vehicleList;
        this.listener = listener;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.tvVehicleName.setText(vehicle.getVehicleName());
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(vehicle));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(vehicle));
    }

    @Override
    public int getItemCount() {
        return vehicleList == null ? 0 : vehicleList.size();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicleName;
        ImageButton btnEdit, btnDelete;
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            btnEdit = itemView.findViewById(R.id.btn_edit_vehicle);
            btnDelete = itemView.findViewById(R.id.btn_delete_vehicle);
        }
    }
} 