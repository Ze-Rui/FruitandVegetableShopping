package com.example.shopping.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.utils.Constants;

import java.util.List;

/**
 * 频道的适配器
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;
    public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext =mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.image_icon = convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //得到数据
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        // 加载图片
        Glide.with(mContext).load(Constants.BASE_REQ + channelInfoBean.getImage())
                .into(viewHolder.image_icon);

        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());
        return convertView;
    }

    static class ViewHolder{
        ImageView image_icon;
        TextView tv_title;
    }
}
