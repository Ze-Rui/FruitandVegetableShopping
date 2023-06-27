package com.example.shopping.location.viewHolder;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.shopping.R;
import com.example.shopping.app.LocationActivity;
import com.example.shopping.app.MyApplication;
import com.example.shopping.bean.Response;
import com.example.shopping.bean.User;
import com.example.shopping.location.bean.Location;
import com.example.shopping.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;

import static android.content.Context.MODE_PRIVATE;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    private TextView user_username;
    private TextView user_phone;
    private TextView user_location;

    private Location location;
    private Button update ;
    private Button delete;


    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);

        user_username = itemView.findViewById(R.id.user_username);
        user_phone = itemView.findViewById(R.id.user_phone);
        user_location = itemView.findViewById(R.id.user_location);

        update = itemView.findViewById(R.id.location_update);
        delete = itemView.findViewById(R.id.location_delete);

        initListen();

    }
    // 修改、删除点击事件
    void initListen(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });

    }

    void deleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("是否删除地址？").setIcon(android.R.drawable.ic_dialog_info);
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
                        .url(Constants.Location_delete)
                        .addParams("id", String.valueOf(location.getId()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("updateLocation",e.getMessage());
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


    void updateDialog(){

        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.item_location_dialog,null);
        EditText userName = view.findViewById(R.id.location_userName);
        userName.setText(location.getUserName());
        EditText phone = view.findViewById(R.id.location_phone);
        phone.setText(location.getPhone());
        EditText address = view.findViewById(R.id.location_address);
        address.setText(location.getLocation());
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("修改收货信息").setIcon(android.R.drawable.ic_dialog_info).setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
                int buyer_id = sharedPreferences.getInt("buyer_id",0);
                Location update = new Location(location.getId(),userName.getText().toString(),phone.getText().toString(),address.getText().toString(),buyer_id);
                HashMap<String, Object> jsonString = new HashMap<>();
                jsonString.put("location",update);
                OkHttpUtils
                        .postString()
                        .url(Constants.Location_update)
                        .content(new Gson().toJson(jsonString))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback(){

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("updateLocation",e.getMessage());
                                Toast.makeText(itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if(response != null){
                                    Response result = JSON.parseObject(response, Response.class);

                                    if(result.getCode() == 200000){
                                        Toast.makeText(itemView.getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                                        userName.setText(update.getUserName());
                                        phone.setText(update.getPhone());
                                        address.setText(update.getLocation());

                                    }

                                }
                            }
                        });

            }});
        builder.show();

    }



    public TextView getUser_username() {
        return user_username;
    }

    public void setUser_username(TextView user_username) {
        this.user_username = user_username;
    }

    public TextView getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(TextView user_phone) {
        this.user_phone = user_phone;
    }

    public TextView getUser_location() {
        return user_location;
    }

    public void setUser_location(TextView user_location) {
        this.user_location = user_location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
