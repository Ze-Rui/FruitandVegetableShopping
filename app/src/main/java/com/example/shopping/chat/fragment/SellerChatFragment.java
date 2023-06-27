package com.example.shopping.chat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.app.ChatActivity;
import com.example.shopping.app.MyApplication;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.chat.adapter.ChatItemAdapter;
import com.example.shopping.chat.bean.ChatItem;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

public class SellerChatFragment extends BaseFragment {

    // 存储item数据
    private List<ChatItem> chatList = new ArrayList<>();
    private View view;
    private Context context;
    private User me;

    private ListView list_chat;

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_sellerchat,null);
        context = getContext();
        list_chat = view.findViewById(R.id.list_chat);
        me = MyApplication.getMe();
        return view;
    }

    /**
     * 初始数据
     */
    @Override
    public void initData() {
        // 通过 用户id 查找聊天信息
        initList();
    }


    private void initList(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",0);
        OkHttpUtils.get()
                .url(Constants.ChatSellers_get)
                .addParams("user_id", String.valueOf(buyer_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage()+"的"+Constants.ChatSellers_get);
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null){
                            Response result = JSON.parseObject(response, Response.class);
                            if(result.getCode() == 200000){
                                System.out.println("data = " + result.getData());
                                chatList = JSON.parseArray(result.getData().toString(),ChatItem.class);
                                // 设置Adapter
                                ChatItemAdapter adapter = new ChatItemAdapter(context, R.layout.chat_item, chatList);
                                list_chat.setAdapter(adapter);

                                list_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        ChatItem item=chatList.get(position);
                                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                                        intent.putExtra("item",item);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                });
    }
}
