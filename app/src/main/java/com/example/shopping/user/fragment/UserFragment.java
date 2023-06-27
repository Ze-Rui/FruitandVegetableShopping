package com.example.shopping.user.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.app.HistoryActivity;
import com.example.shopping.app.LoginActivity;
import com.example.shopping.app.MyApplication;
import com.example.shopping.app.MySellerActivity;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.app.LocationActivity;
import com.example.shopping.utils.Constants;
import com.google.gson.Gson;
import com.hankkin.gradationscroll.GradationScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

// 个人中心页面
public class UserFragment extends BaseFragment {

    @BindView(R.id.rl_person_header)
    RelativeLayout rlPersonHeader;
    @BindView(R.id.sv_person)
    GradationScrollView svPerson;
    @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;

    @BindView(R.id.user_location)
    TextView user_location;
    @BindView(R.id.user_collect)
    TextView user_collect;
    @BindView(R.id.user_setting)
    TextView user_setting;

    @BindView(R.id.user_logout)
    TextView user_logout;


    private User me = MyApplication.getMe();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);


        user_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });
        user_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        ViewTreeObserver vto = rlPersonHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private int height;

            @Override
            public void onGlobalLayout() {
                // 移除监听
                tvUsercenter.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                // 获取顶部图片的高度
                height = rlPersonHeader.getHeight();

                // 监听滑动，改变透明度
                svPerson.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

                        if (y <= 0) {   //设置标题的背景颜色
                            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255, 0, 0));
                        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            tvUsercenter.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
                        } else {    //滑动到banner下面设置普通颜色
                            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 255, 0, 0));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
