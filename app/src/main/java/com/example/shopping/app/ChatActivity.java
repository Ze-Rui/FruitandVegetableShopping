package com.example.shopping.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.chat.bean.ChatItem;
import com.example.shopping.chat.bean.PersonChat;
import com.example.shopping.chat.adapter.ChatAdapter;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ChatActivity extends AppCompatActivity {

    private View view;

    private Context context;
    private ImageView iv_product;
    private TextView tv_name;
    private TextView tv_price;
    private ListView lv_chat_dialog;
    private Button btn_chat_message_send;
    private EditText et_chat_message;

    private GoodsBean goodsBean;
    private String seller_id;
    private User me;

    private List<PersonChat> chatList;
    private ChatItem item;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.context = context;

        iv_product = findViewById(R.id.iv_product);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        lv_chat_dialog = findViewById(R.id.lv_chat_dialog);
        btn_chat_message_send = findViewById(R.id.btn_chat_message_send);
        et_chat_message = findViewById(R.id.et_chat_message);
        me = MyApplication.getMe();
        Intent intent = getIntent();
        item = (ChatItem) intent.getSerializableExtra("item");

        System.out.println(item);
        seller_id = String.valueOf(item.getSeller_id());

        initChatItem();


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



    public View initView() {


        // 获得intent中的值
        if (item != null){

            String productId = item.getProductId();
            OkHttpUtils.get()
                    .url(Constants.Goods_get)
                    .addParams("product_id",productId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("TAG", "联网失败" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Response result = JSON.parseObject(response, Response.class);
                            // 将返回的商品信息显示到页面
                            if (result != null){
                                Map<String,String> map = JSON.parseObject(result.getData().toString(), Map.class);
                                System.out.println(map);
                                goodsBean = new GoodsBean(map.get("cover_price"),map.get("figure"),map.get("name"),map.get("product_id"),map.get("num"));

                                Glide.with(ChatActivity.this).load(Constants.BASE_REQ + goodsBean.getFigure()).into(iv_product);
                                tv_name.setText(goodsBean.getName());
                                tv_price.setText("￥ "+goodsBean.getCover_price());
                            }
                        }
                    });


        }


        /**
         *setAdapter
         */
        chatAdapter = new ChatAdapter(this, chatList);
        lv_chat_dialog.setAdapter(chatAdapter);
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonChat personChat = new PersonChat();
                //代表自己发送
                personChat.setIsMeSend("1");
                //得到发送内容
                personChat.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });

        return view;
    }

    /**
     * 初始化聊天记录
     */
    public void initChatItem(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",0);
        Map<String,String> map =new HashMap<>();
        map.put("user_id", String.valueOf(buyer_id));
        map.put("seller_id",seller_id);

        OkHttpUtils.get()
                .url(Constants.ChatContents_get)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        // 将得到的聊天信息封装到页面
                        Response result = JSON.parseObject(response, Response.class);
                        if (result != null){
                            chatList = JSON.parseArray(result.getData().toString(), PersonChat.class);
                            initView();
                        }
                    }
                });


    }

    private ChatAdapter chatAdapter;

    /**
     * 集合
     */
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;

                default:
                    break;
            }
        };
    };


}
