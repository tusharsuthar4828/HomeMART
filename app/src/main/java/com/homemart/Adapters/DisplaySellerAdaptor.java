package com.homemart.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.homemart.Fragments.DisplaySellerProducts;
import com.homemart.R;
import com.homemart.utils.Capitalize;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class DisplaySellerAdaptor extends RecyclerView.Adapter<DisplaySellerAdaptor.DisplaySellerViewHolder> {

    List<DataSnapshot> mSellerList = new ArrayList<>();

    String mCategory;

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public DisplaySellerAdaptor(List<DataSnapshot> mSellerList) {
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
        Log.d("Log", "onBindViewHolder: " + mSellerList.get(position).child("email").getValue());
        holder.mSellerUsernameTextView.setText(Capitalize.capitalize(mSellerList.get(position).child("username").getValue().toString()) + "'s Store");

        holder.mSellerDescription.setText(mSellerList.get(position).child("description").getValue() + "");
        String imageURL = mSellerList.get(position).child("imageURL").getValue().toString();
        if (!imageURL.isEmpty())
            Picasso.get().load(imageURL)
                    .placeholder(R.drawable.loading)
                    .fit().centerCrop().into(holder.mSellerImageView);

        holder.materialRatingBar.setRating(Float.parseFloat(mSellerList.get(position).child("rating").getValue().toString()));
        holder.mSeller_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_conatiner, new DisplaySellerProducts(mSellerList.get(position).getKey(), mCategory), "DisplaySellerProducts").commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSellerList.size();
    }

    class DisplaySellerViewHolder extends RecyclerView.ViewHolder {

        ImageView mSellerImageView;
        TextView mSellerUsernameTextView, mSellerDescription;
        LinearLayout mSeller_linearlayout;
        MaterialRatingBar materialRatingBar;

        public DisplaySellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mSellerUsernameTextView = itemView.findViewById(R.id.sellerimage);
            mSellerDescription = itemView.findViewById(R.id.seller_description);
            mSellerImageView = itemView.findViewById(R.id.profile_image);
            mSeller_linearlayout = itemView.findViewById(R.id.seller_linearlayout);
            materialRatingBar = itemView.findViewById(R.id.display_rating);
        }
    }
}
