package com.example.shopping.utils;

import android.content.Context;
import android.content.Intent;

import com.example.shopping.app.GoodsInfoActivity;
import com.example.shopping.home.bean.GoodsBean;

public class ToGoodsInfoActivity {

    static final String GOODS_BEAN = "goods_bean";

    // 跳转到商品详情
    public static void startGoodsInfoActivity(Context mContext, GoodsBean goodsBean){
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }
}
