package com.homemart.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.homemart.Adapters.DisplaySellerAdaptor;
import com.homemart.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayAllSellers extends Fragment {

    public static String mCategoryName;
    public DisplayAllSellers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_allsellers, container, false);

        //FIREBASE
        DatabaseReference mSellersDatabaseReference;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        mSellersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers");

        List<DataSnapshot> mSellerList = new ArrayList<>();

        RecyclerView mSellerRecyclerview = rootView.findViewById(R.id.seller_recyclerview);

        mSellerRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mSellersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("Log1", "onDataChange: " + children);

                mSellerList.clear();
                for (DataSnapshot datasnapshotobject : children) {

                    for(DataSnapshot dataSnapshotobject2:datasnapshotobject.child("categories").getChildren()){

                        if(dataSnapshotobject2.getKey().equals(mCategoryName) && !dataSnapshotobject2.getValue().toString().equals("nothing")){

                            /*Object object = datasnapshotobject.getValue(Object.class);
                            String json = new Gson().toJson(object);
                            Log.d("Log3", "onDataChange: "+json);

                            Seller seller = new Gson().fromJson(json, Seller.class);
                            Log.d("Log4", "onDataChange: "+seller.getUsername());*/
                            mSellerList.add(datasnapshotobject);

                        }
                    }


                }

                DisplaySellerAdaptor displaySellerAdaptor = new DisplaySellerAdaptor(mSellerList);
                displaySellerAdaptor.setCategory(mCategoryName);
                mSellerRecyclerview.setAdapter(displaySellerAdaptor);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
