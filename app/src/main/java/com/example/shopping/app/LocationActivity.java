package com.example.shopping.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.location.bean.Location;
import com.example.shopping.location.adapter.LocationAdapter;
import com.example.shopping.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

public class LocationActivity extends AppCompatActivity {


    RecyclerView rv_location;
    private Button location_add;
    private LocationAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private Context context;
    /**
     * 返回得到的数据
     */
    private List<Location> resultBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        rv_location = findViewById(R.id.rv_location);
        location_add = findViewById(R.id.location_add);
        context = LocationActivity.this;
        // 标题栏，返回功能
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getDataFromNet();
        initListener();
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
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("d","d");
        getDataFromNet();
    }
    public void getDataFromNet(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",0);
        OkHttpUtils
                .get()
                .url(Constants.Location_get)
                .addParams("user_id", String.valueOf(buyer_id))
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }
        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                processData(response);
            }
        }
    }

    private void initListener() {

        location_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_location_dialog,null);
                EditText userName = view.findViewById(R.id.location_userName);

                EditText phone = view.findViewById(R.id.location_phone);

                EditText address = view.findViewById(R.id.location_address);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("增加收货信息").setIcon(android.R.drawable.ic_dialog_info).setView(view);

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        int buyer_id = sharedPreferences.getInt("buyer_id",0);
                        Location add = new Location(0,userName.getText().toString(),phone.getText().toString(),address.getText().toString(),buyer_id);
                        HashMap<String, Object> jsonString = new HashMap<>();
                        jsonString.put("location",add);
                        Log.e("json","json = " + new Gson().toJson(jsonString));
                        OkHttpUtils
                                .postString()
                                .url(Constants.Location_add)
                                .content(new Gson().toJson(jsonString))
                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                .build()
                                .execute(new StringCallback(){

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Log.e("addLocation",e.getMessage());
                                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        if(response != null){
                                            Response result = JSON.parseObject(response, Response.class);

                                            if(result.getCode() == 200000){
                                                Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                                                userName.setText(add.getUserName());
                                                phone.setText(add.getPhone());
                                                address.setText(add.getLocation());
                                                getDataFromNet();
                                            }

                                        }
                                    }
                                });

                    }});
                builder.show();
            }
        });

    }

    private void processData(String response) {

        Response res = JSON.parseObject(response, Response.class);
        resultBeans = JSON.parseArray(res.getData().toString(), Location.class);

        if(resultBeans != null){
            mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            mAdapter = new LocationAdapter(context,resultBeans);
            rv_location.setLayoutManager(mLinearLayoutManager);
            rv_location.setAdapter(mAdapter);
        }
    }
}