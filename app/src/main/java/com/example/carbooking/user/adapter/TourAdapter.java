package com.example.carbooking.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.R;
import com.example.carbooking.databinding.ItemTourBinding;
import com.example.carbooking.helpler.FormatUtils;
import com.example.carbooking.user.TourDetailActivity;

public class TourAdapter extends ListAdapter<Tour, TourAdapter.TourViewHolder> {

    public TourAdapter() {
        super(new TourDiffUtilCallback());
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TourViewHolder(ItemTourBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        private final ItemTourBinding binding;

        public TourViewHolder(ItemTourBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Tour tour) {
            try {
                Context context = binding.getRoot().getContext();

                // Bind data
                Glide.with(context)
                        .load(tour.getImage())
                        .error(R.drawable.placeholder)
                        .into(binding.imageTour);
                binding.textTitle.setText(tour.getTile());
                binding.textLocation.setText(tour.getLocationTo());
                binding.textPrice.setText(FormatUtils.formatCurrency(tour.getPricePerPerson()) + "đ");

                // Set listeners
                binding.getRoot().setOnClickListener(v -> {
                    Intent intent = new Intent(context, TourDetailActivity.class);
                    intent.putExtra(TourDetailActivity.KEY_TOUR, tour);
                    context.startActivity(intent);
                });
            } catch (Exception e) {
                Log.d("TAG", "[TourAdapter.TourViewHolder.bind()] Error while binding data: " + e.getMessage());
            }
        }
    }

    private static class TourDiffUtilCallback extends DiffUtil.ItemCallback<Tour> {
        @Override
        public boolean areItemsTheSame(@NonNull Tour oldItem, @NonNull Tour newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tour oldItem, @NonNull Tour newItem) {
            return oldItem.equals(newItem);
        }
    }
}
