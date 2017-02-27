package com.atguigu.guigushangcheng;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.guigushangcheng.base.BaseFragment;
import com.atguigu.guigushangcheng.community.fragment.CommunityFragment;
import com.atguigu.guigushangcheng.home.fragment.HomeFragment;
import com.atguigu.guigushangcheng.shopping.fragment.ShoppingFragment;
import com.atguigu.guigushangcheng.type.fragment.TypeFragment;
import com.atguigu.guigushangcheng.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.fl_main)
    FrameLayout flMain;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    private List<BaseFragment> fragments;
    //记录点击的位置
    private int position;
    //记录缓存的fragment页面
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        initFragment();

        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                //切换到对应的fragment
                BaseFragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    private void switchFragment(BaseFragment currentFragment) {
       if(currentFragment!=tempFragment){
           FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           if(!currentFragment.isAdded()){//当前的没有被添加
               if(tempFragment!=null){
                   //隐藏缓存
                   ft.hide(tempFragment);
               }
               ft.add(R.id.fl_main,currentFragment);
           }else{
               if(tempFragment!=null){
                   ft.hide(tempFragment);
               }
               ft.show(currentFragment);
           }
           ft.commit();
           tempFragment =currentFragment;
       }

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingFragment());
        fragments.add(new UserFragment());
    }


}
