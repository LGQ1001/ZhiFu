package com.wd.tech.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.tencent.mm.sdk.modelpay.PayReq;

import com.wd.tech.app.App;
import com.wd.tech.bean.PayWxBean;
import com.wd.tech.bean.buyVipBean;
import com.wd.tech.di.component.DaggerMainComponent;
import com.wd.tech.di.module.MainModule;
import com.wd.tech.mvp.contract.MainContract;
import com.wd.tech.mvp.presenter.MainPresenter;

import tech.wd.com.R;
import com.wd.tech.mvp.ui.activity.utils.MyALipayUtils;


import static com.jess.arms.utils.Preconditions.checkNotNull;



public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private Button btn_01;
    private String payInfo;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        btn_01 = findViewById(R.id.btn_01);
        //签名
        String s = MD5( "105" + "1001" + "tech");
        //赋值
        HashMap<String,String> Header = new HashMap<>();
        Header.put("userId","105");
        Header.put("sessionId","1542416143272105");
        HashMap<String,String> Query = new HashMap<>();
        Query.put("commodityId","1001");
        Query.put("sign",s+"");
        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下单
                mPresenter.buyVip(Header,Query);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("请选择支付方式");
                builder.setTitle("提示");
                builder.setPositiveButton("支付宝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> prams2 = new HashMap<>();
                        prams2.put("orderId", payInfo);
                        prams2.put("payType", "2");
                        mPresenter.PAY2(Header, prams2);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("微信", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> prams1 = new HashMap<>();
                        prams1.put("payType", "1");
                        prams1.put("orderId", payInfo);
                        mPresenter.PAY1(Header, prams1);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    //MD5加密
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    //下单
    @Override
    public void bUYVIP(buyVipBean bvBean) {
        Toast.makeText(this, bvBean.getMessage()+":"+bvBean.getOrderId(), Toast.LENGTH_SHORT).show();
        payInfo = bvBean.getOrderId();
    }

    //支付宝
    @Override
    public void PAY2(String payBean) {
        MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
        builder.build().toALiPay(this, payBean.toString());
    }

    //微信
    @Override
    public void PAY1(PayWxBean payWxBean) {
        PayReq req = new PayReq();
        req.appId = payWxBean.getAppId();
        req.partnerId = payWxBean.getPartnerId();
        req.prepayId = payWxBean.getPrepayId();
        req.nonceStr = payWxBean.getNonceStr();
        req.timeStamp = payWxBean.getTimeStamp();
        req.packageValue = payWxBean.getPackageValue();
        req.sign = payWxBean.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
        App.mWxApi.sendReq(req);
    }
}
