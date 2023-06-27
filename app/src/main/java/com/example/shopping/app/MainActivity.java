package com.example.shopping.app;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shopping.R;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.chat.fragment.SellerChatFragment;
import com.example.shopping.community.fragment.CommunityFragment;
import com.example.shopping.home.fragment.HomeFragment;
import com.example.shopping.sell.fragment.SellerFragment;
import com.example.shopping.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    @BindView(R.id.rb_chat)
    RadioButton rbChat;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    // 多个fragment合集
    private ArrayList<BaseFragment> fragments;

    private int position;

    // 上次显示的页面（缓存）
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Butternif 和 Activity 绑定
        ButterKnife.bind(this);
        //初始化fragments
        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new SellerChatFragment());
        fragments.add(new UserFragment());
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  switch (checkedId) {
                      case R.id.rb_home: //前往主页
                          position = 0;
                          break;
                      case R.id.rb_community: // 前往community页
                          position = 1;
                          break;
                      case R.id.rb_chat: // 前往消息页
                          position = 2;
                          break;
                      case R.id.rb_user: // 前往用户页
                          position = 3;
                          break;
                  }
                  //根据位置取不同的fragment
                  BaseFragment baseFragment = getFragment(position);

                  // 切换fragment ： 上次fragment - 》 当前fragment
                  switchFragment(tempFragment, baseFragment);
              }
          });
        //默认设置首页
        rgMain.check(R.id.rb_home);
    }


    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }


    private void switchFragment(Fragment fromFragment, BaseFragment
            nextFragment) {
        //相同页面就不缓存
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                //判断 nextFragment 是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    // 添加
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    // 展示下一个fragment
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}