package com.example.shopping.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.home.viewHolder.ActViewHolder;
import com.example.shopping.utils.Constants;

import java.util.List;

public class ActAdapter extends PagerAdapter {
    private static final String TAG =
            ActAdapter.class.getSimpleName();
    private Context mContext;
    private List<ResultBeanData.ResultBean.ActInfoBean> act_info;
    public ActAdapter(Context mContext, List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.act_info = act_info;
    }

    @Override
    public int getCount() {
        return act_info.size();
    }

    /**
     * 比较是否为同一页面
     * @param view 页面
     * @param object instantiateItem返回的对象
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     *
     * @param container viewpage
     * @param position 对应页面的位置
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //添加到容器
        container.addView(imageView);

        // 加载图片
        Glide.with(mContext).load(Constants.BASE_REQ +act_info.get(position).getIcon_url())
                .into(imageView);

        // 设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"点击 ："+position);
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

       container.removeView((View)object);
    }

}
