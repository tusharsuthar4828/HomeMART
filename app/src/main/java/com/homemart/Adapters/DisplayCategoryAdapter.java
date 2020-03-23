package com.homemart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.Fragments.DisplayAllSellers;
import com.homemart.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayCategoryAdapter extends RecyclerView.Adapter<DisplayCategoryAdapter.DisplayCategoryViewHolder> {
    List<String> mCategoryList = new ArrayList<>();

    public DisplayCategoryAdapter(List<String> mCategoryList) {
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public DisplayCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_category, parent, false);
        return new DisplayCategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DisplayCategoryViewHolder holder, int position) {
        holder.mNameTextView.setText(mCategoryList.get(position));
        holder.mProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(holder.itemView.getContext(), "", Toast.LENGTH_SHORT).show();
                Context context = holder.itemView.getContext();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_conatiner, new DisplayAllSellers(mCategoryList.get(position)), "DisplayEachProduct").commit();

            }
        });
    }

    @Override

    public int getItemCount() {
        return mCategoryList.size();
    }


    class DisplayCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTextView;
        CardView mProductCard;


        public DisplayCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.category_textview);
            mProductCard = itemView.findViewById(R.id.product_cardview);

        }
    }
}
