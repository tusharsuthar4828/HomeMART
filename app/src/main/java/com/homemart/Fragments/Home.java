package com.homemart.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.homemart.Adapters.DisplayCategoryAdapter;
import com.homemart.Adapters.DisplayProductAdapter;
import com.homemart.Adapters.DisplaySellerAdaptor;
import com.homemart.R;
import com.homemart.models.Product;
import com.homemart.models.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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

        //FIREBASE
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabaseReference;
        DatabaseReference mCategoryDatabaseReference;
        DatabaseReference mSellerDatabaseReference;
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(getActivity(), ""+mAuth.getUid(), Toast.LENGTH_SHORT).show();

        /*mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers")
                .child(mAuth.getUid()).child("category").child("cake");*/
        mCategoryDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers")
                .child(mAuth.getUid()).child("category");
        mSellerDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers");

        //List of data
        //List<Product> mProductList = new ArrayList<>();
        List<String> mCategoryList = new ArrayList<>();
        List<Seller> mSellerList = new ArrayList<>();


        //RecyclerView mProductRecyclerview = rootView.findViewById(R.id.product_recyclerView);
        RecyclerView mCategoryRecyclerview = rootView.findViewById(R.id.category_recyclerview);
        RecyclerView mSellerRecyclerview = rootView.findViewById(R.id.seller_recyclerview);


        //mProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mSellerRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        //DATA FETCHING
        /*mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mProductList.clear();
                for (DataSnapshot datasnapshotobject : children) {
                    //Log.d("Log2", "onDataChange2: "+datasnapshotobject.getValue());


                    Object object = datasnapshotobject.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    Product product = new Gson().fromJson(json, Product.class);


                    //Log.d("Log3", "onDataChange: "+product.getName());


                    mProductList.add(product);
                }
                mProductRecyclerview.setAdapter(new DisplayProductAdapter(mProductList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        mCategoryDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mCategoryList.clear();
                for (DataSnapshot datasnapshotobject : children) {


                    mCategoryList.add(datasnapshotobject.getKey() + "");
                }

                mCategoryRecyclerview.setAdapter(new DisplayCategoryAdapter(mCategoryList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSellerDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mSellerList.clear();
                for (DataSnapshot datasnapshotobject : children) {

                    Object object = datasnapshotobject.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    Log.d("Log3", "onDataChange: "+json);
                    Seller seller = new Gson().fromJson(json, Seller.class);
                    Log.d("Log4", "onDataChange: "+seller.getUsername());
                    mSellerList.add(seller);

                }

                mSellerRecyclerview.setAdapter(new DisplaySellerAdaptor(mSellerList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
