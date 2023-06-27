package com.example.shopping.home.viewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.home.adapter.ActAdapter;
import com.example.shopping.home.adapter.ChannelAdapter;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.home.uitls.AlphaPageTransformer;
import com.example.shopping.home.uitls.ScaleInTransformer;
import com.example.shopping.utils.Constants;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;

import java.util.List;

public class ActViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG =
            ActViewHolder.class.getSimpleName();
    private Context mContext;
    private ViewPager actViewPager;
    private ActAdapter adapter;


    public ActViewHolder(Context mContext, @NonNull View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.actViewPager = itemView.findViewById(R.id.act_viewpager);
    }

    public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> data) {
        actViewPager.setPageMargin(20);
        actViewPager.setOffscreenPageLimit(3);
        actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

        actViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = new ImageView(mContext);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                //绑定数据
                Glide.with(mContext)
                        .load(Constants.BASE_REQ + data.get(position).getIcon_url())
                        .into(view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        //点击事件
        actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
