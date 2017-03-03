package com.atguigu.guigushangcheng.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.type.bean.TypeBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.atguigu.guigushangcheng.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 何健 on 2017/3/3.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<TypeBean.ResultBean> result;

    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;
    private final List<TypeBean.ResultBean.ChildBean> child;
    private final List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    private LayoutInflater inflater;
    private int tempViewType;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
        child = result.get(0).getChild();//得到-常用分类
        hot_product_list = result.get(0).getHot_product_list();//热卖数据集合
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HOT:
                tempViewType = HOT;
                break;
            case COMMON:
                tempViewType = COMMON;
        }
        return tempViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HOT:
                return new HotViewHolder(inflater.inflate(R.layout.item_hot_right, null));
            case COMMON:
        }
        return null;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;
        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

        public void setData(final List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {
            for (int i = 0; i < hot_product_list.size(); i++) {

                TypeBean.ResultBean.HotProductListBean bean = hot_product_list.get(i);


                //外面的线性布局
                LinearLayout layout = new LinearLayout(mContext);
                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, -2);
                params.setMargins((DensityUtil.dip2px(mContext, 5)), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));


                layout.setGravity(Gravity.CENTER);//设置布局居中
                layout.setOrientation(LinearLayout.VERTICAL);//设置竖直方向


                //创建图片---------------------
                ImageView imageView = new ImageView(mContext);
                //设置图片宽和高80dip
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                //设置间距
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));

                //请求图片
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + bean.getFigure()).into(imageView);


                //把图片添加到线性布局
                layout.addView(imageView, ivParams);


                //--创建文本---------

                //文字
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
//                textView.setTextColor(Color.RED);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + bean.getCover_price());

                //把文本添加到线性布局
                layout.addView(textView, tvParams);


                //把每个线性布局添加到外部的线性布局中

                llHotRight.addView(layout, params);


                //设置item的点击事件
                layout.setTag(i);


                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();

                        Toast.makeText(mContext, "position==" + hot_product_list.get(position).getCover_price(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HOT:
                HotViewHolder viewHolder = (HotViewHolder) holder;
                viewHolder.setData(hot_product_list);
                break;
            case COMMON:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
