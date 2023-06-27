package com.example.shopping.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.pay.PayResult;
import com.example.shopping.pay.SignUtils;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.PayKeys;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    private Context myContext;

    private GoodsBean goodsBean;

    private ImageView order_iv_img;

    private TextView order_tv_name;

    private TextView order_tv_price;


    private Button order_buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        goodsBean = (GoodsBean) intent.getSerializableExtra("goods_bean");

        myContext = this;

        order_iv_img = findViewById(R.id.order_iv_img);
        order_tv_name  = findViewById(R.id.order_tv_name);
        order_tv_price = findViewById(R.id.order_tv_price);
        order_buy = findViewById(R.id.order_buy);

        Glide.with(myContext).load(Constants.BASE_REQ + goodsBean.getFigure()).into(order_iv_img);


        order_tv_name.setText(goodsBean.getName());
        order_tv_price.setText("￥"+goodsBean.getCover_price());

        order_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog();
            }
        });

        // 标题栏，返回功能
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


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


    // 弹出支付界面
    public void payDialog(){
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        View view = View.inflate(OrderActivity.this,R.layout.item_pay,null);

        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);

        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.iv_close_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this,"取消支付",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.pay_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.pay_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }



}