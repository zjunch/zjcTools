package com.android.zjctools.display;

import android.view.View;
import android.widget.LinearLayout;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.router.ZParams;
import com.android.zjctools.router.ZRouter;

import java.util.List;

import androidx.viewpager.widget.ViewPager;


/**
 * 展示图片集合类
 */
public class DisplayMultiActivity extends ZBActivity {
    ViewPager viewPager;
     LinearLayout dotLayout;

    private DisplayAdapter adapter;
    private List<String> imagePaths;
    private int selected;
    private View dotView;
    private View[] dotViews;

    @Override
    protected int layoutId() {
        return R.layout.activity_display_images;
    }

    @Override
    protected void initUI() {
        viewPager=findViewById(R.id.display_view_pager);
        dotLayout=findViewById(R.id.display_dot_layout);
    }

    @Override
    protected void initData() {
        ZParams params = (ZParams) ZRouter.getParcelable(mActivity);
        selected = params.what;
        imagePaths = params.strList;

        dotViews = new View[imagePaths.size()];
        for (int i = 0; i < imagePaths.size(); i++) {
            dotView = new View(mActivity);
            dotViews[i] = dotView;
            dotView.setBackgroundResource(R.color.zjcWhite);
            if (i == selected) {
                dotView.setBackgroundResource(R.color.picture_in);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 0);
            lp.width = 20;
            lp.height = 20;
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            dotLayout.addView(dotView, lp);
        }
        adapter = new DisplayAdapter(mActivity, imagePaths);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selected);
        adapter.setListener(() -> {onFinish();});

        setPageListener();
    }

    private void setPageListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotViews.length; i++) {
                    dotViews[i].setBackgroundResource(R.color.zjc_white_87);
                    if (i == position) {
                        dotViews[position].setBackgroundResource(R.color.picture_in);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
