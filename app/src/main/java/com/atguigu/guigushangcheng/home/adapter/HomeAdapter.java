package com.atguigu.guigushangcheng.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.home.bean.HomeBean;

import java.util.List;

/**
 * Created by 何健 on 2017/2/23.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private final Context mContext;

    /**
     * 六种类型
     */
    /**
     * 横幅广告-要从0开
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    private final HomeBean.ResultBean result;

    //当前类型
    public int currentType =BANNER;
    private final LayoutInflater inflater;


    @Override
    public int getItemViewType(int position) {
        switch (position){
            case BANNER:
                currentType =BANNER;
                break;
            case CHANNEL:
                currentType =CHANNEL;
                break;
            case ACT:
                currentType =ACT;
                break;
            case SECKILL:
                currentType =SECKILL;
                break;
            case RECOMMEND:
                currentType =RECOMMEND;
                break;
        }
        return currentType;
    }

    public HomeAdapter(Context context, HomeBean.ResultBean result){
        this.mContext =context;
        this.result = result;
        inflater = LayoutInflater.from(mContext);

    }

    /**
     *
     * @param parent
     * @param viewType 当前的类型
     * @return
     */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BANNER:
                //View view = View.inflate(mContext, R.layout.banner_viewpager, null);
                return new BannerViewHolder(inflater.inflate(R.layout.banner_viewpager, null),mContext);
            case CHANNEL:
                break;
            case ACT:
                break;
            case SECKILL:
                break;
            case RECOMMEND:
                break;
        }

        return null;
    }


    class BannerViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_title;

        public BannerViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            tv_title.setText("我是banner的内容");
        }
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case BANNER:
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerViewHolder.setData(result.getHot_info());
                break;
            case CHANNEL:
                break;
            case ACT:
                break;
            case SECKILL:
                break;
            case RECOMMEND:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 1;
    }
}