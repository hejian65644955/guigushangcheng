package com.atguigu.guigushangcheng.community.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.guigushangcheng.base.BaseFragment;

/**
 * Created by lenovo on 2017/2/22.
 */

public class CommunityFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "发现内容页面被初始化了");
        textView.setText("发现内容");
    }
}
