package com.atguigu.guigushangcheng.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.base.BaseFragment;
import com.atguigu.guigushangcheng.home.adapter.HomeAdapter;
import com.atguigu.guigushangcheng.home.bean.HomeBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by lenovo on 2017/2/22.
 */

public class HomeFragment extends BaseFragment {


    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    private HomeAdapter homeAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页页面被初始化了");
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网请求失败"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网请求成功"+response);
                        processData(response);

                    }
                });
    }

    private void processData(String response) {
        HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
        Log.e("TAG", "解析数据成功"+
                homeBean.getResult().getRecommend_info().get(0).getName());

        //设置 RecyclerView适配器
        homeAdapter = new HomeAdapter(mContext, homeBean.getResult());

        rvHome.setAdapter(homeAdapter);

        //设置布局管理器
       // rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        rvHome.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if(position <=3){
                    ///按钮隐藏
                    ibTop.setVisibility(View.GONE);
                }else{
                    //按钮显示
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "查看消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                //Toast.makeText(mContext, "回到顶部", Toast.LENGTH_SHORT).show();
                rvHome.scrollToPosition(0);
                break;
        }
    }
}
