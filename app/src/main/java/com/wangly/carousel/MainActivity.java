package com.wangly.carousel;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<View> dots;
    private ViewPager mViewPager;
    private List<ImageView> images;
    private BannerUtils bannerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        initData();
        bannerUtils=  new BannerUtils.Builder()
               .buildPicture(images)
               .buildPoint(dots)
               .withContext(this)
               .withViewPager(mViewPager)
               .buildStart()
               .build();
        bannerUtils.onStart();
    }

    //TODO:模拟Server 数据后期在更改这些改方法还可以做封装

    private void initData() {
        //图片的内容
        images = new ArrayList<>();

        String url [] = getResources().getStringArray(R.array.ImageData);

        for (int i = 0; i < url.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(this).load(url[i])
                    .error(R.drawable.ic_launcher)
                    .into(imageView);
            images.add(imageView);
        }


        //TODO: 改方法也是可以再做封装

        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot1));
        dots.add(findViewById(R.id.dot2));
        dots.add(findViewById(R.id.dot3));
        dots.add(findViewById(R.id.dot4));
        dots.add(findViewById(R.id.dot5));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerUtils.onCancle();
    }
}
