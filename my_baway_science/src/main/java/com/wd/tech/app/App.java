package com.wd.tech.app;

import com.jess.arms.base.BaseApplication;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class App extends BaseApplication {

    public static  IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224", false);
        // 将该app注册到微信
        mWxApi.registerApp("wx4c96b6b8da494224");
    }

}
