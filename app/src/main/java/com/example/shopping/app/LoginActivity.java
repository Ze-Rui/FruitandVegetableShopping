package com.example.shopping.app;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.shopping.app.ForgetActivity;
import com.example.shopping.app.MainActivity;
import com.example.shopping.app.RegisterActivity;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.home.fragment.HomeFragment;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.NbButton;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;


public class LoginActivity extends AppCompatActivity {

    private Button button;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;

    private TextView email;

    private TextView password;

    private String loginResponse;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);
        button = findViewById(R.id.btn_login);
        rlContent=findViewById(R.id.rl_content);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Drawable background = rlContent.getBackground();
        if (background != null){
            background.setAlpha(0);
        }

        handler=new Handler();

        button.setOnClickListener(v ->
        {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 请求网络
                    getDataFromNet();
                }
            }).start();

        });
    }
    public void getDataFromNet(){

        String loginEmail =  email.getText().toString();
        String loginPassword = password.getText().toString();
        Log.e(loginEmail,loginPassword);
        adminlogin(loginEmail,loginPassword);
    }

    private void adminlogin(String name, String pwd) {
        String url = getResources().getString(R.string.url)+"user/login";
        JSONObject jt = new JSONObject();
        try {
            jt.put("email",name);
            jt.put("pwd",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("buyer_id",jsonObject.getJSONObject("object").getInt("id"));
                        editor.commit();
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError.toString());
                Toast.makeText(LoginActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(animator != null){
            animator.cancel();
        }

        rlContent.getBackground().setAlpha(0);
    }

    // 前往"忘记密码"页面
    public void toForget(View view){
        Intent intent = new Intent(this, ForgetActivity.class);
        startActivity(intent);
    }
    // 前往"注册账号"页面
    public void toRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}

