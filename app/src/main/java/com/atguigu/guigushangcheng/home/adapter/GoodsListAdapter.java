package com.atguigu.guigushangcheng.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.home.bean.TypeListBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 何健 on 2017/3/6.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<TypeListBean.ResultBean.PageDataBean> datas;
    private LayoutInflater inflater;

    public GoodsListAdapter(Context context, List<TypeListBean.ResultBean.PageDataBean> datas) {
        this.mContext = context;
        this.datas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items_goods_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TypeListBean.ResultBean.PageDataBean pageDataBean = datas.get(position);
        //绑定数据
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE+pageDataBean.getFigure())
                .placeholder(R.drawable.new_img_loading_2)
                .into(holder.ivHot);
        holder.tvName.setText(pageDataBean.getName());
        holder.tvPrice.setText("￥"+pageDataBean.getCover_price());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_hot)
        ImageView ivHot;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

            //设置监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    /**
     * 点击item的接口
     */
    public interface OnItemClickListener{
        public void onItemClick(TypeListBean.ResultBean.PageDataBean data);
    }

    private OnItemClickListener listener;

    /**
     * 设置item的点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
