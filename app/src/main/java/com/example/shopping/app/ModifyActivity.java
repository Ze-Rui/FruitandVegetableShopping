package com.example.shopping.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

import okhttp3.Call;

public class ModifyActivity extends AppCompatActivity {

    private EditText ed_password;
    private EditText ed_toPassword;
    private TextView textView;

    private Button btn_modify;

    private String result;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        queue = Volley.newRequestQueue(this);
        ed_password = findViewById(R.id.ed_password);
        ed_toPassword = findViewById(R.id.ed_toPassword);
        btn_modify = findViewById(R.id.btn_modify);

        textView = findViewById(R.id.warning);

        // 点击提交事件
        Intent intent = getIntent();
        String email = intent.getSerializableExtra("email").toString();
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                String password = ed_password.getText().toString().trim();
                String toPassword = ed_toPassword.getText().toString().trim();

                Log.e("pa",password+"A");
                // 两次密码相等
                if(password.equals(toPassword)){
                    sets(email,password);
                }else {
                    Toast.makeText(ModifyActivity.this,  "两次密码输入不同", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sets(String email, String password){
        String url = getResources().getString(R.string.url)+"user/update";
        JSONObject jt = new JSONObject();
        try {
            jt.put("email",email);
            jt.put("pwd",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        startActivity(new Intent(ModifyActivity.this, LoginActivity.class));
                        finish();
                        Toast.makeText(ModifyActivity.this,  "密码修改成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ModifyActivity.this,  "密码修改失败", Toast.LENGTH_SHORT).show();
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