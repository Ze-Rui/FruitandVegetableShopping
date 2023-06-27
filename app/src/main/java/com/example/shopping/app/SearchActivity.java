package com.example.shopping.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.bean.Response;
import com.example.shopping.home.activity.GoodsListActivity;
import com.example.shopping.home.adapter.GoodsListAdapter;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.home.bean.TypeListBean;
import com.example.shopping.home.uitls.SpaceItemDecoration;
import com.example.shopping.location.adapter.LocationAdapter;
import com.example.shopping.search.adapter.SearchAdapter;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.rv_search_goods)
    RecyclerView rv_search_goods;
    @BindView(R.id.tv_search_home)
    EditText tv_search_home;

    private SearchAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private Context context;

    private String searchText;

    private List<GoodsBean> goodsBeans;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        queue = Volley.newRequestQueue(this);
        context = SearchActivity.this;
        //Butternif 和 Activity 绑定
        ButterKnife.bind(this);

        Intent intent = getIntent();
        searchText = (String) intent.getSerializableExtra("search");
        Log.e("search",searchText);
        tv_search_home.setHint(searchText);

        getDataFromNet();
        initListener();

    }

    /**
     * 请求 获得查询数据
     */
    private void getDataFromNet() {

        String url = getResources().getString(R.string.url)+"goods/search";
        Log.e("url",url);
        JSONObject jt = new JSONObject();
        try {
            jt.put("name",searchText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,jt, new com.android.volley.Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(org.json.JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        bindData(jsonObject.getString("object"));
                    }else {
                        Toast.makeText(SearchActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
                Toast.makeText(SearchActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }

    void bindData(String data){
        goodsBeans = JSON.parseArray(data,GoodsBean.class);

        if (goodsBeans != null){
            mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            mAdapter = new SearchAdapter(context,goodsBeans);
            rv_search_goods.setLayoutManager(mLinearLayoutManager);
            rv_search_goods.setAdapter(mAdapter);
        }

    }


    // 搜索框的查询事件
    private void initListener() {

        // 监听键盘发送
        tv_search_home.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //以下方法防止两次发送请求
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = tv_search_home.getText().toString();

                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.putExtra("search",search);

                    startActivity(intent);
                }
                return false;
            }
        });


    }


}