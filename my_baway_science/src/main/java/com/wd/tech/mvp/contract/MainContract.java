package com.wd.tech.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

import com.wd.tech.bean.PayWxBean;
import com.wd.tech.bean.buyVipBean;


public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //下单-李国庆
        void bUYVIP(buyVipBean bvBean);
        //支付2-支付宝
        void PAY2(String payBean);
        //支付1-微信
        void PAY1(PayWxBean payWxBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //下单-李国庆
        Observable<buyVipBean> buyVip(HashMap<String, String> Header, HashMap<String, String> Query);
        //支付2-支付宝
        Observable<ResponseBody> pay2(HashMap<String, String> Header, HashMap<String, String> Query);
        //支付1-微信
        Observable<PayWxBean> pay1(HashMap<String, String> Header, HashMap<String, String> Query);
    }
}
