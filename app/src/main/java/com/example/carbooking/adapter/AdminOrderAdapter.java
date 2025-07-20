package com.example.carbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.OrderTour.OrderDetailActivity;
import com.example.carbooking.R;
import com.example.carbooking.admin.order.OrderDetail;
import com.example.carbooking.repository.TourRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private List<Tour> tourList;

    public AdminOrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        TourRepository tourRepository = new TourRepository(context);
        Tour tour = tourRepository.getTour(order.getTourId());

        // Hiển thị dữ liệu trong ViewHolder
        holder.nameTour.setText("Tour: " + tour.getTile());
        holder.statusTour.setText("Status: " + (order.getStatusId() == 1 ? "Completed" : "Pending"));
        holder.priceTour.setText("Price: " + order.getTotalFee());
        Glide.with(context)
                .load(tour.getImage())
                .error(R.drawable.placeholder)
                .into(holder.imgTour);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String departureDayString = sdf.format(order.getDepartureDay());
        String endDayString = sdf.format(order.getEndDate());
        String orderDay = sdf.format(order.getOrderDate());


        holder.btnOrderDetail.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetail.class);
            intent.putExtra("orderId", order.getId());
            intent.putExtra("tourId", order.getTourId());
            intent.putExtra("fee", order.getTotalFee());
            intent.putExtra("statusId", order.getStatusId());
            intent.putExtra("userId", order.getUserId());
            intent.putExtra("numPer", order.getNumberOfPerson());
            intent.putExtra("orderDay",orderDay);
            intent.putExtra("departDay", departureDayString);
            intent.putExtra("endDay", endDayString);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView nameTour, statusTour, priceTour;
        ImageView imgTour;
        Button btnOrderDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTour = itemView.findViewById(R.id.admin_name_tour);
            statusTour = itemView.findViewById(R.id.admin_status_tour);
            priceTour = itemView.findViewById(R.id.admin_price_tour);
            imgTour = itemView.findViewById(R.id.admin_img_tour);
            btnOrderDetail = itemView.findViewById(R.id.btnAdminOrderDetail);
        }

    }

}
