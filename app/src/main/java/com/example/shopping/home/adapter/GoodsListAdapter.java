package com.example.shopping.home.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.home.activity.GoodsListActivity;
import com.example.shopping.home.bean.TypeListBean;
import com.example.shopping.utils.Constants;

import java.util.List;

/**
 * 首页频道 进入
 */
public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<TypeListBean.ResultBean> page_data;

    public GoodsListAdapter(GoodsListActivity mContext, List<TypeListBean.ResultBean> page_data) {
        this.mContext = mContext;
        this.page_data = page_data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_goods_list_adapter, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(page_data.get(position));

    }

    @Override
    public int getItemCount() {
        return page_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private TypeListBean.ResultBean data;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_hot = (ImageView) itemView.findViewById(R.id.iv_hot);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });
        }

        public void setData(TypeListBean.ResultBean data) {
            Glide.with(mContext).load(Constants.BASE_REQ +data.getFigure()).into(iv_hot);
            tv_name.setText(data.getName());
            tv_price.setText("￥" + data.getCover_price());
            this.data = data;
        }
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
