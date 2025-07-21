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
import com.example.carbooking.Entity.User;
import com.example.carbooking.OrderTour.OrderDetailActivity;
import com.example.carbooking.R;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.UserRepository;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private TourRepository tourRepository;
    private UserRepository userRepository;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.userRepository = new UserRepository(context);
        this.tourRepository = new TourRepository(context);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        User user = userRepository.getUserById(order.getUserId());
        Tour tour = tourRepository.getTour(order.getTourId());

        // Hiển thị dữ liệu trong ViewHolder
        holder.nameTour.setText("" + tour.getTile());
        holder.statusTour.setText("Status: " + (order.getStatusId() == 1 ? "Completed" : "Pending"));
        holder.priceTour.setText("Price: " + order.getTotalFee());
        Glide.with(context)
                .load(tour.getImage())
                .error(R.drawable.placeholder)
                .into(holder.imgTour);

        holder.btnHistoryDetail.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getId());
            intent.putExtra("tourId",tour.getId());
            intent.putExtra("tourName", tour.getTile());
            intent.putExtra("fee", order.getTotalFee());
            intent.putExtra("statusId", order.getStatusId());
            intent.putExtra("userName", user.getUserName());
            intent.putExtra("numPer", order.getNumberOfPerson());
            intent.putExtra("orderDay", order.getOrderDate());
            intent.putExtra("departDay", order.getDepartureDay());
            intent.putExtra("image", tour.getImage());
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
        Button btnHistoryDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTour = itemView.findViewById(R.id.name_tour);
            statusTour = itemView.findViewById(R.id.status_tour);
            priceTour = itemView.findViewById(R.id.price_tour);
            imgTour = itemView.findViewById(R.id.img_tour_order);
            btnHistoryDetail = itemView.findViewById(R.id.btnHistoryDetail);
        }

    }

}
