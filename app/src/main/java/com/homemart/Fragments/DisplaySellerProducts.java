package com.homemart.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.homemart.Adapters.DisplaySellerProductsAdapter;
import com.homemart.R;
import com.homemart.models.CategoryToDisplay;
import com.homemart.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.homemart.utils.Capitalize.capitalize;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplaySellerProducts extends Fragment {

    private String mSellerId;
    private String mSellerCategory;

    private String mSellerName;
    private String mSellerPhoneNo;
    private String mSellerRating;
    private String mSellerProfileURL;
    private String mSellerDescription;

    @BindView(R.id.sellername_textview)
    TextView mSellerNameTextView;

    @BindView(R.id.phoneno_textview)
    TextView mSellerPhoneNoTextView;

    @BindView(R.id.description_textview)
    TextView mSellerDescriptionTextView;

    @BindView(R.id.display_rating)
    MaterialRatingBar mMaterialRatingBar;

    @BindView(R.id.seller_profile_image)
    ImageView mSellerProfileImageView;

    @BindView(R.id.background_image)
    ImageView mBackgroundImageView;
    public DisplaySellerProducts() {
        // Required empty public constructor
    }

    public DisplaySellerProducts(String mSellerId, String mSellerCategory) {
        this.mSellerId = mSellerId;
        this.mSellerCategory = mSellerCategory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seller_products, container, false);
        ButterKnife.bind(this,rootView);

        List<CategoryToDisplay> categoryData = new ArrayList<>();

        //FIREBASE
        DatabaseReference mSellerProductsDatabaseReference;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        mSellerProductsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers").child(mSellerId);

        mSellerProductsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSellerProfileURL =dataSnapshot.child("imageURL").getValue().toString();

                if (!mSellerProfileURL.isEmpty()) {
                    Picasso.get().load(mSellerProfileURL)
                            .placeholder(R.drawable.loading)
                            .fit().centerCrop().into(mSellerProfileImageView);

                    Picasso.get().load(mSellerProfileURL)
                            .placeholder(R.color.white)
                            .fit().centerCrop().into(mBackgroundImageView);
                }
                mSellerName = dataSnapshot.child("username").getValue().toString();
                mSellerPhoneNo = dataSnapshot.child("phoneno").getValue().toString();
                mSellerRating = dataSnapshot.child("rating").getValue().toString();
                mSellerDescription = dataSnapshot.child("description").getValue().toString();


                mSellerNameTextView.setText(capitalize(mSellerName)+"'s Store");
                mSellerPhoneNoTextView.setText("+91 "+mSellerPhoneNo);
                mMaterialRatingBar.setRating(Float.parseFloat(mSellerRating));
                mSellerDescriptionTextView.setText(mSellerDescription);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSellerProductsDatabaseReference.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> categorylist = dataSnapshot.getChildren();

                categoryData.clear();
                for (DataSnapshot eachCategory : categorylist) {
                    List<Product> productData = new ArrayList<>();

                    Iterable<DataSnapshot> productlist = eachCategory.getChildren();
                    Log.d("TAG", "#####onDataChange: "+eachCategory.getValue().toString());
                    if (!eachCategory.getValue().toString().equals("nothing")) {

                        for (DataSnapshot eachproduct : productlist) {

                            Object object = eachproduct.getValue(Object.class);
                            String json = new Gson().toJson(object);
                            Log.d("Log3", "onDataChange: " + json);

                            Product product = new Gson().fromJson(json, Product.class);
                            Log.d("Log4", "onDataChange: " + product.getDescription());
                            Log.d("Log4", "onDataChange: " + product.getName());
                            productData.add(product);

                        }
                    }
                    Log.d("TAG", "onDataChange: " + productData.size());
                    if (productData.size() > 0) {
                        categoryData.add(new CategoryToDisplay(eachCategory.getKey(), productData));
                    }
                }
                RecyclerView recyclerView = rootView.findViewById(R.id.sellerproducts_recyclerview);
                LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());

                //instantiate your adapter with the list of genres
                Log.d("TAG", "onDataChange: ");
                DisplaySellerProductsAdapter adapter = new DisplaySellerProductsAdapter(categoryData, mSellerCategory);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                /*recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                             recyclerView.findViewHolderForAdapterPosition().itemView.performClick();
                    }
                });*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
