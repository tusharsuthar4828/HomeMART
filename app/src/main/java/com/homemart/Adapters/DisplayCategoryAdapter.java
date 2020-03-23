package com.homemart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    }

    @Override

    public int getItemCount() {
        return mCategoryList.size();
    }


    class DisplayCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTextView;

        public DisplayCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.category_textview);
        }
    }
}
