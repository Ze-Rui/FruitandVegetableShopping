package com.example.shopping.app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.bean.Response;
import com.example.shopping.chat.bean.ChatItem;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.pay.PayResult;
import com.example.shopping.pay.SignUtils;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.PayKeys;
import com.example.shopping.utils.ToGoodsInfoActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.Call;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvLike;
    private Button btnBuy,shop;
    private LinearLayout ll_root;

    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home,tv_num;
    private Button btn_more;


    private Context myContext;
    // 传递的商品数据
    private GoodsBean goodsBean;

    private String seller_name;
    private int seller_id;
    RequestQueue queue;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2021-04-11 13:10:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        queue = Volley.newRequestQueue(this);
        ibGoodInfoBack = (ImageButton)findViewById( R.id.ib_good_info_back );
        ibGoodInfoMore = (ImageButton)findViewById( R.id.ib_good_info_more );
        ivGoodInfoImage = (ImageView)findViewById( R.id.iv_good_info_image );
        tvGoodInfoName = (TextView)findViewById( R.id.tv_good_info_name );
        tvGoodInfoDesc = (TextView)findViewById( R.id.tv_good_info_desc );
        tvGoodInfoPrice = (TextView)findViewById( R.id.tv_good_info_price );
        tvGoodInfoStore = (TextView)findViewById( R.id.tv_good_info_store );
        wbGoodInfoMore = (WebView)findViewById( R.id.wb_good_info_more );
        llGoodsRoot = (LinearLayout)findViewById( R.id.ll_goods_root );
        tvGoodInfoCallcenter = (TextView)findViewById( R.id.tv_good_info_callcenter );
        tvGoodInfoCollection = (TextView)findViewById( R.id.tv_good_info_collection );
        tvLike = (TextView)findViewById( R.id.tv_like );
        tv_num = findViewById(R.id.tv_num);
        btnBuy = findViewById(R.id.btn_good_info_buy);
        shop = findViewById(R.id.shop);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        tv_more_share = findViewById(R.id.tv_more_share);
        tv_more_search = findViewById(R.id.tv_more_search);
        tv_more_home = findViewById(R.id.tv_more_home);
        btn_more = findViewById(R.id.btn_more);


        setListener();
    }
    //设置 监听器
    private void setListener(){

        // 加购
        shop.setOnClickListener(this);
        // 返回
        ibGoodInfoBack.setOnClickListener( this );
        // 更多
        ibGoodInfoMore.setOnClickListener( this );
        // 联系商家
        tvGoodInfoCallcenter.setOnClickListener(this);
        // 收藏
        tvGoodInfoCollection.setOnClickListener(this);
        // 点赞
        tvLike.setOnClickListener(this);

        // 分享
        tv_more_share.setOnClickListener(this);
        // 搜索
        tv_more_search.setOnClickListener(this);
        // 主页面
        tv_more_home.setOnClickListener(this);

        // 购买
        btnBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toPay(v);
            }

        });
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2021-04-11 13:10:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == ibGoodInfoBack ) {
            // Handle clicks for ibGoodInfoBack 返回
            finish();
            // 显示更多操作
        } else if ( v == ibGoodInfoMore ) {
            if (ll_root.getVisibility() == View.VISIBLE) {
                ll_root.setVisibility(View.GONE);
            } else {
                ll_root.setVisibility(View.VISIBLE);
            }
        } else if (v == btn_more) {
            ll_root.setVisibility(View.GONE);
        } else if ( v == tvGoodInfoCallcenter){
            Intent intent = new Intent(this, ChatActivity.class);
            // 封装成ChatItem
            ChatItem chatItem = new ChatItem(seller_id,seller_name,goodsBean.getFigure(),goodsBean.getProduct_id());
            intent.putExtra("item",chatItem);
            startActivity(intent);

        } else if ( v == tvGoodInfoCollection){

            Drawable drawable = getResources().getDrawable(R.drawable.good_collected);//得到drawable
            tvGoodInfoCollection.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);

        }  else if ( v == tvLike){

            Drawable drawable = getResources().getDrawable(R.drawable.icon_good_like);//得到drawable
            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);

        } else if ( v == tv_more_share){
            Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
        } else if ( v == tv_more_search){
            Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
        } else if ( v == tv_more_home){
            Constants.isBackHome = true;
            finish();
        }else if (v.getId()== R.id.shop){
            goodsBean = (GoodsBean) getIntent().getSerializableExtra("goods_bean");
            String p = goodsBean.getCover_price().split("/")[0];
            goodsBean.setCover_price(p);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goods",goodsBean);
            intent.putExtra("goods",bundle);
            intent.putExtra("seller_id",seller_id);
            intent.setClass(GoodsInfoActivity.this, GouWuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goods_info);

        findViews();

        // 接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goods_bean");

        // 获得Seller信息
        getSeller(goodsBean.getProduct_id());

        // 渲染页面
        Glide.with(this).load(Constants.BASE_REQ + goodsBean.getFigure())
                .into(ivGoodInfoImage);
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());
        tv_num.setText("库存："+goodsBean.getNum()+"份");

        myContext = this;
    }


    public void getSeller(String product_id){
        String url = getResources().getString(R.string.url)+"user/getByProductId";
        JSONObject jt = new JSONObject();
        try {
            jt.put("product_id",product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("json",jsonObject.toString());
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        seller_name = jsonObject.getJSONObject("object").getString("name");
                        seller_id = jsonObject.getJSONObject("object").getInt("id");
                        tvGoodInfoStore.setText(seller_name);
                    }else {
                        Toast.makeText(GoodsInfoActivity.this,  "查询失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(GoodsInfoActivity.this,  "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }

    /**
     * 跳转到订单页面
     */
    public void toPay(View v) {
        Intent intent = new Intent(GoodsInfoActivity.this, OrderActivity.class);
        intent.putExtra("goods_bean",goodsBean);
        startActivity(intent);
    }




}