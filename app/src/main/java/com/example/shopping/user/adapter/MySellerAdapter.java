package com.example.shopping.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.search.viewHolder.SearchViewHolder;
import com.example.shopping.user.viewHolder.MySellerViewHolder;
import com.example.shopping.utils.Constants;

import java.util.List;

public class MySellerAdapter extends RecyclerView.Adapter<MySellerViewHolder>  {

    private Context context;
    private List<GoodsBean> resultBeans;
    private LayoutInflater mLayoutInflater;

    public MySellerAdapter(Context context, List<GoodsBean> resultBeans) {
        this.context = context;
        this.resultBeans = resultBeans;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MySellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myseller, parent, false);
        return new MySellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySellerViewHolder holder, int position) {
        GoodsBean goodsBean = resultBeans.get(position);
        ImageView iv_product = holder.mySeller_iv_product;
        Glide.with(context).load(Constants.BASE_REQ + goodsBean.getFigure()).into(iv_product);
        holder.mySeller_tv_name.setText(goodsBean.getName());
        holder.mySeller_tv_price.setText("￥"+goodsBean.getCover_price());

        holder.goodsBean = goodsBean;
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
