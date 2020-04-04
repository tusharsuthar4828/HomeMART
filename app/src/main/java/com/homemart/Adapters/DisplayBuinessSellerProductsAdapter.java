package com.homemart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.homemart.Fragments.BusinessProfile;
import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.utils.Capitalize;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class DisplayBuinessSellerProductsAdapter extends ExpandableRecyclerViewAdapter<DisplayBuinessSellerProductsAdapter.DisplayCategoryViewHolder, DisplayBuinessSellerProductsAdapter.DisplayCategoryProductsViewHolder> {

    BusinessProfile businessProfile;

    public DisplayBuinessSellerProductsAdapter(List<? extends ExpandableGroup> groups, BusinessProfile businessProfile) {
        super(groups);
        this.businessProfile = businessProfile;
    }

    public DisplayBuinessSellerProductsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public DisplayCategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.business_single_seller_name, parent, false);
        return new DisplayCategoryViewHolder(view);

    }

    @Override
    public DisplayCategoryProductsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.business_single_product_name, parent, false);
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

        holder.mProductDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(holder.itemView.getContext(), FirebaseAuth.getInstance().getUid()+"hey"+product.getKey()+ " cat"+category, Toast.LENGTH_SHORT).show();
                //Toast.makeText(holder.itemView.getContext(), ""+product.getProducttype(), Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("sellers").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("categories").child(product.getProducttype()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() == 1){
                            FirebaseDatabase.getInstance().getReference().child("sellers").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("categories").child(product.getProducttype()).setValue("nothing");

                        }else{
                            FirebaseDatabase.getInstance().getReference().child("sellers").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("categories").child(product.getProducttype()).child(product.getKey()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                }
        });


        if (product.getFoodtype() == 0)
            holder.mFoodTypeImageView.setBackgroundResource(R.drawable.veg);
        else
            holder.mFoodTypeImageView.setBackgroundResource(R.drawable.nonveg);


    }

    @Override
    public void onBindGroupViewHolder(DisplayCategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        Context context = holder.itemView.getContext();
        holder.setCategoryName(group);
        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                holder.itemView.performClick();
            }
        });
        holder.mAddProductImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessProfile.prepareProduct(group.getTitle());
            }
        });
        FirebaseDatabase.getInstance().getReference().child("links").child("category").child(group.getTitle()+"").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mImageURL = dataSnapshot.getValue().toString();
                Picasso.get().load(mImageURL)
                        .placeholder(R.drawable.loading)
                        .fit().centerCrop().into(holder.mCategoryImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    class DisplayCategoryViewHolder extends GroupViewHolder {

        private TextView mCategoryName;
        private ImageButton mAddProductImageButton;
        private ImageView mCategoryImageView;

        public DisplayCategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryName = itemView.findViewById(R.id.category_name);
            mAddProductImageButton = itemView.findViewById(R.id.add_product);
            mCategoryImageView = itemView.findViewById(R.id.category_imageview);
        }

        public void setCategoryName(ExpandableGroup group) {
            mCategoryName.setText(Capitalize.capitalize(group.getTitle()));
        }

    }


    class DisplayCategoryProductsViewHolder extends ChildViewHolder {
        private TextView mProductName, mProductPrice, mProductUnit, mProductDescription;
        private ImageView mProductImage,mFoodTypeImageView;;
        private ImageButton mProductDeleteButton;

        public DisplayCategoryProductsViewHolder(View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductPrice = itemView.findViewById(R.id.product_price);
            mProductUnit = itemView.findViewById(R.id.product_unit);
            mProductImage = itemView.findViewById(R.id.product_image);
            mProductDescription = itemView.findViewById(R.id.product_description);
            mProductDeleteButton = itemView.findViewById(R.id.delete_button);
            mFoodTypeImageView = itemView.findViewById(R.id.product_foodtype);
        }

        public void onBind(Product product) {
            mProductName.setText(product.getName());
        }
    }


}
