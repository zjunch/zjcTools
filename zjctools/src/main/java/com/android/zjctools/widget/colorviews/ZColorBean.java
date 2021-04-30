package com.android.zjctools.widget.colorviews;

import com.android.zjcutils.R;

public class ZColorBean {
    public int start;
    public int end;
    public int colorId= R.color.zGray54;

    public ZColorBean(int start, int end, int colorId) {
        this.colorId = colorId;
        this.start = start;
        this.end = end;
    }

    public ZColorBean() {
    }
}
