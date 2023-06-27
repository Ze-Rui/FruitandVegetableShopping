package com.example.shopping.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.shopping.R;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.home.viewHolder.ActViewHolder;
import com.example.shopping.home.viewHolder.BannerViewHolder;
import com.example.shopping.home.viewHolder.ChannelViewHolder;
import com.example.shopping.home.viewHolder.RecommendViewHolder;

public class HomeRecycleAdapter extends RecyclerView.Adapter {
    private static final String TAG =
            HomeRecycleAdapter.class.getSimpleName();
     /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;
    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 推荐
     */
    public static final int RECOMMEND = 3;

    /**
     * 当前类型
     *
     * */
    public int currentType = BANNER;

    /**
     * 数据对象
     */
    private ResultBeanData.ResultBean resultBean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    /**
     * 总共有多少个item
     * @return
     */
    @Override
    public int getItemCount() {
        return 4;
    }

    /**
     * 相当于getView,创建ViewHolder 部分代码
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder viewHolder = null ;

        switch (viewType){
            case BANNER:
                viewHolder = new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager,null), mContext,resultBean);
                break;
            case CHANNEL:
                viewHolder = new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.channel_item,null));
                break;
            case ACT:
                viewHolder = new ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,null));
                break;
            case RECOMMEND:
                viewHolder = new RecommendViewHolder(mContext,mLayoutInflater.inflate(R.layout.recommend_item,null));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case BANNER:
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerViewHolder.setData(resultBean.getBanner_info());
                break;
            case CHANNEL:
                ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
                channelViewHolder.setData(resultBean.getChannel_info());
                break;
            case ACT:
                ActViewHolder actViewHolder = (ActViewHolder) holder;
                actViewHolder.setData(resultBean.getAct_info());
                break;
            case RECOMMEND:
                RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
                recommendViewHolder.setData(resultBean.getRecommend_info());
                break;
            default:
                break;
        }

    }



    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
        }
        return currentType; }

    public HomeRecycleAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }




}

