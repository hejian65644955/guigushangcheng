package com.atguigu.guigushangcheng.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.app.WebViewActivity;
import com.atguigu.guigushangcheng.home.activity.GoodsInfoActivity;
import com.atguigu.guigushangcheng.home.bean.GoodsBean;
import com.atguigu.guigushangcheng.home.bean.HomeBean;
import com.atguigu.guigushangcheng.home.bean.WebviewBean;
import com.atguigu.guigushangcheng.home.view.MyGridView;
import com.atguigu.guigushangcheng.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by 何健 on 2017/2/23.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final String GOODS_BEAN = "goods_bean";
    public static final String WEBVIEW_BEAN = "webview_bean";
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

    public static final int HOT = 5;
    private final HomeBean.ResultBean result;

    //当前类型
    public int currentType = BANNER;
    private final LayoutInflater inflater;


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
        }
        return currentType;
    }

    public HomeAdapter(Context context, HomeBean.ResultBean result) {
        this.mContext = context;
        this.result = result;
        inflater = LayoutInflater.from(mContext);

    }

    /**
     * @param parent
     * @param viewType 当前的类型
     * @return
     */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                //View view = View.inflate(mContext, R.layout.banner_viewpager, null);
                return new BannerViewHolder(inflater.inflate(R.layout.banner_viewpager, null), mContext);
            case CHANNEL:
                return new ChannelViewHolder(inflater.inflate(R.layout.channel_item, null), mContext);
            case ACT:
                return new ActViewHolder(inflater.inflate(R.layout.act_item, null), mContext);
            case SECKILL:
                return new SeckillViewHolder(inflater.inflate(R.layout.seckill_item, null), mContext);
            case RECOMMEND:
                return new RecommendViewHolder(inflater.inflate(R.layout.recommend_item, null), mContext);
            case HOT:
                return new HotViewHolder(inflater.inflate(R.layout.hot_item, null), mContext);
        }

        return null;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {


        private final Context mContext;
        @InjectView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @InjectView(R.id.gv_hot)
        MyGridView gvHot;
        HotGridViewAdapter adapter;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            adapter = new HotGridViewAdapter(mContext, hot_info);
            gvHot.setAdapter(adapter);

            //设置item点击事件
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @InjectView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @InjectView(R.id.gv_recommend)
        GridView gvRecommend;
        RecommendGridViewAdapter adapter;

        public RecommendViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
            //1.设置适配器
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gvRecommend.setAdapter(adapter);
            //2.设置点击事件
            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.RecommendInfoBean InfoBean = recommend_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(InfoBean.getName());
                    goodsBean.setCover_price(InfoBean.getCover_price());
                    goodsBean.setFigure(InfoBean.getFigure());
                    goodsBean.setProduct_id(InfoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);


                }
            });

        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.countdownview)
        CountdownView countdownview;
        @InjectView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @InjectView(R.id.rv_seckill)
        RecyclerView rvSeckill;
        private final Context mContext;
        SeckillRecyclerViewAdapter adapter;

        public SeckillViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);
            rvSeckill.setAdapter(adapter);
            //设置布局管理器
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //设置点击事件
            adapter.setOnItemClickListener(new SeckillRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });

            //设置倒计时
            countdownview.setTag("test1");
            long duration = Long.parseLong(seckill_info.getEnd_time()) - Long.parseLong(seckill_info.getStart_time());
            countdownview.start(duration);
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @InjectView(R.id.act_viewpager)
        ViewPager actViewpager;
        ViewPageAdapter adapter;

        public ActViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {
            adapter = new ViewPageAdapter(mContext, act_info);
            actViewpager.setAdapter(adapter);

            actViewpager.setPageMargin(20);//设置page间间距，自行根据需求设置
            actViewpager.setOffscreenPageLimit(3);//>=3
            actViewpager.setAdapter(adapter);
            //setPageTransformer 决定动画效果
            actViewpager.setPageTransformer(true, new
                    RotateYTransformer());

            //设置点击事件
            adapter.setOnItemClickListener(new ViewPageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //Toast.makeText(mContext, "postion==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    WebviewBean webviewBean = new WebviewBean();
                    webviewBean.setIcon_url(actInfoBean.getIcon_url());
                    webviewBean.setName(actInfoBean.getName());
                    webviewBean.setUrl(actInfoBean.getUrl());

                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEBVIEW_BEAN, webviewBean);
                    mContext.startActivity(intent);
                }
            });


        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context mComtext;
        @InjectView(R.id.gv_channel)
        GridView gvChannel;

        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.mComtext = mContext;
        }

        public void setData(List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            //设置Gridview适配器
            ChannelAdapter channelAdapter = new ChannelAdapter(mContext, channel_info);
            gvChannel.setAdapter(channelAdapter);

        }
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            //得到数据
            List<String> images = new ArrayList<>();
            //设置Banner的数据
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context)
                                    .load(path)
                                    .crossFade()
                                    .into(imageView);
                        }
                    }).start();
            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            //3.设置Banner的点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                                           @Override
                                           public void OnBannerClick(int position) {
                                               if (position < banner_info.size()) {
                                                   String product_id = "";
                                                   String name = "";
                                                   String cover_price = "";
                                                   String image = "";
                                                   if (position == 0) {
                                                       product_id = "627";
                                                       cover_price = "32.00";
                                                       name = "剑三T恤批发";
                                                   } else if (position == 1) {
                                                       product_id = "21";
                                                       cover_price = "8.00";
                                                       name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                                                   } else {
                                                       product_id = "1341";
                                                       cover_price = "50.00";
                                                       name = "【蓝诺】《天下吾双》 剑网3同人本";
                                                   }

                                                   image = banner_info.get(position).getImage();

                                                   GoodsBean goodsBean = new GoodsBean();
                                                   goodsBean.setProduct_id(product_id);
                                                   goodsBean.setName(name);
                                                   goodsBean.setCover_price(cover_price);
                                                   goodsBean.setFigure(image);


                                                   Intent intent = new Intent(mContext, GoodsInfoActivity.class);

                                                   intent.putExtra(GOODS_BEAN, goodsBean);
                                                   mContext.startActivity(intent);
                                               }
                                           }
                                       }
                );
            }
        }

        //绑定数据
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            switch (getItemViewType(position)) {
                case BANNER:
                    BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                    bannerViewHolder.setData(result.getBanner_info());
                    break;
                case CHANNEL:
                    ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
                    channelViewHolder.setData(result.getChannel_info());
                    break;
                case ACT:
                    ActViewHolder actViewHolder = (ActViewHolder) holder;
                    actViewHolder.setData(result.getAct_info());
                    break;
                case SECKILL:
                    SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
                    seckillViewHolder.setData(result.getSeckill_info());
                    break;
                case RECOMMEND:
                    RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
                    recommendViewHolder.setData(result.getRecommend_info());
                    break;
                case HOT:
                    HotViewHolder hotViewHolder = (HotViewHolder) holder;
                    hotViewHolder.setData(result.getHot_info());
            }


        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }
