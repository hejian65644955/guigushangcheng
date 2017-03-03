package com.atguigu.guigushangcheng.type.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.base.BaseFragment;
import com.atguigu.guigushangcheng.type.adapter.TypeLeftAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 何健 on 2017/3/3.
 */

public class ListFragment extends BaseFragment {
    @InjectView(R.id.lv_left)
    ListView lvLeft;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private TypeLeftAdapter leftAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.inject(this, view);
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        leftAdapter = new TypeLeftAdapter(mContext, titles);
        lvLeft.setAdapter(leftAdapter);

        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftAdapter.changeSelected(position);
                leftAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
