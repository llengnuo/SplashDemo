package demo.imdemo.com.splashdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**指示器的小点
 * Created by Leon on 2017/6/22.
 */

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
