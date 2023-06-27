package com.example.shopping.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.search.adapter.SearchAdapter;
import com.example.shopping.user.adapter.MySellerAdapter;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MySellerActivity extends AppCompatActivity {

    @BindView(R.id.my_seller_goods)
    RecyclerView my_seller_goods;

    Context mContext;

    private User me;

    private List<GoodsBean> goodsBeans;
    private LinearLayoutManager mLinearLayoutManager;
    private MySellerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myseller);
        //Butternif 和 Activity 绑定
        ButterKnife.bind(this);
        mContext = this;
        me = MyApplication.getMe();
        // 标题栏，返回功能
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getMySeller();
    }
    /**
     * 返回功能
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 获得我的商品
    public void getMySeller(){
        String user_id = String.valueOf(me.getId());
        OkHttpUtils.get()
                .addParams("user_id",user_id)
                .url(Constants.GoodsList_ByUserId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Response res = JSON.parseObject(response, Response.class);
                        if (res != null){
                            // 绑定数据
                            bindData(res);
                        }
                    }
                });


    }

    private void bindData(Response res) {
        goodsBeans = JSON.parseArray(res.getData().toString(),GoodsBean.class);

        if (goodsBeans != null){
            mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            mAdapter = new MySellerAdapter(mContext, goodsBeans);
            my_seller_goods.setLayoutManager(mLinearLayoutManager);
            my_seller_goods.setAdapter(mAdapter);
        }
    }
}