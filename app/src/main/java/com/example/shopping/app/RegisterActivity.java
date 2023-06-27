package com.example.shopping.app;

import android.content.Context;
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
import com.example.shopping.bean.User;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.CountDownTimerUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.MediaType;

public class RegisterActivity extends AppCompatActivity {


    private EditText ed_toPassword;
    // 邮箱
    private EditText email;
    // 输入的验证码
    private EditText register_inputCode;
    // 手机号
    private EditText register_phone;
    // 用户名
    private EditText register_name;
    //密码
    private EditText ed_password;

    private TextView code;

    private Context mContext;

    private Button btn_register;
    private TextView register_warning;
    private TextView email_warning;
    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        queue = Volley.newRequestQueue(this);
        register_inputCode = findViewById(R.id.register_inputCode);
        register_phone = findViewById(R.id.register_phone);
        register_name = findViewById(R.id.register_name);
        code =  findViewById(R.id.register_code);
        ed_password = findViewById(R.id.register_password);
        ed_toPassword = findViewById(R.id.register_toPassword);
        email = findViewById(R.id.register_email);
        btn_register = findViewById(R.id.register_btn);
        register_warning = findViewById(R.id.register_warning);
        email_warning = findViewById(R.id.register_email_warning);

        mContext = this;
        // 初始化请求验证码按钮
        initView();

        listenPassword();
        listenEmail();

        register();
    }

    // 监听两次密码是否相同
    public void listenPassword(){

        // 判断两次密码是否相同
        ed_toPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //失去焦点处理
                if(!hasFocus){
                    String password = ed_password.getText().toString().trim();
                    String toPassword = ed_toPassword.getText().toString().trim();

                    //判断两个密码是否相等
                    if(password.equals(toPassword)){
                        // 如果密码相同，警告文字不会出现
                        register_warning.setVisibility(View.INVISIBLE);
                        btn_register.setClickable(true);
                    }else{
                        // 如果密码不等，警告文字出现
                        register_warning.setVisibility(View.VISIBLE);
                        btn_register.setClickable(false);
                    }

                }
            }
        });
    }
    // 监听邮箱输入框
    public void listenEmail(){

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String emailString = email.getText().toString().trim();
                // 失去焦点时判断是否为邮箱格式
                if(!hasFocus){
                    if(!isEmail(emailString)){
                        email_warning.setVisibility(View.VISIBLE);
                    }
                    else {
                        email_warning.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        // 当邮箱格式不正确，禁止点击获得验证码功能
        if(email_warning.getVisibility() == View.VISIBLE){
           code.setClickable(false); //设置不可点击
        }
        else{
            code.setClickable(true); //设置可点击
        }

    }

    protected void initView() {
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }



    /**
     * 注册
     */
    public void register(){

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String myEmail = email.getText().toString();
                String code = register_inputCode.getText().toString();
                String phone = register_phone.getText().toString();
                String name = register_name.getText().toString();
                String password = ed_password.getText().toString();
                // 两次密码相等时
                if(register_warning.getVisibility() == View.INVISIBLE){
                    sets(myEmail,name,password,phone);
                }else {
                    Toast.makeText(mContext,"两次密码不相等！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sets(String email, String username, String password, String phone){
        String url = getResources().getString(R.string.url)+"user/regist";
        JSONObject jt = new JSONObject();
        try {
            jt.put("email",email);
            jt.put("name",username);
            jt.put("pwd",password);
            jt.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,jt, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("flug").equals("true")){
                        Toast.makeText(RegisterActivity.this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this,  "账户名已注册", Toast.LENGTH_SHORT).show();
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