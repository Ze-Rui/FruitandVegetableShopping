package com.example.shopping.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopping.R;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.community.adapter.HotPostListViewAdapter;
import com.example.shopping.community.bean.HotPostBean;
import com.example.shopping.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/10/6.
 */
public class HotPostFragment extends BaseFragment {
    private ListView lv_hot_post;
    private List<HotPostBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
        lv_hot_post = (ListView) view.findViewById(R.id.lv_hot_post);
        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        Map requestPar = new HashMap<>();
        requestPar.put("is_hot","1");
        OkHttpUtils
                .get()
                .url(Constants.Dynamic_get)
                .params(requestPar)
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
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                processData(response);
                HotPostListViewAdapter adapter = new HotPostListViewAdapter(mContext, result);
                lv_hot_post.setAdapter(adapter);
            }

        }

    }

    private void processData(String json) {
        Gson gson = new Gson();
        HotPostBean hotPostBean = gson.fromJson(json, HotPostBean.class);
        result = hotPostBean.getData();
    }
}
