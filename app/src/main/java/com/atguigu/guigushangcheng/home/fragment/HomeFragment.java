package com.atguigu.guigushangcheng.home.fragment;

import android.util.Log;
import android.view.View;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.base.BaseFragment;

/**
 * Created by lenovo on 2017/2/22.
 */

public class HomeFragment extends BaseFragment {


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页页面被初始化了");
    }
}
