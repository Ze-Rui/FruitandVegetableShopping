package com.example.shopping.home.fragment;


import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.app.LoginActivity;
import com.example.shopping.app.MainActivity;
import com.example.shopping.app.SearchActivity;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.Response;
import com.example.shopping.home.adapter.HomeRecycleAdapter;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.Arrays;

import okhttp3.Call;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG =
            HomeFragment.class.getSimpleName();

    private RecyclerView rvHome;
    private ImageView ib_top;
    private EditText tv_search_home;
    private HomeRecycleAdapter adapter;

    /**
     * 返回得到的数据
     */
    private ResultBeanData.ResultBean resultBean;
    RequestQueue queue;
    @Override
    public View initView() {
        queue = Volley.newRequestQueue(getContext());
        View view = View.inflate(mContext, R.layout.fragment_home,
                null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home =
                view.findViewById(R.id.tv_search_home);
        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();
        initListener();
    }

    public void getDataFromNet(){
        String url = getResources().getString(R.string.url)+"home/select";
        Log.e("url",url);
        JsonObjectRequest jor = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(org.json.JSONObject jsonObject) {
                Log.e("jsonObject",jsonObject.toString());
                try {
                    processData(jsonObject.getString("object"));
//                    if (jsonObject.getString("flug").equals("true")){
//                        processData(jsonObject.getString("object"));
//                    }else {
//                        Toast.makeText(getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
                Toast.makeText(getContext(),"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }

    private void processData(String response) {

        ResultBeanData resultBeanData = new ResultBeanData();
        resultBeanData.setData(JSON.parseObject(response, ResultBeanData.ResultBean.class));
        resultBean = resultBeanData.getData();

        if(resultBean != null){
            // 设置适配器
            adapter = new HomeRecycleAdapter(mContext,resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
            //设置滑动到哪个位置了的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        ib_top.setVisibility(View.GONE);
                    } else {
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            //设置网格布局
            rvHome.setLayoutManager(manager);
        }
    }

    private void initListener() {
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        // 监听键盘发送
        tv_search_home.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //以下方法防止两次发送请求
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = tv_search_home.getText().toString();

                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("search",search);

                    startActivity(intent);
                }
                return false;
            }
        });



    }


}
