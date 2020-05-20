package com.homemart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.utils.Capitalize;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DisplayPopularProductsAdapter extends RecyclerView.Adapter<DisplayPopularProductsAdapter.DisplayPopularProductsViewHolder> {

    List<Product> mPopularProductList = new ArrayList<>();

    public DisplayPopularProductsAdapter(List<Product> popularProductList) {
        mPopularProductList = popularProductList;
    }

    @NonNull
    @Override
    public DisplayPopularProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_popular_product, parent, false);
        return new DisplayPopularProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayPopularProductsViewHolder holder, int position) {
        Picasso.get().load(mPopularProductList.get(position).getImageURL())
                .placeholder(R.drawable.loading)
                .fit().centerCrop().into(holder.mProductImage);
        holder.mProductName.setText(Capitalize.capitalize(mPopularProductList.get(position).getName()));
        holder.mProductPrice.setText("₹" + mPopularProductList.get(position).getPrice() + "");
        //holder.mProductUnit.setText(mPopularProductList.get(position).getUnit());
        //holder.mProductDescription.setText(mPopularProductList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mPopularProductList.size();
    }

    class DisplayPopularProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView mProductName, mProductPrice, mProductUnit, mProductDescription;
        private ImageView mProductImage;

        public DisplayPopularProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductName = itemView.findViewById(R.id.product_name);
            mProductPrice = itemView.findViewById(R.id.product_price);
            //mProductUnit = itemView.findViewById(R.id.product_unit);
            mProductImage = itemView.findViewById(R.id.product_image);
            //mProductDescription = itemView.findViewById(R.id.product_description);


        }
    }
}
