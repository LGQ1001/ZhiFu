package com.wd.tech.mvp.model.api.service;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import com.wd.tech.bean.PayWxBean;
import com.wd.tech.bean.buyVipBean;

public interface  ApiService {
    //1.下单-李国庆
    @FormUrlEncoded
    @POST("techApi/tool/verify/v1/buyVip")
    Observable<buyVipBean> buyVip(@HeaderMap HashMap<String, String> Header, @FieldMap HashMap<String, String> Query);

    //2.支付-李国庆
    @FormUrlEncoded
    @POST("techApi/tool/verify/v1/pay")
    Observable<ResponseBody> pay2(@HeaderMap HashMap<String, String> Header, @FieldMap HashMap<String, String> Query);

    //2.微信-李国庆
    @FormUrlEncoded
    @POST("techApi/tool/verify/v1/pay")
    Observable<PayWxBean> pay1(@HeaderMap HashMap<String, String> Header, @FieldMap HashMap<String, String> Query);

}
