package com.example.shopping.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.community.bean.History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {

    private ListView rvHistory;

    RequestQueue queue;
    ArrayList<History> historyArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rvHistory = findViewById(R.id.rv_history);
        queue = Volley.newRequestQueue(this);
        // 标题栏，返回功能
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getdata();
    }

    private void getdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        int buyer_id = sharedPreferences.getInt("buyer_id",1);

        String url = getResources().getString(R.string.url)+"history/select";
        JSONObject jt = new JSONObject();
        try {
            jt.put("buyer_id",buyer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        Toast.makeText(HistoryActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                        historyArrayList = new ArrayList<>();
                        JSONArray ja = jsonObject.getJSONArray("object");
                        for (int i=0;i<ja.length();i++){
                            History history = new History();
                            history.setFigure(ja.getJSONObject(i).getString("figure"));
                            history.setName(ja.getJSONObject(i).getString("name"));
                            history.setProduct_id(ja.getJSONObject(i).getString("product_id"));
                            history.setTime(ja.getJSONObject(i).getString("time"));
                            history.setPrice(ja.getJSONObject(i).getString("price"));
                            history.setSeller_id(ja.getJSONObject(i).getInt("seller_id"));
                            history.setShopid(ja.getJSONObject(i).getInt("shopid"));
                            history.setBuyer_id(ja.getJSONObject(i).getInt("buyer_id"));
                            historyArrayList.add(history);
                        }
                        rvHistory.setAdapter(new MyAdapter());
                    }else {
                        Toast.makeText(HistoryActivity.this,"暂无订单记录",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("volleyError", e.toString());
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
                Toast.makeText(HistoryActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(HistoryActivity.this, R.layout.dingdan_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.titleItemFragment1.setText(historyArrayList.get(position).getName());
            holder.pubmanItemFragment1.setText("￥"+historyArrayList.get(position).getPrice());
            holder.pubtimeItemFragment1.setText(historyArrayList.get(position).getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            try {
                float cha = comparePastDate(historyArrayList.get(position).getTime(),sdf.format(date));
                if (cha>24){
                    holder.status.setText("已送达");
                    holder.status.setTextColor(R.color.orange);
                }else if (cha>1){
                    holder.status.setText("已发货");
                    holder.status.setTextColor(R.color.white);
                }else {
                    holder.status.setText("未发货");
                    holder.status.setTextColor(R.color.grey);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }
    class ViewHolder {
        TextView titleItemFragment1;
        TextView pubmanItemFragment1;
        TextView pubtimeItemFragment1;
        private TextView status;

        public ViewHolder(View convertView) {
            titleItemFragment1 = (TextView) convertView.findViewById(R.id.title_item_fragment1);
            pubmanItemFragment1 = (TextView) convertView.findViewById(R.id.pubman_item_fragment1);
            pubtimeItemFragment1 = (TextView) convertView.findViewById(R.id.pubtime_item_fragment1);
            status = (TextView) convertView.findViewById(R.id.status);
        }
    }
    public static float comparePastDate(String oldDate,String nowDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        Date old = sdf.parse(oldDate);
        calendar.setTime(old);
        Long oTime = calendar.getTimeInMillis();

        Date now = sdf.parse(nowDate);
        calendar.setTime(now);
        Long nTime = calendar.getTimeInMillis();
        return (nTime - oTime)/(3600F * 1000);
    }
}
