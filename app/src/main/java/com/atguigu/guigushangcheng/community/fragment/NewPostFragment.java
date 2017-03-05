package com.atguigu.guigushangcheng.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.guigushangcheng.R;
import com.atguigu.guigushangcheng.base.BaseFragment;
import com.atguigu.guigushangcheng.community.adapter.NewPostListViewAdapter;
import com.atguigu.guigushangcheng.community.bean.NewPostBean;
import com.atguigu.guigushangcheng.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 何健 on 2017/3/4.
 */

public class NewPostFragment extends BaseFragment {

    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    private NewPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_news_post, null);
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
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.NEW_POST_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","新帖联网成功==");
                        processData(response);

                    }
                });
    }

    private void processData(String response) {
        NewPostBean newPostBean = JSON.parseObject(response, NewPostBean.class);
        List<NewPostBean.ResultEntity> result = newPostBean.getResult();
        if(result != null && result.size() >0){

            //设置适配器
            adapter = new NewPostListViewAdapter(mContext,result);
            lvNewPost.setAdapter(adapter);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
