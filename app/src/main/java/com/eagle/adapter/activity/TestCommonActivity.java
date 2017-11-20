package com.eagle.adapter.activity;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.eagle.adapter.R;

public class TestCommonActivity extends BaseActivity {

    private TextView txtv_JustTest;

    @Override
    public int getContentLayout() {
        return R.layout.activity_test_common;
    }

    @Override
    public void initView() {
        setToolBarTitle("Common Test");
        txtv_JustTest = findViewById(R.id.txtv_JustTest);
        txtv_JustTest.setOnClickListener(view -> {
            finish();
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestCommonActivity.class);
        context.startActivity(intent);
    }
}
