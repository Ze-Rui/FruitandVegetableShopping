package com.example.shopping.home.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.home.adapter.RecommendAdapter;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.utils.ToGoodsInfoActivity;

import java.util.List;

public class RecommendViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private GridView gv_hot;
    private RecommendAdapter adapter;

    public RecommendViewHolder(Context mContext, @NonNull View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.gv_hot = itemView.findViewById(R.id.gv_hot);
    }

    public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        //设置适配器
        adapter = new RecommendAdapter(mContext,recommend_info);
        gv_hot.setAdapter(adapter);
        // 设置点击事件
        gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 得到点击的热卖商品数据
                ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                GoodsBean goodsBean = new GoodsBean(recommendInfoBean.getCover_price(),recommendInfoBean.getFigure(),recommendInfoBean.getName(),recommendInfoBean.getProduct_id(),recommendInfoBean.getProduct_id());

                // 跳转到详情
                ToGoodsInfoActivity.startGoodsInfoActivity(mContext,goodsBean);
            }
        });
    }
}
