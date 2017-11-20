package com.eagle.adapter.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.eagle.adapter.R;
import com.eagle.adapter.util.ImageUtils;


public class TestCommonActivity extends BaseActivity {

    private TextView txtv_JustTest;
    private ImageView imgv_TestSketch;

    @Override
    public int getContentLayout() {
        return R.layout.activity_test_common;
    }

    @Override
    public void initView() {
        setToolBarTitle("Common Test");
        txtv_JustTest = findViewById(R.id.txtv_JustTest);
        txtv_JustTest.setOnClickListener(view -> {
            imgv_TestSketch.setImageBitmap(ImageUtils.changeToSketch(this, BitmapFactory.decodeResource(getResources(), R.mipmap.just_test_one)));
        });
        imgv_TestSketch = findViewById(R.id.imgv_TestSketch);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestCommonActivity.class);
        context.startActivity(intent);
    }


}
