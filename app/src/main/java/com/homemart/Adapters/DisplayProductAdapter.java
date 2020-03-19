package com.homemart.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.R;
import com.homemart.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DisplayProductAdapter extends RecyclerView.Adapter<DisplayProductAdapter.DisplayProuctViewHolder> {

    List<Product> mProductList = new ArrayList<>();

    public DisplayProductAdapter(List<Product> mProductList) {
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public DisplayProuctViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_product, parent, false);
        return new DisplayProuctViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DisplayProuctViewHolder holder, int position) {
        holder.mNameTextView.setText(mProductList.get(position).getName()+"");
        holder.mPriceTextView.setText(mProductList.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class DisplayProuctViewHolder extends RecyclerView.ViewHolder{

        TextView mNameTextView;
        TextView mPriceTextView;

        public DisplayProuctViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.productname);
            mPriceTextView = itemView.findViewById(R.id.productprice);
        }
    }
}