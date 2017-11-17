package com.eagle.adapter.activity;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.multidex.MultiDex;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;


public class SuperApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.white);
            MaterialHeader header = new MaterialHeader(context);
            layout.setEnableHeaderTranslationContent(false);
            return header;

        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            BallPulseFooter footer = new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            footer.setBackgroundColor(Color.parseColor("#F0F0F0"));
            footer.setIndicatorColor(context.getResources().getColor(android.R.color.white));
            return footer;
        });
    }
}
