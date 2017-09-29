## 最简单的app引导页面（欢迎页面）

### 1.图片的显示是MainActivity.java页面

```
布局页面的展示activity.xml。
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="demo.imdemo.com.splashdemo.MainActivity">


    <android.support.v4.view.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </android.support.v4.view.ViewPager>

    <demo.imdemo.com.splashdemo.CirclePageIndicator
        android:id="@+id/circle_page"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="10dp"
        android:padding="10dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        />
   <Button
       android:id="@+id/btn_enter"
       android:layout_width="200dp"
       android:layout_height="wrap_content"
       android:text="立即体验"
       android:layout_alignParentBottom="true"
       android:gravity="center"
       android:textSize="24sp"
       android:layout_centerHorizontal="true"
       android:layout_marginBottom="30dp"
       android:textColor="@color/enter_ui_colors"
       android:background="@drawable/enter_ui_selector"
       android:visibility="gone"
       />
</RelativeLayout>
```



### 2.底部小圆点随图片的切换的显示

```
public class CirclePageIndicator extends View {

    //点的半径
    private int mDotRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    //点与点的间隔
    private int mDotGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());


    private ViewPager mViewPager;
    private Paint mDotPaint;

    private int mPosition;
    private float mPositionOffset;

    //不动点的颜色
    private int mNormalColor;
    //动点颜色
    private int mSelectedColor;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);//设置防止边缘出现锯齿，同时还可以设置位图的过滤处理  mDotPaint.setFilterBitmap(true);
        mNormalColor = Color.BLACK;//设置点在没有选中到的时候显示的颜色
        mSelectedColor = Color.RED;//设置点在选中的时候显示的颜色
    }

    /**
     * 测量CirclePageIndicator的高宽，不去使用在布局中的配置的宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = mViewPager.getAdapter().getCount();
        //宽度 = 点的直径 * 点的个数 + 点与点间隔 * (点的个数 - 1)
        int width =  2 * mDotRadius * count + (count - 1) * mDotGap;
        //高度 = 点的直径
        int height = 2 * mDotRadius;
        setMeasuredDimension(width, height);//添加设置宽高
    }

  public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        /**
         * 页面滑动的时候
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mPosition = position;
            mPositionOffset = positionOffset;
            invalidate();//重新绘制的意思
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        //点与点之间圆心的距离
        int dotDistance = mDotGap + 2 *  mDotRadius;
        //循环遍历不动点
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            float cx = mDotRadius + i * dotDistance;
            float cy = mDotRadius;
            mDotPaint.setColor(mNormalColor);
            canvas.drawCircle(cx, cy, mDotRadius, mDotPaint);
        }
        //绘制动点
        mDotPaint.setColor(mSelectedColor);
        //计算动点x轴的位置
        float mMoveCx = mDotRadius + dotDistance * mPositionOffset + mPosition * dotDistance ;
        float mMoveCy = mDotRadius;
        canvas.drawCircle(mMoveCx, mMoveCy, mDotRadius, mDotPaint);
    }
}
```



### 3.判断是否第一次进入app时显示不同的欢迎页面

```
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
```



### 4.其它

其它的一些页面可以打开app里面查看，这个引导页面可以直接拷贝代码都可以用的，至于小圆点颜色大小，距离等等可以自己在代码中根据需求自己改。



## 5.效果的显示

![img1](C:\Users\lenovo\Desktop\图片\img1.jpg)





![img2](C:\Users\lenovo\Desktop\图片\img2.jpg)







![img3](C:\Users\lenovo\Desktop\图片\img3.jpg)





![img4](C:\Users\lenovo\Desktop\图片\img4.jpg)