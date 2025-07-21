package com.example.carbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbooking.Entity.Tour;
import com.example.carbooking.R;
import com.example.carbooking.helpler.FormatUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourViewHolder> {
    private List<Tour> tours = null;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onRemoveClick(int position);
    }
    public TourListAdapter(List<Tour> tours, Context context, OnItemClickListener onItemClickListener) {
        this.tours = tours;
        this.context = context;
        this.onItemClickListener = onItemClickListener;  // Set the listener here
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_tour, parent ,false);
        return new TourViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tours.get(position);
        holder.tvTourTitle.setText("" + tour.getTile());

        if(tour.isAvaliable()){
            holder.imTourStatus.setImageResource(R.drawable.active_icon);
        }else{
            holder.imTourStatus.setImageResource(R.drawable.ban_icon);
        }
        //format price
        double pricePerPerson = tour.getPricePerPerson();
        String formattedPrice = FormatUtils.formatCurrency(pricePerPerson);
        holder.tvPricePerPerson.setText("" + formattedPrice+ " VND");
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public class TourViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTourTitle,
                 tvPricePerPerson;
        private ImageButton imbRemove, imbEdit;
        private ImageView imTourStatus;
        public TourViewHolder (@NotNull View itemView, OnItemClickListener listener){
            super(itemView);
            tvTourTitle = itemView.findViewById(R.id.tv_tour_title);

            imTourStatus = itemView.findViewById(R.id.img_status);
            tvPricePerPerson = itemView.findViewById(R.id.tv_price_per_person);
            imbEdit = itemView.findViewById(R.id.imb_edit);
            imbRemove = itemView.findViewById(R.id.imb_remove);
            imbEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
            imbRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            listener.onRemoveClick(position);
                    }
                }
            });
        }
    }
}

