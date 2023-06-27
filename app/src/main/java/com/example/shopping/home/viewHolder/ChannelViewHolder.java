package com.example.shopping.home.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.home.activity.GoodsListActivity;
import com.example.shopping.home.adapter.ChannelAdapter;
import com.example.shopping.home.adapter.HomeRecycleAdapter;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.utils.ToGoodsInfoActivity;

import java.util.List;

public class ChannelViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG =
            ChannelViewHolder.class.getSimpleName();
    private Context mContext;
    private GridView gv_channel;
    private ChannelAdapter adapter;
    public ChannelViewHolder(Context mContext, @NonNull View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.gv_channel = itemView.findViewById(R.id.gv_channel);

    }

    public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        // 得到数据
        //设置适配器
        adapter = new ChannelAdapter(mContext,channel_info);
        gv_channel.setAdapter(adapter);
        // 设置item点击事件
        gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                intent.putExtra("position", channel_info.get(position).getChannelId());
                mContext.startActivity(intent);
            }
        });
    }
}
