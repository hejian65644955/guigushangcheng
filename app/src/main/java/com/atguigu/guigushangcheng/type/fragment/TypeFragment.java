package com.atguigu.guigushangcheng.type.fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/2/22.
 */

public class TypeFragment extends BaseFragment {


    @InjectView(R.id.tl_1)
    SegmentTabLayout tl1;
    @InjectView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @InjectView(R.id.fl_type)
    FrameLayout flType;
    private String[] titles = {"分类","标签"};
    private List<BaseFragment> fragments;
    private BaseFragment tempFragment;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "分类内容被初始化了");
        initListener();
        initFragment();
        switchFragment(fragments.get(0));

    }

    private void switchFragment(BaseFragment currentFragment) {
        if(currentFragment!= tempFragment){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            if(currentFragment.isAdded()){
                if(tempFragment!=null){
                    ft.hide(tempFragment);
                }
                ft.show(currentFragment);
            }else {
                if(tempFragment !=null){
                    ft.hide(tempFragment);
                }
                ft.add(R.id.fl_type,currentFragment);
            }
            ft.commit();
            tempFragment =currentFragment;
        }

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());

    }

    private void initListener() {
        tl1.setTabData(titles);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.iv_type_search)
    public void onClick() {
    }
}
