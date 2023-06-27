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

public class RecommendAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;

    public RecommendAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = mContext;
        this.recommend_info = recommend_info;
    }



    @Override
    public int getCount() {
        return recommend_info.size();
    }

    @Override
    public Object getItem(int position) {
        return recommend_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendAdapter.ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view,null);
            viewHolder = new RecommendAdapter.ViewHolder();

            viewHolder.iv_hot = convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RecommendAdapter.ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
        Glide.with(mContext).load(Constants.BASE_REQ + recommendInfoBean.getFigure())
                .into(viewHolder.iv_hot);

        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥ "+recommendInfoBean.getCover_price());

        return convertView;
    }

    static class ViewHolder{
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;
    }

}
