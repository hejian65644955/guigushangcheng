package com.atguigu.guigushangcheng.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.home.bean.HomeBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 何健 on 2017/2/23.
 */

public class ChannelAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<HomeBean.ResultBean.ChannelInfoBean> data;

    public ChannelAdapter(Context context, List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = context;
        this.data = channel_info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_channel, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeBean.ResultBean.ChannelInfoBean channelInfoBean = data.get(position);
        viewHolder.tvChannel.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE+channelInfoBean.getImage())
                .crossFade()
                .into(viewHolder.ivChannel);
        return convertView;
    }

     class ViewHolder {
        @InjectView(R.id.iv_channel)
        ImageView ivChannel;
        @InjectView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
