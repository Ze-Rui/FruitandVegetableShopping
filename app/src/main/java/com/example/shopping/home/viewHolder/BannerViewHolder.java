package com.example.shopping.home.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.app.GoodsInfoActivity;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.home.bean.ResultBeanData;
import com.example.shopping.home.fragment.HomeFragment;
import com.example.shopping.utils.Constants;
import com.example.shopping.utils.ToGoodsInfoActivity;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

public class BannerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG =
            HomeFragment.class.getSimpleName();
    public XBanner banner;
    public Context mContext;
    public ResultBeanData.ResultBean resultBean;

    public BannerViewHolder(View itemView, Context mContext,
                            ResultBeanData.ResultBean resultBean) {
        super(itemView);
        banner = (XBanner) itemView.findViewById(R.id.xbanner_view);
        this.mContext = mContext;
        this.resultBean = resultBean;
    }


    public void setData(final List<ResultBeanData.ResultBean.BannerInfoBean>
                                banner_info)  {

        //得到图片地址
        List<String> imagesUrl = new ArrayList<>();
        for(int i = 0;i < banner_info.size();i ++ ){
            String imageUrl = Constants.BASE_REQ + banner_info.get(i).getImage();
            System.out.println(imageUrl);
            imagesUrl.add(imageUrl);
        }
        //数据集合导入banner里
        banner.setData(imagesUrl,null);

        //图片加载
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //glide请求网络图片
                Glide.with(mContext).load(imagesUrl.get(position)).into((ImageView) view);
            }
        });



        //设置切换延时,单位sm，默认5000sm
        banner.setPageChangeDuration(3000);

        // 设置XBanner的页面切换特效，有多个，其他的可以到网上去查
        //xbanner_view.setPageTransformer(Transformer.Default);//横向移动

        banner.setPageTransformer(Transformer.Alpha); //渐变，效果不明显

        //设置轮播图点击监听
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                if(position - 1 < banner_info.size()){
                    //int option = banner_info.get(position - 1).getOption();
                    String product_id = "";
                    String name = "";
                    String cover_price = "";
                    String num = "";
                    if (position - 1 == 0) {
                        product_id = "627";
                        cover_price = "32.00";
                        name = "剑三T恤批发";
                    } else if (position - 1 == 1) {
                        product_id = "21";
                        cover_price = "8.00";
                        name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                    } else {
                        product_id = "1341";
                        cover_price = "50.00";
                        name = "【蓝诺】《天下吾双》 剑网3同人本";
                    }
                    String image = banner_info.get(position).getImage();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, image, product_id,num);

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("goods_bean", goodsBean);
                    mContext.startActivity(intent);
                }


            }
        });

        //-----------一下可以在控件里面进行设置，也可以在当前执行页面进行设置-------------------------
        banner.setAutoPlayAble(true);   //设置自动轮播

        banner.setAutoPalyTime(5000);   //图片轮播事件间隔,int类型，默认5000ms



    }

}

