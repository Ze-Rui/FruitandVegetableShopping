package com.example.shopping.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.app.GoodsInfoActivity;
import com.example.shopping.home.adapter.ExpandableListViewAdapter;
import com.example.shopping.home.adapter.GoodsListAdapter;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.home.bean.TypeListBean;
import com.example.shopping.utils.Constants;
import com.example.shopping.home.uitls.SpaceItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

// 商品列表页面
public class GoodsListActivity extends Activity implements View.OnClickListener {

    private LinearLayout llGoodsListHead;
    private ImageButton ibGoodsListBack;
    private TextView tvGoodsListSearch;
    private ImageButton ibGoodsListHome;
    private TextView tvGoodsListSort;
    private LinearLayout llGoodsListPrice;
    private TextView tvGoodsListPrice;
    private ImageView ivGoodsListArrow;
    private TextView tvGoodsListSelect;
    private RecyclerView recyclerview;
    private ExpandableListView listView;

    /*    private static final int DEFAULE_STATE = 1;
        private static final int ASC_STATE = 2;
        private static final int DESC_STATE = 3;*/
    private int childP;
    private int groupP;

    private int click_count = 0;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;

    private LinearLayout ll_select_root;
    private LinearLayout ll_price_root;
    private LinearLayout ll_theme_root;
    private LinearLayout ll_type_root;

    private ImageButton ib_drawer_layout_back;
    private Button btn_drawer_layout_confirm;
    private Button btn_drawer_layout_cancel;

    private Button btn_drawer_type_confirm;
    private Button btn_drawer_type_cancel;

    private Button btn_drawer_theme_confirm;
    private Button btn_drawer_theme_cancel;

    private RelativeLayout rl_select_price;
    private RelativeLayout rl_select_recommend_theme;
    private RelativeLayout rl_select_type;
    private RelativeLayout rl_price_30_50;
    private RelativeLayout rl_theme_note;
    private int position;
    private List<TypeListBean.ResultBean> page_data;
    private GoodsListAdapter adapter1;
    private DrawerLayout dl_left;

    private void findViews() {
        llGoodsListHead = (LinearLayout) findViewById(R.id.ll_goods_list_head);
        ibGoodsListBack = (ImageButton) findViewById(R.id.ib_goods_list_back);
        tvGoodsListSearch = (TextView) findViewById(R.id.tv_goods_list_search);
        ibGoodsListHome = (ImageButton) findViewById(R.id.ib_goods_list_home);
        tvGoodsListSort = (TextView) findViewById(R.id.tv_goods_list_sort);
        llGoodsListPrice = (LinearLayout) findViewById(R.id.ll_goods_list_price);
        tvGoodsListPrice = (TextView) findViewById(R.id.tv_goods_list_price);
        ivGoodsListArrow = (ImageView) findViewById(R.id.iv_goods_list_arrow);
        tvGoodsListSelect = (TextView) findViewById(R.id.tv_goods_list_select);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        ll_select_root = (LinearLayout) findViewById(R.id.ll_select_root);
        ll_price_root = (LinearLayout) findViewById(R.id.ll_price_root);
        ll_theme_root = (LinearLayout) findViewById(R.id.ll_theme_root);
        ll_type_root = (LinearLayout) findViewById(R.id.ll_type_root);

        ib_drawer_layout_back = (ImageButton) findViewById(R.id.ib_drawer_layout_back);
        btn_drawer_layout_confirm = (Button) findViewById(R.id.btn_drawer_layout_confirm);
        btn_drawer_layout_cancel = (Button) findViewById(R.id.btn_drawer_layout_cancel);
        btn_drawer_type_confirm = (Button) findViewById(R.id.btn_drawer_type_confirm);
        btn_drawer_type_cancel = (Button) findViewById(R.id.btn_drawer_type_cancel);
        btn_drawer_theme_confirm = (Button) findViewById(R.id.btn_drawer_theme_confirm);
        btn_drawer_theme_cancel = (Button) findViewById(R.id.btn_drawer_theme_cancel);

        rl_select_price = (RelativeLayout) findViewById(R.id.rl_select_price);
        rl_select_recommend_theme = (RelativeLayout) findViewById(R.id.rl_select_recommend_theme);
        rl_select_type = (RelativeLayout) findViewById(R.id.rl_select_type);
        rl_price_30_50 = (RelativeLayout) findViewById(R.id.rl_price_30_50);
        rl_theme_note = (RelativeLayout) findViewById(R.id.rl_theme_note);
        dl_left = (DrawerLayout) findViewById(R.id.dl_left);

        ibGoodsListBack.setOnClickListener(this);
        ibGoodsListHome.setOnClickListener(this);
        tvGoodsListSearch.setOnClickListener(this);
        llGoodsListPrice.setOnClickListener(this);
        tvGoodsListSort.setOnClickListener(this);
        tvGoodsListSelect.setOnClickListener(this);
        ib_drawer_layout_back.setOnClickListener(this);

        rl_select_price.setOnClickListener(this);
        rl_select_recommend_theme.setOnClickListener(this);
        rl_select_type.setOnClickListener(this);

        btn_drawer_layout_confirm.setOnClickListener(this);
        btn_drawer_layout_cancel.setOnClickListener(this);
        btn_drawer_type_confirm.setOnClickListener(this);
        btn_drawer_type_cancel.setOnClickListener(this);
        btn_drawer_theme_confirm.setOnClickListener(this);
        btn_drawer_theme_cancel.setOnClickListener(this);

        rl_price_30_50.setOnClickListener(this);
        rl_theme_note.setOnClickListener(this);

        listView = (ExpandableListView) findViewById(R.id.expandableListView);

    }


