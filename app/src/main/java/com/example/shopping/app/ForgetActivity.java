package com.example.shopping.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.bean.Response;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.CountDownTimerUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;


public class  ForgetActivity extends AppCompatActivity {

    private TextView textView;

    private TextView forget_email;

    private TextView forget_code;

    private Button btn_verification;

    private String forget_response = null;
    // 验证结果
    private boolean requestResult = true;

    private String email;

    RequestQueue queue;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        queue = Volley.newRequestQueue(this);
        textView =  findViewById(R.id.code);
        forget_email = findViewById(R.id.email);
        forget_code = findViewById(R.id.forget_code);
        btn_verification = findViewById(R.id.btn_verification);

        // 设置点击事件
        initView();

    }

    protected void initView() {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
                mCountDownTimerUtils.start();
            }
        });


        // 判断是否验证成功
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = forget_email.getText().toString();
                sets(email);
            }
        });

    }


    private void sets(String email){
        String url = getResources().getString(R.string.url)+"user/find";
        JSONObject jt = new JSONObject();
        try {
            jt.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        Intent intent = new Intent(ForgetActivity.this, ModifyActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ForgetActivity.this,  "邮箱验证成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ForgetActivity.this,  "该邮箱不存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
            }
        });
        queue.add(jor);
    }
}
