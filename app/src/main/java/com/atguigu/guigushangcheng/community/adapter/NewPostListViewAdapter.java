package com.atguigu.guigushangcheng.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.community.bean.NewPostBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 何健 on 2017/3/4.
 */

public class NewPostListViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<NewPostBean.ResultEntity> datas;

    public NewPostListViewAdapter(Context mContext, List<NewPostBean.ResultEntity> result) {
        this.mContext = mContext;
        this.datas = result;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        NewPostBean.ResultEntity entity = datas.get(position);
        //图像
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+entity.getAvatar()).into(viewholder.ibNewPostAvatar);
        viewholder.tvCommunityUsername.setText(entity.getUsername());
        //图像
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+entity.getFigure()).into(viewholder.ivCommunityFigure);

        viewholder.tvCommunitySaying.setText(entity.getSaying());
        viewholder.tvCommunityLikes.setText(entity.getLikes());
        viewholder.tvCommunityComments.setText(entity.getComments());

        //显示弹幕

        //显示弹幕
        List<String> strings = entity.getComment_list();
        if (strings != null && strings.size() > 0) {
            //有弹幕数据
            List<IDanmakuItem> list = initItems(viewholder.danmakuView,strings);
            Collections.shuffle(list);
            viewholder.danmakuView.addItem(list, true);
            viewholder.danmakuView.show();
            viewholder.danmakuView.setVisibility(View.VISIBLE);
        }else{
            viewholder.danmakuView.hide();
            viewholder.danmakuView.setVisibility(View.GONE);
        }


        return convertView;
    }

    private List<IDanmakuItem> initItems(DanmakuView danmakuView,List<String> strings ) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            IDanmakuItem item = new DanmakuItem(mContext, strings.get(i), danmakuView.getWidth());
            list.add(item);
        }
        return list;
    }

    class ViewHolder {
        @InjectView(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @InjectView(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @InjectView(R.id.rl)
        RelativeLayout rl;
        @InjectView(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;
        @InjectView(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @InjectView(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @InjectView(R.id.tv_community_likes)
        TextView tvCommunityLikes;
        @InjectView(R.id.tv_community_comments)
        TextView tvCommunityComments;
        @InjectView(R.id.danmakuView)
        DanmakuView danmakuView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
