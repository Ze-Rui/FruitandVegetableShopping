package com.example.shopping.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {
    private XBanner xbanner_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




            //初始化控件
            xbanner_view = findViewById(R.id.xbanner_view);

            //图片集合,模拟一下数据
            final List<String> imgesUrl = new ArrayList<>();
            imgesUrl.add("http://192.168.0.107:8080/atguigu/img/1478770583834.png");
            imgesUrl.add("http://192.168.0.107:8080/atguigu/img/1478770583835.png");
            imgesUrl.add("http://192.168.0.107:8080/atguigu/img/1478770583836.png");

            //数据集合导入banner里
            xbanner_view.setData(imgesUrl,null);

            //图片加载
            xbanner_view.loadImage(new XBanner.XBannerAdapter() {

                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    //glide请求网络图片
                    Glide.with(Test.this).load(imgesUrl.get(position)).into((ImageView) view);
                }
            });

            //图片加载，跟上面的loadImage一样，不过setmAdapter已经标识着过时了，上面的和这个用一个就行
            xbanner_view.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    //glide请求网络图片
                    Glide.with(Test.this).load(imgesUrl.get(position)).into((ImageView) view);
                }
            });

            //设置切换延时,单位sm，默认5000sm
            xbanner_view.setPageChangeDuration(3000);

            // 设置XBanner的页面切换特效，有多个，其他的可以到网上去查
            //xbanner_view.setPageTransformer(Transformer.Default);//横向移动

            xbanner_view.setPageTransformer(Transformer.Alpha); //渐变，效果不明显

            //设置轮播图点击监听
            xbanner_view.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    Toast.makeText(Test.this, "点击了"+position, Toast.LENGTH_SHORT).show();
                }
            });

            //-----------一下可以在控件里面进行设置，也可以在当前执行页面进行设置-------------------------
            xbanner_view.setAutoPlayAble(true);   //设置自动轮播

            xbanner_view.setAutoPalyTime(5000);   //图片轮播事件间隔,int类型，默认5000ms
            //………………详细设置属性可以看下面

    }
}