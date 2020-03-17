package com.android.zjctools.widget.tabview;

public class ZTabBean {
    private int selectResId, unSelectResId;   //选中和未选中的图片资源id
    private String title;
    public ZTabBean(int selectResId, int unSelectResId, String title) {
        this.selectResId = selectResId;
        this.unSelectResId = unSelectResId;
        this.title = title;
    }


    public int getSelectResId() {
        return selectResId;
    }

    public void setSelectResId(int selectResId) {
        this.selectResId = selectResId;
    }

    public int getUnSelectResId() {
        return unSelectResId;
    }

    public void setUnSelectResId(int unSelectResId) {
        this.unSelectResId = unSelectResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
