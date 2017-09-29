package demo.imdemo.com.splashdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import demo.imdemo.com.splashdemo.utils.ContantsUtil;
import demo.imdemo.com.splashdemo.utils.ShapeUtils;

/**
 * 判断是否第一次进入app，如果是第一次进入则跳转MainActivity页面，否则跳转SplashActivity
 * Created by lenovo on 2017/9/29.
 */

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.img_splash);
        mImageView.setImageResource(R.mipmap.img01);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //判断是否第一次进入，如果第一次进入则走
        if (ShapeUtils.getEnterBoolean(mContext, ContantsUtil.SHAPE_STARTED)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(SplashActivity.this, TwoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).start();
        } else {
            Intent pageView = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(pageView);
            finish();
        }
    }
}