    @Override
    public void onClick(View v) {
        if (v == ibGoodsListBack) {
            finish();
        } else if (v == ibGoodsListHome) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            Constants.isBackHome = true;
            finish();
        } else if (v == tvGoodsListSearch) {
            Toast.makeText(GoodsListActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == llGoodsListPrice) {
            //价格点击事件
            click_count++;
            //综合排序变为默认
            tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
            //价格红
            tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
            if (click_count % 2 == 1) {
                // 箭头向下红
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
            } else {
                // 箭头向上红
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
            }
        } else if (v == tvGoodsListSort) {
            //综合排序点击事件
            click_count = 0;
            ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
            tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
            tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
        } else if (v == tvGoodsListSelect) {
            //筛选的点击事件
            tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
            dl_left.openDrawer(Gravity.RIGHT);

        } else if (v == rl_select_price) {
            //价格筛选的页面
            ll_price_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showPriceLayout();
        } else if (v == rl_select_recommend_theme) {
            ll_theme_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showThemeLayout();
        } else if (v == rl_select_type) {
            ll_type_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.GONE);

            showTypeLayout();
        } else if (v == ib_drawer_layout_back) {
            dl_left.closeDrawers();
        } else if (v == btn_drawer_layout_cancel) {
            Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();

            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        } else if (v == btn_drawer_layout_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == rl_price_30_50) {
            Toast.makeText(GoodsListActivity.this, "123123123", Toast.LENGTH_SHORT).show();
        } else if (v == rl_theme_note) {
            Toast.makeText(GoodsListActivity.this, "123123123", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_type_cancel) {
            Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        } else if (v == btn_drawer_theme_confirm) {
            Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
        } else if (v == btn_drawer_theme_cancel) {
            ll_select_root.setVisibility(View.VISIBLE);
            ib_drawer_layout_back.setVisibility(View.VISIBLE);
            showSelectorLayout();
        }


    }

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        queue = Volley.newRequestQueue(this);
        findViews();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        getDataFromNet();
        ll_select_root.setVisibility(View.VISIBLE);
        ib_drawer_layout_back.setVisibility(View.VISIBLE);
        showSelectorLayout();

        initListener();
    }

    private void initListener() {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(GoodsListActivity.this, "childPosition" + childPosition, Toast.LENGTH_SHORT).show();
                childP = childPosition;
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(GoodsListActivity.this, "groupPosition" + groupPosition, Toast.LENGTH_SHORT).show();
                groupP = groupPosition;
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GoodsListActivity.this, "position---" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //筛选页面
    private void showSelectorLayout() {
        ll_price_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //价格页面
    private void showPriceLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_price_root.setVisibility(View.GONE);
        ll_type_root.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        ll_select_root.setVisibility(View.GONE);
        ll_price_root.setVisibility(View.GONE);
        ll_theme_root.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
        adapter = new ExpandableListViewAdapter(this, group, child);
        listView.setAdapter(adapter);
    }

    private void initExpandableListView() {
        group = new ArrayList<>();
        child = new ArrayList<>();
        //去掉默认箭头
        listView.setGroupIndicator(null);
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});

        // 这里是控制如果列表没有孩子菜单不展开的效果
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {// isEmpty没有
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }

    public void getDataFromNet() {
        String url = getResources().getString(R.string.url)+"goods/getByChannelId";
        Log.e("url",url);
        JSONObject jt = new JSONObject();
        try {
            jt.put("channel_id",position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,jt, new com.android.volley.Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(org.json.JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        processData(jsonObject.getString("object"));
                        GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
                        recyclerview.setLayoutManager(manager);
                        adapter1 = new GoodsListAdapter(GoodsListActivity.this, page_data);
//                        recyclerview.addItemDecoration(new DividerItemDecoration(GoodsListActivity.this, manager.getOrientation()));
                        recyclerview.addItemDecoration(new SpaceItemDecoration(10));
                        recyclerview.setAdapter(adapter1);

                        adapter1.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
                            @Override
                            public void setOnItemClickListener(TypeListBean.ResultBean data) {
                                String name = data.getName();
                                String cover_price = data.getCover_price();
                                String figure = data.getFigure();
                                String product_id = data.getProduct_id();
                                GoodsBean goodsBean = new GoodsBean(cover_price,figure,name, product_id,data.getNum());
                                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                                intent.putExtra("goods_bean", goodsBean);
                                startActivity(intent);
                            }
                        });
                    }else {
                        Toast.makeText(GoodsListActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
                Toast.makeText(GoodsListActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);

    }

    private void processData(String response) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TypeListBean.ResultBean>>() {}.getType();
        TypeListBean result = new TypeListBean();
        result.setData(gson.fromJson(response, listType));
        page_data = result.getData();

        for(int i = 0;i < page_data.size();i++){
            System.out.println(page_data.get(i));
        }
    }

}
