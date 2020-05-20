package com.homemart.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.homemart.Adapters.DisplayCategoryAdapter;
import com.homemart.Adapters.DisplayOffersAdapter;
import com.homemart.Adapters.DisplayPopularProductsAdapter;
import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.models.Seller;

import java.util.ArrayList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        EditText searchEditText = rootView.findViewById(R.id.searchEditText);

        /*searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchEditText.getRight() - searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Toast.makeText(getActivity(), "ssssssssss", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });*/


        //searchEditText.setCompoundDrawablePadding(20);

        //FIREBASE
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mOfferlinksDatabaseReference;
        DatabaseReference mCategoryDatabaseReference;
        DatabaseReference mSellerDatabaseReference;
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(getActivity(), ""+mAuth.getUid(), Toast.LENGTH_SHORT).show();

        mOfferlinksDatabaseReference = FirebaseDatabase.getInstance().getReference().child("links").child("offerlink");
        mCategoryDatabaseReference = FirebaseDatabase.getInstance().getReference().child("links").child("category");
        mSellerDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers");

        //List of data
        List<String> mOffersList = new ArrayList<>();
        List<DataSnapshot> mCategoryList = new ArrayList<>();
        List<Seller> mSellerList = new ArrayList<>();
        List<Product> mPopularProductList = new ArrayList<>();


        RecyclerView mOffersRecyclerview = rootView.findViewById(R.id.offers_recyclerView);
        RecyclerView mCategoryRecyclerview = rootView.findViewById(R.id.category_recyclerview);
        RecyclerView mSellerRecyclerview = rootView.findViewById(R.id.seller_recyclerview);

        //New
        RecyclerView mPopularFoodRecyclerView = rootView.findViewById(R.id.popularfood_recyclerView);


        mOffersRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mPopularFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        //mCategoryRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //mCategoryRecyclerview.addItemDecoration(new EqualSpacingItemDecoration(10,EqualSpacingItemDecoration.VERTICAL));
        mCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mSellerRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        mSellerDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> allseller = dataSnapshot.getChildren();
                //clear list
                mPopularProductList.clear();

                Log.d(TAG, "onDataChange: allseller" + allseller);
                for (DataSnapshot eachseller : allseller) {

                    Iterable<DataSnapshot> allcategory = eachseller.child("categories").getChildren();

                    Log.d(TAG, "onDataChange: allcategory" + allcategory);

                    for (DataSnapshot eachcategory : allcategory) {

                        if (!eachcategory.getValue().toString().equals("nothing")) {

                            Iterable<DataSnapshot> allproducts = eachcategory.getChildren();

                            Log.d(TAG, "onDataChange: allproducts" + allproducts);
                            for (DataSnapshot eachproduct : allproducts) {

                                Object object = eachproduct.getValue(Object.class);
                                String json = new Gson().toJson(object);
                                Log.d("Log3", "onDataChange: " + json);

                                Product product = new Gson().fromJson(json, Product.class);
                                Log.d("Log4", "onDataChange: " + product.getDescription());
                                Log.d("Log4", "onDataChange: " + product.getName());
                                mPopularProductList.add(product);
                            }

                        }

                    }
                }
                mPopularFoodRecyclerView.setAdapter(new DisplayPopularProductsAdapter(mPopularProductList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Offers Fetching
        mOfferlinksDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                mOffersList.clear();
                for (DataSnapshot datasnapshotobject : children) {

                    mOffersList.add(datasnapshotobject.getValue().toString());
                }
                mOffersRecyclerview.setAdapter(new DisplayOffersAdapter(mOffersList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Category Fetching
        mCategoryDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mCategoryList.clear();
                for (DataSnapshot datasnapshotobject : children) {
                    mCategoryList.add(datasnapshotobject);
                }

                mCategoryRecyclerview.setAdapter(new DisplayCategoryAdapter(mCategoryList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }


}
