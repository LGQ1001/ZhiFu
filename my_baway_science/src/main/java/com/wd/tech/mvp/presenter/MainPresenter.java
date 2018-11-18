package com.wd.tech.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.ResponseBody;

import javax.inject.Inject;

import com.wd.tech.bean.PayWxBean;
import com.wd.tech.bean.buyVipBean;
import com.wd.tech.mvp.contract.MainContract;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    @SuppressLint("CheckResult")
    public void buyVip(HashMap<String, String> header, HashMap<String, String> query) {
        mModel.buyVip(header,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<buyVipBean>() {
                    @Override
                    public void accept(buyVipBean byVpBean) throws Exception {
                        mRootView.bUYVIP(byVpBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRootView.bUYVIP(null);
                    }
                });
    }
    //支付宝
    @SuppressLint("CheckResult")
    public void PAY2(HashMap<String, String> header, HashMap<String, String> prams2) {
        mModel.pay2(header,prams2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String string = responseBody.string().toString();
                        mRootView.PAY2(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRootView.PAY2(null);
                    }
                });
    }
    //微信
    public void PAY1(HashMap<String, String> header, HashMap<String, String> prams1) {
        mModel.pay1(header,prams1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayWxBean>() {
                    @Override
                    public void accept(PayWxBean payWxBean) throws Exception {
                        mRootView.PAY1(payWxBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRootView.PAY1(null);
                    }
                });
    }
}
