package com.wangly.carousel;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangly on 216/10/28.
 */

public class BannerUtils {

    private List<View> mPointer;//指示器

    private List<ImageView> mPictures;//图片集合

    private ViewPager mViewPager;
    private Context mContext;

    private int currentItem;
    private int oldPostion; //控制轮播指示器的图片的状态

    private ScheduledExecutorService scheduledExecutor;

    private boolean isStart;

    public BannerUtils(Builder builder) {
        this.mContext = builder.mContext;
        this.mViewPager = builder.mViewPager;
        this.mPictures = builder.mPictures;
        this.mPointer = builder.mPoints;
        this.isStart = builder.isStart;
        initViewPager();
    }

    public void initViewPager() {
        ViewPagerScroller scroller = new ViewPagerScroller(this.mContext);
        scroller.initViewPagerScroll(this.mViewPager);
        mViewPager.setOnPageChangeListener(new MyPageListener());
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.mViewPager, this.mPictures);
        mViewPager.setAdapter(adapter);
    }

    /**
     * 开启轮播
     */
    public void onStart() {
        if (this.isStart) {
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutor.scheduleAtFixedRate(runnable, 3, 3, TimeUnit.SECONDS);
        }

    }

    /**
     * 关闭轮播
     */
    public void onCancle() {

        scheduledExecutor.shutdown();
        if (null != handler) {
            handler.removeCallbacks(runnable);
        }
    }


    private class MyPageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            //文字更新
//            title.setText(titles[position%images.size()]);


            //圆点更新
            //更新当前页面为白色的圆点
            if (mPointer != null && mPointer.size() > 0) {


                mPointer.get(position % mPictures.size()).setBackgroundResource(R.drawable.dot_focused);
                //更新上一个页面为灰色的圆点

                mPointer.get(oldPostion % mPictures.size()).setBackgroundResource(R.drawable.dot_nomal);
                //更新上一个页面的位置
            }
            oldPostion = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //切换到Viewpager 当前的页面
            mViewPager.setCurrentItem(currentItem);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentItem++;
            handler.sendEmptyMessage(0);
        }
    };


    public static class Builder {
        private Context mContext;
        private ViewPager mViewPager;
        private List<ImageView> mPictures;
        private List<View> mPoints;
        private boolean isStart;


        public Builder withViewPager(ViewPager viewPager) {
            this.mViewPager = viewPager;
            return this;
        }

        public Builder withContext(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder buildPicture(List<ImageView> pictures) {
            this.mPictures = pictures;
            return this;
        }

        public Builder buildPoint(List<View> point) {
            this.mPoints = point;
            return this;
        }

        public Builder buildStart() {
            this.isStart = true;
            return this;
        }


        public BannerUtils build() {
            return new BannerUtils(this);
        }


    }
}
