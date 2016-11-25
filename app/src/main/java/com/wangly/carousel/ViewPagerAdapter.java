package com.wangly.carousel;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wangly on 2016/10/28.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> mImages;
    private ViewPager mViewPager;

    public ViewPagerAdapter(ViewPager viewPager, List<ImageView> mPictures) {
        this.mImages = mPictures;
        this.mViewPager = viewPager;
    }

    @Override
    public int getCount() {
        if (null != mImages && mImages.size() > 0) {
            return Integer.MAX_VALUE;
        }
        return 0;

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.i("MyAdapter", "当前页面位置--" + position + "\n" + "处理之后的值：" + position % mImages.size());
        if (null != mImages && mImages.size() > 0) {
            mViewPager.addView(mImages.get(position % mImages.size()));
            return mImages.get(position % mImages.size());
        }
        return super.instantiateItem(container, position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mViewPager.removeView(mImages.get(position % mImages.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
