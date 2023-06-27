package com.example.shopping.community.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.community.bean.History;
import com.example.shopping.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class CommunityFragment extends BaseFragment implements View.OnClickListener {
    ListView dingdan_lv;
    View view;
    private TextView jine;
    private TextView del;
    private TextView fukuan;

    ArrayList<History> historyArrayList ;
    RequestQueue queue;
    ArrayList<String> ids;
    int count = 0;
    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.fragment_community, null);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ids = new ArrayList<>();
        queue = Volley.newRequestQueue(getContext());
        dingdan_lv = view.findViewById(R.id.dingdan_lv);
        jine = (TextView) view.findViewById(R.id.jine);
        del = (TextView) view.findViewById(R.id.del);
        fukuan = (TextView) view.findViewById(R.id.fukuan);
        getdata();
        jine.setText("￥"+count+".00");
        fukuan.setOnClickListener(this);
        del.setOnClickListener(this);
    }
    @Override
    public void initData() {
        super.initData();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("d","12");
        getdata();
    }
    private void getdata() {
        String url = getResources().getString(R.string.url)+"history/selectShop";
        JSONObject jt = new JSONObject();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",0);
        try {
            jt.put("buyer_id",buyer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        JSONArray ja = jsonObject.getJSONArray("object");
                        historyArrayList = new ArrayList<>();
                        for (int i=0;i<ja.length();i++){
                            History history = new History();
                            history.setTime(ja.getJSONObject(i).getString("time"));
                            history.setProduct_id(ja.getJSONObject(i).getString("product_id"));
                            history.setBuyer_id(ja.getJSONObject(i).getInt("buyer_id"));
                            history.setNum(ja.getJSONObject(i).getInt("num"));
                            history.setSeller_id(ja.getJSONObject(i).getInt("seller_id"));
                            history.setShopid(ja.getJSONObject(i).getInt("shopid"));
                            history.setName(ja.getJSONObject(i).getString("name"));
                            history.setPrice(ja.getJSONObject(i).getString("price"));
                            history.setFigure(ja.getJSONObject(i).getString("figure"));
                            historyArrayList.add(history);
                        }
                        dingdan_lv.setAdapter(new MyAdapter());
                    }else {
                        Toast.makeText(getContext(),  "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
            }
        });
        queue.add(jor);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.del:
                delid();
                break;
            case R.id.fukuan:
                fukuanid();
                break;
        }
    }
    private void fukuanid() {
        for (int i = 0;i<ids.size();i++){
            String url = getResources().getString(R.string.url)+"history/revise";
            JSONObject jt = new JSONObject();
            try {
                jt.put("shopid",ids.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("flug").equals("true")){
                            Toast.makeText(getContext(),  "购买成功", Toast.LENGTH_SHORT).show();
                            ids.clear();
                            count = 0;
                            jine.setText("￥"+count);
                            getdata();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("volleyError", volleyError.toString());
                }
            });
            queue.add(jor);
        }
    }

    private void delid() {
        for (int i = 0;i<ids.size();i++){
            String url = getResources().getString(R.string.url)+"history/del";
            JSONObject jt = new JSONObject();
            try {
                jt.put("shopid",ids.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.e("js", jsonObject.toString());
                    try {
                        if (jsonObject.getString("flug").equals("true")){
                            Toast.makeText(getContext(),  "删除成功", Toast.LENGTH_SHORT).show();
                            ids.clear();
                            count = 0;
                            getdata();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("volleyError", volleyError.toString());
                }
            });
            queue.add(jor);
        }
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return historyArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return historyArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_shop, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.titleTv.setText(historyArrayList.get(position).getName());
            holder.pubmanTv.setText("￥"+historyArrayList.get(position).getPrice());
            holder.pubtimeTv.setText(historyArrayList.get(position).getTime());
            // 渲染页面
            Glide.with(getContext()).load(Constants.BASE_REQ + historyArrayList.get(position).getFigure())
                    .into(holder.res);
            holder.goumai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        ids.add(historyArrayList.get(position).getShopid()+"");
                        count += Integer.parseInt(historyArrayList.get(position).getPrice().split("\\.")[0]);
                    }else {
                        ids.remove(historyArrayList.get(position).getShopid()+"");
                        count -= Integer.parseInt(historyArrayList.get(position).getPrice().split("\\.")[0]);
                    }
                    jine.setText("￥"+count+".00");
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        private TextView titleTv;
        private TextView pubmanTv;
        private TextView pubtimeTv;
        private CheckBox goumai;
        ImageView res;


        public ViewHolder(View convertView) {
            titleTv = (TextView) convertView.findViewById(R.id.title_tv);
            pubmanTv = (TextView) convertView.findViewById(R.id.pubman_tv);
            pubtimeTv = (TextView) convertView.findViewById(R.id.pubtime_tv);
            goumai = (CheckBox) convertView.findViewById(R.id.goumai);
            res = convertView.findViewById(R.id.res);
        }
    }
}
