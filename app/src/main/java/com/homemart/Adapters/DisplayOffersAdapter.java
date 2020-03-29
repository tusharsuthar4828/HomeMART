package com.homemart.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.R;
import com.homemart.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DisplayOffersAdapter extends RecyclerView.Adapter<DisplayOffersAdapter.DisplayProuctViewHolder> {

    List<String> mOffersList = new ArrayList<>();

    public DisplayOffersAdapter(List<String> mOffersList) {
        this.mOffersList = mOffersList;
    }

    @NonNull
    @Override
    public DisplayProuctViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_offer, parent, false);
        return new DisplayProuctViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DisplayProuctViewHolder holder, int position) {
        //Set image in container
        Picasso.get().load(mOffersList.get(position))
                .placeholder(R.drawable.loading)
                .fit().centerCrop(1).into(holder.mOffersImageView);
    }

    @Override
    public int getItemCount() {
        return mOffersList.size();
    }

    class DisplayProuctViewHolder extends RecyclerView.ViewHolder {

        ImageView mOffersImageView;

        public DisplayProuctViewHolder(@NonNull View itemView) {
            super(itemView);

            mOffersImageView = itemView.findViewById(R.id.offer_imageview);

        }

    }
}
