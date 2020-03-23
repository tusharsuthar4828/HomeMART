package com.homemart.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.models.Seller;

import java.util.ArrayList;
import java.util.List;

public class DisplaySellerAdaptor extends RecyclerView.Adapter<DisplaySellerAdaptor.DisplaySellerViewHolder> {

    List<Seller> mSellerList = new ArrayList<>();

    public DisplaySellerAdaptor(List<Seller> mSellerList) {
        this.mSellerList = mSellerList;
    }

    @NonNull
    @Override
    public DisplaySellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_seller, parent, false);
        return new DisplaySellerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DisplaySellerViewHolder holder, int position) {
        Log.d("Log", "onBindViewHolder: "+mSellerList.get(position).getEmail());
        holder.mSellerTextView.setText(mSellerList.get(position).getUsername() + "");

    }

    @Override
    public int getItemCount() {
        return mSellerList.size();
    }

    class DisplaySellerViewHolder extends RecyclerView.ViewHolder {

        //ImageView mSellerImageView;
        TextView mSellerTextView;

        public DisplaySellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mSellerTextView = itemView.findViewById(R.id.sellerimage);

        }
    }
}
