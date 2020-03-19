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
import com.homemart.Adapters.DisplayProductAdapter;
import com.homemart.R;
import com.homemart.models.Product;

import java.util.ArrayList;
import java.util.List;


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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference mDatabaseReference;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(getActivity(), ""+mAuth.getUid(), Toast.LENGTH_SHORT).show();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers")
                .child(mAuth.getUid()).child("category").child("cake");

        List<Product> mProductList = new ArrayList<>();


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView mProductRecyclerview = rootView.findViewById(R.id.product_recyclerView);
        mProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mProductList.clear();
                for (DataSnapshot datasnapshotobject : children) {
                    Log.d("Log2", "onDataChange2: "+datasnapshotobject.getValue());
                    Object object = datasnapshotobject.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    Product product = new Gson().fromJson(json, Product.class);
                    Log.d("Log3", "onDataChange: "+product.getName());
                    mProductList.add(product);
                }
                mProductRecyclerview.setAdapter(new DisplayProductAdapter(mProductList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //FETCH DATA

        /*
        Product p = new Product();
        p.setDescription("1");
        p.setImageURL("as");
        p.setName("cakee");
        p.setPrice(890);

        mProductList.add(p);
        p = new Product();
        p.setDescription("1");
        p.setImageURL("as");
        p.setName("cakee2");
        p.setPrice(590);

        mProductList.add(p);*/



        return rootView;
    }

}
