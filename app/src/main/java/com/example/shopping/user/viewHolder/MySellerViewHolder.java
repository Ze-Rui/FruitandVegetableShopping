package com.example.shopping.user.viewHolder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.app.GoodsInfoActivity;
import com.example.shopping.bean.Response;
import com.example.shopping.home.bean.GoodsBean;
import com.example.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MySellerViewHolder extends RecyclerView.ViewHolder{

    public ImageView mySeller_iv_product;
    public TextView mySeller_tv_name;
    public TextView mySeller_tv_price;
    public Button mySeller_del;
    public Button mySeller_update;
    private LinearLayout mySeller_goods_item;

    public GoodsBean goodsBean;

    public MySellerViewHolder(@NonNull View itemView) {
        super(itemView);

        mySeller_iv_product = itemView.findViewById(R.id.mySeller_iv_product);
        mySeller_tv_name = itemView.findViewById(R.id.mySeller_tv_name);
        mySeller_tv_price = itemView.findViewById(R.id.mySeller_tv_price);
        mySeller_del = itemView.findViewById(R.id.mySeller_del);
        mySeller_update = itemView.findViewById(R.id.mySeller_update);
        mySeller_goods_item = itemView.findViewById(R.id.mySeller_goods_item);
        initListener();
    }

    public void initListener(){
        mySeller_goods_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), GoodsInfoActivity.class);
                intent.putExtra("goods_bean",goodsBean);
                itemView.getContext().startActivity(intent);
            }
        });

        mySeller_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog();
            }
        });

        mySeller_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
    }

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("是否删除商品？").setIcon(android.R.drawable.ic_dialog_info);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils
                        .get()
                        .url(Constants.Goods_delete)
                        .addParams("product_id", String.valueOf(goodsBean.getProduct_id()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response != null){
                                    Response result = JSON.parseObject(response, Response.class);

                                    if(result.getCode() == 200000) {
                                        Toast.makeText(itemView.getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
        builder.show();
    }

    private void updateDialog() {

        final Dialog dialog = new Dialog(itemView.getContext(),R.style.DialogTheme);
        View view = View.inflate(itemView.getContext(),R.layout.item_update_goods,null);
/*        String sellGood_name = view.findViewById(R.id.sellGood_name).toString();
        String sellGood_description = view.findViewById(R.id.sellGood_description).toString();
        String sellGood_price = view.findViewById(R.id.sellGood_price).toString();
        Spinner spinner1 = view.findViewById(R.id.spinner1);
        String type = spinner1.getSelectedItem().toString();
        System.out.println(type);
        dialog.setContentView(view);

        Map<String,Object> jsonString = new HashMap<>();
        jsonString.put("product_id",goodsBean.getProduct_id());
        jsonString.put("cover_price",sellGood_price);*/
        

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);

        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.iv_close_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                OkHttpUtils
                        .post()
                        .url(Constants.Goods_update)
                        .addParams("product_id", String.valueOf(goodsBean.getProduct_id()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response != null){
                                    Response result = JSON.parseObject(response, Response.class);

                                    if(result.getCode() == 200000) {
                                        Toast.makeText(itemView.getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });*/
                dialog.dismiss();
            }
        });

    }

}
