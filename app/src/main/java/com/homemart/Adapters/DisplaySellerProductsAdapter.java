package com.homemart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.utils.Capitalize;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class DisplaySellerProductsAdapter extends ExpandableRecyclerViewAdapter<DisplaySellerProductsAdapter.DisplayCategoryViewHolder, DisplaySellerProductsAdapter.DisplayCategoryProductsViewHolder> {

    String mCategory;

    public DisplaySellerProductsAdapter(List<? extends ExpandableGroup> groups, String mCategory) {
        super(groups);
        this.mCategory = mCategory;
    }

    public DisplaySellerProductsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public DisplayCategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_seller_name, parent, false);
        return new DisplayCategoryViewHolder(view);

    }

    @Override
    public DisplayCategoryProductsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_product_name, parent, false);
        return new DisplayCategoryProductsViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(DisplayCategoryProductsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Product product = (Product) (group).getItems().get(childIndex);

        Picasso.get().load(product.getImageURL())
                .placeholder(R.drawable.loading)
                .fit().centerCrop().into(holder.mProductImage);
        holder.mProductName.setText(Capitalize.capitalize(product.getName()));
        holder.mProductPrice.setText("₹" + product.getPrice() + "");
        holder.mProductUnit.setText(product.getUnit());
        holder.mProductDescription.setText(product.getDescription());
        //Toast.makeText(holder.itemView.getContext(), ""+product.getFoodtype(), Toast.LENGTH_SHORT).show();
        if (product.getFoodtype() == 0)
            holder.mFoodTypeImageView.setBackgroundResource(R.drawable.veg);
        else
            holder.mFoodTypeImageView.setBackgroundResource(R.drawable.nonveg);

    }

    @Override
    public void onBindGroupViewHolder(DisplayCategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryName(group);

        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                if (group.getTitle().equals(mCategory)) {
                    holder.itemView.performClick();
                    //holder.itemView.;
                    //Toast.makeText(holder.itemView.getContext(), "yes"+mCategory, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);


    }

    class DisplayCategoryViewHolder extends GroupViewHolder {

        private TextView mCategoryName;

        public DisplayCategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryName = itemView.findViewById(R.id.category_name);
        }

        public void setCategoryName(ExpandableGroup group) {
            mCategoryName.setText("Types of " + group.getTitle() + " !");

        }

    }


    class DisplayCategoryProductsViewHolder extends ChildViewHolder {
        private TextView mProductName, mProductPrice, mProductUnit, mProductDescription;
        private ImageView mProductImage, mFoodTypeImageView;

        public DisplayCategoryProductsViewHolder(View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductPrice = itemView.findViewById(R.id.product_price);
            mProductUnit = itemView.findViewById(R.id.product_unit);
            mProductImage = itemView.findViewById(R.id.product_image);
            mProductDescription = itemView.findViewById(R.id.product_description);
            mFoodTypeImageView = itemView.findViewById(R.id.product_foodtype);

        }

        public void onBind(Product product) {
            mProductName.setText(product.getName());
        }
    }


}
