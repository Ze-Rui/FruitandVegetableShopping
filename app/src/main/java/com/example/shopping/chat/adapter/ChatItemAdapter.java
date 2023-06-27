package com.example.shopping.chat.adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.chat.bean.ChatItem;
import com.example.shopping.utils.Constants;

import java.util.List;
/**
 * 聊天item适配器
 */

public class ChatItemAdapter extends ArrayAdapter<ChatItem> {
    private int resourceId;

    private Context context;
    // 适配器的构造函数，把要适配的数据传入这里
    public ChatItemAdapter(Context context, int textViewResourceId, List<ChatItem> objects){

        super(context,textViewResourceId,objects);

        System.out.println("objects = " +objects);
        resourceId=textViewResourceId;
        this.context = context;

    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ChatItem item =getItem(position); //获取当前项的item实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.itemImage=view.findViewById(R.id.product_img);
            viewHolder.itemName=view.findViewById(R.id.seller_name);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        ImageView image = viewHolder.itemImage;
        // 加载图片
        Glide.with(context).load(Constants.BASE_REQ+item.getProduct_img()).into(image);

        viewHolder.itemName.setText(item.getSeller_name());
        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        ImageView itemImage;
        TextView itemName;
    }
}

