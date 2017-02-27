package com.atguigu.guigushangcheng.shopping.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.guigushangcheng.home.bean.GoodsBean;
import com.atguigu.guigushangcheng.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 何健 on 2017/2/27.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private final Context mContext;
    private SparseArray<GoodsBean> sparseArray;
    private static CartStorage instance;

    private CartStorage(Context context) {
        this.mContext = context;
        sparseArray = new SparseArray<>();
        listToSparseArray();
    }

    private void listToSparseArray() {
        List<GoodsBean> beanList = getAllData();
        for (int i = 0; i < beanList.size(); i++) {
            GoodsBean goodsBean = beanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);


        }
    }

    private List<GoodsBean> getAllData() {

        return getLocalData();
    }

    private List<GoodsBean> getLocalData() {
        List<GoodsBean> goodsBeans = new ArrayList<>();
        String json = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(json)) {
            goodsBeans = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }

        return goodsBeans;
    }

    public static CartStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (CartStorage.class) {
                if (instance == null) {
                    instance = new CartStorage(context);
                }
            }
        }
        return instance;
    }
}
