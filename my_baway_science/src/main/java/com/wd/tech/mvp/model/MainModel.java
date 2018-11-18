package com.wd.tech.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

import com.wd.tech.bean.PayWxBean;
import com.wd.tech.bean.buyVipBean;
import com.wd.tech.mvp.contract.MainContract;
import com.wd.tech.mvp.model.api.service.ApiService;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    //下单-李国庆
    @Override
    public Observable<buyVipBean> buyVip(HashMap<String, String> Header, HashMap<String, String> Query) {
        Observable<buyVipBean> observable = mRepositoryManager.obtainRetrofitService(ApiService.class).buyVip(Header,Query);
        return observable;
    }
    //支付2-支付宝
    @Override
    public Observable<ResponseBody> pay2(HashMap<String, String> Header, HashMap<String, String> Query) {
        Observable<ResponseBody> observable = mRepositoryManager.obtainRetrofitService(ApiService.class).pay2(Header,Query);
        return observable;
    }
    //支付1-微信
    @Override
    public Observable<PayWxBean> pay1(HashMap<String, String> Header, HashMap<String, String> Query) {
        Observable<PayWxBean> observable = mRepositoryManager.obtainRetrofitService(ApiService.class).pay1(Header,Query);
        return observable;
    }
}