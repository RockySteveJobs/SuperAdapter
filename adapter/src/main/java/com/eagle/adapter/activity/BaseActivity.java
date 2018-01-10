package com.eagle.adapter.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.eagle.adapter.R;
import com.eagle.adapter.base.BaseEvent;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutInflater mInflater;
    private ViewGroup fram_Container;
    private ViewGroup fram_TitleBar;
    private Toolbar toolbar;

    protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_base);

        fram_TitleBar = findViewById(R.id.fram_TitleBar);
        fram_Container = findViewById(R.id.fram_Container);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onToolBarBackClick());

        if (getContentLayout() > 0) {
            mInflater.inflate(getContentLayout(), fram_Container);
        }
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolBarTitle(String title) {
        if (toolbar == null) {
            return;
        }

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(title);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void onToolBarBackClick() {
        finish();
    }

    public void setToolBarTitle(@StringRes int title) {
        setToolBarTitle(getString(title));
    }

    public void replaceDefaultToolBar(View titleBar) {
        toolbar = null;
        fram_TitleBar.removeAllViews();
        fram_TitleBar.addView(titleBar);
    }

    public void removeDefaultToolBar() {
        toolbar = null;
        fram_TitleBar.removeAllViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEvent(BaseEvent event) {
    }

    public abstract int getContentLayout();

    public abstract void initView();

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    public void addView(View view) {
        fram_Container.addView(view);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fram_Container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram_Container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 设置 Activity 全屏
     */
    public void setFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置 Activity 退出全屏
     */
    public void exitFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
