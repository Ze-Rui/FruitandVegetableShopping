package com.example.shopping.location.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.location.bean.Location;
import com.example.shopping.location.viewHolder.LocationViewHolder;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private Context mContext;
    private List<Location> resultBeans;
    private LayoutInflater mLayoutInflater;


    public LocationAdapter(Context mContext, List<Location> resultBeans) {
        this.mContext = mContext;
        this.resultBeans = resultBeans;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);

        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.getUser_username().setText(resultBeans.get(position).getUserName());
        holder.getUser_phone().setText(resultBeans.get(position).getPhone());
        holder.getUser_location().setText(resultBeans.get(position).getLocation());
        holder.setLocation(resultBeans.get(position));
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (resultBeans != null){
            count = resultBeans.size();
        }
        return count;
    }

}
