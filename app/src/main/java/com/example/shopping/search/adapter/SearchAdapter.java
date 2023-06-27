package com.example.shopping.search.adapter;

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
import com.example.shopping.utils.Constants;

import java.util.List;

public class SearchAdapter  extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<GoodsBean> resultBeans;
    private LayoutInflater mLayoutInflater;

    public SearchAdapter(Context context, List<GoodsBean> resultBeans) {
        this.context = context;
        this.resultBeans = resultBeans;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        GoodsBean goodsBean = resultBeans.get(position);
        ImageView iv_product = holder.getIv_product();
        Glide.with(context).load(Constants.BASE_REQ + goodsBean.getFigure()).into(iv_product);

        holder.getTv_name().setText(goodsBean.getName());
        holder.getTv_price().setText("￥"+goodsBean.getCover_price());

        holder.setGoodsBean(goodsBean);
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
