package demo.imdemo.com.splashdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import demo.imdemo.com.splashdemo.utils.ContantsUtil;
import demo.imdemo.com.splashdemo.utils.ShapeUtils;

/**
 * 引导页面的图片显示
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private CirclePageIndicator mPageIndicator;
    private Context mContext;
    private Button mBtnEnter;
    private int[] mImgs = {R.mipmap.img01, R.mipmap.img02, R.mipmap.img03};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initData();
        initListener();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page);
        mBtnEnter = (Button) findViewById(R.id.btn_enter);
    }

    private void initData() {
        mViewPager.setAdapter(mPageAdapter);
        mPageIndicator.setViewPager(mViewPager);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
        mBtnEnter.setOnClickListener(this);
    }
    /**
     * 引导页面的适配器
     */
    private PagerAdapter mPageAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //在这个方法中加入一个ImageView的控件用来显示图片
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
          //  return super.instantiateItem(container, position);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mImgs[position]);//设置图片进去
            //缩放图片
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }
        //当页面显示完成的时候进行销毁给定位置的页面，不然会出现页面滑动崩溃情况
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    /**
     * 监听页面的滑动情况
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==mImgs.length-1){
            mBtnEnter.setVisibility(View.VISIBLE);
        }else{
            mBtnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 点击立即体验进入首页面
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_enter){
            Intent intent = new Intent(MainActivity.this,TwoActivity.class);
            startActivity(intent);
            ShapeUtils.saveEnterBoolean(mContext, ContantsUtil.SHAPE_STARTED,true);
            finish();
        }
    }
}
