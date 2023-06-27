package com.example.shopping.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.home.bean.GoodsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GouWuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleTv;
    private TextView pubmanTv;
    private TextView pubtimeTv;
    private TextView contentTv;
    private CheckBox goumai;
    private TextView jian;
    private TextView shu;
    private TextView jia;
    private CheckBox goumai2;
    private TextView jine;
    private TextView del;
    LinearLayout vis;
    boolean flug = false;
    private TextView shop;
    GoodsBean goods;
    RequestQueue queue;
    private int seller_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou_wu);

        queue = Volley.newRequestQueue(this);
        titleTv = (TextView) findViewById(R.id.title_tv);
        pubmanTv = (TextView) findViewById(R.id.pubman_tv);
        pubtimeTv = (TextView) findViewById(R.id.pubtime_tv);
        contentTv = (TextView) findViewById(R.id.content_tv);
        goumai = findViewById(R.id.goumai);
        shop = (TextView) findViewById(R.id.shop);
        jian = (TextView) findViewById(R.id.jian);
        shu = (TextView) findViewById(R.id.shu);
        jia = (TextView) findViewById(R.id.jia);
        goumai2 =  findViewById(R.id.goumai2);
        jine = (TextView) findViewById(R.id.jine);
        del = (TextView) findViewById(R.id.del);
        vis = findViewById(R.id.vis);

//     获得ShowlifeguideActivity 传递i值
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("goods");
        goods = (GoodsBean) bundle.getSerializable("goods");
        seller_id = intent.getIntExtra("seller_id",0);
//    通过item值,将对应的信息填入到视图中
        titleTv.setText(goods.getName());
        pubtimeTv.setText("￥"+goods.getCover_price());

        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        del.setOnClickListener(this);
        shop.setOnClickListener(this);
// 通过goumai按钮的点击，来判断合计的金额
        goumai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (goumai.isChecked()){
                    goumai2.setChecked(true);
                    int dan = (int) Double.parseDouble(goods.getCover_price());
                    int ge = Integer.parseInt(shu.getText().toString());
                    int ji = 0;
                    if (ge >= 10){
                        ji = (int) (dan * ge * 0.7);
                    }else if (ge >= 5){
                        ji = (int) (dan * ge * 0.8);
                    }else if (ge >= 3){
                        ji = (int) (dan * ge * 0.9);
                    }else {
                        ji = dan * ge;
                    }
                    jine.setText("合计：￥"+ji+".00");
                }else {
                    goumai2.setChecked(false);
                    jine.setText("合计：￥0.00");
                }
            }
        });
        goumai2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (goumai2.isChecked()){
                    goumai.setChecked(true);
                    int dan = (int) Double.parseDouble(goods.getCover_price());
                    int ge = Integer.parseInt(shu.getText().toString());
                    int ji = 0;
                    if (ge >= 10){
                        ji = (int) (dan * ge * 0.7);
                    }else if (ge >= 5){
                        ji = (int) (dan * ge * 0.8);
                    }else if (ge >= 3){
                        ji = (int) (dan * ge * 0.9);
                    }else {
                        ji = dan * ge;
                    }
                    jine.setText("合计：￥"+ji+".00");
                }else {
                    goumai.setChecked(false);
                    jine.setText("合计：￥0.00");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop:
//        点击付款之后，就将订单内容存储到sqlite内
                if (goumai.isChecked()){
                    String[] s = jine.getText().toString().split("￥|\\.");
                    putdata(goods.getProduct_id(),goods.getName(),seller_id,s[1],goods.getFigure());
                }else {
                    Toast.makeText(GouWuActivity.this,"请选择要加入购物车的商品",Toast.LENGTH_SHORT).show();
                }
                break;
//         减少购买数量
            case R.id.jian:
                if (goumai.isChecked()){
                    Toast.makeText(GouWuActivity.this,"请先取消选择，再更改数量",Toast.LENGTH_SHORT).show();
                }else {
                    if (Integer.parseInt(shu.getText().toString())>0){
                        shu.setText(Integer.parseInt(shu.getText().toString())-1+"");
                    }
                }
                break;
//         增加购买数量
            case R.id.jia:
                if (goumai.isChecked()){
                    Toast.makeText(GouWuActivity.this,"请先取消选择，再更改数量",Toast.LENGTH_SHORT).show();
                }else {
                    shu.setText(Integer.parseInt(shu.getText().toString())+1+"");
                }
                break;
            case R.id.del:
                if (flug){
                    vis.setVisibility(View.VISIBLE);
                }else {
                    vis.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void putdata(String product_id, String name, int seller_id, String price, String figure) {
        int ge = Integer.parseInt(shu.getText().toString());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        String url = getResources().getString(R.string.url)+"history/add";
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",0);
        JSONObject jt = new JSONObject();
        try {
            jt.put("product_id",product_id);
            jt.put("seller_id",seller_id);
            jt.put("name",name);
            jt.put("buyer_id",buyer_id);
            jt.put("price",price);
            jt.put("time",time);
            jt.put("figure",figure);
            jt.put("state",1);
            jt.put("num",ge);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        Toast.makeText(GouWuActivity.this,"加购成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(GouWuActivity.this,  "加购失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(GouWuActivity.this,  "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }
//    private void putdata(String sid, String da, String name, String user, int toString, int price, int i) {
//        String url = getResources().getString(R.string.url)+"history/add";
//        JSONObject jt = new JSONObject();
//        final int status = i;
//        try {
//            jt.put("gid",sid);
//            jt.put("date",da);
//            jt.put("gname",name);
//            jt.put("uname",user);
//            jt.put("num",toString);
//            jt.put("price",price);
//            jt.put("status",i);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    if (jsonObject.getString("flug").equals("true")){
//                        if (status == 0){
//                            Toast.makeText(GouWuActivity.this,"已加入购物车",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(GouWuActivity.this,"购买成功",Toast.LENGTH_SHORT).show();
//                        }
//                        Intent intent = new Intent();
//                        intent.setClass(GouWuActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }else {
//                        Toast.makeText(GouWuActivity.this,  "购买失败", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(GouWuActivity.this,  "网络连接失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(jor);
//    }
}
