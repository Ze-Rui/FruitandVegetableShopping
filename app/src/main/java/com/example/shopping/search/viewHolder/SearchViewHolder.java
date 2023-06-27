package com.example.shopping.search.viewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.app.GoodsInfoActivity;
import com.example.shopping.home.bean.GoodsBean;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_product;
    private TextView tv_name;
    private TextView tv_price;
    private LinearLayout search_goods_item;

    private GoodsBean goodsBean;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_product = itemView.findViewById(R.id.search_iv_product);
        tv_name = itemView.findViewById(R.id.search_tv_name);
        tv_price = itemView.findViewById(R.id.search_tv_price);
        search_goods_item = itemView.findViewById(R.id.search_goods_item);

        initListener();
    }

    // 设置点击事件 - > 跳转商品界面
    void initListener(){

        search_goods_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), GoodsInfoActivity.class);
                intent.putExtra("goods_bean",goodsBean);
                itemView.getContext().startActivity(intent);
            }
        });


    }




    public ImageView getIv_product() {
        return iv_product;
    }

    public void setIv_product(ImageView iv_product) {
        this.iv_product = iv_product;
    }

    public TextView getTv_name() {
        return tv_name;
    }

    public void setTv_name(TextView tv_name) {
        this.tv_name = tv_name;
    }

    public TextView getTv_price() {
        return tv_price;
    }

    public void setTv_price(TextView tv_price) {
        this.tv_price = tv_price;
    }

    public GoodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }
}
