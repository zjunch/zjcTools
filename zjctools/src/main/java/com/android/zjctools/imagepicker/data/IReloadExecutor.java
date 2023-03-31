package com.android.zjctools.imagepicker.data;

import com.android.zjctools.imagepicker.bean.ImageItem;

import java.util.List;

public interface IReloadExecutor {

    /**
     * 根据当前选择列表，重新刷新选择器选择状态
     *
     * @param selectedList 当前选中列表
     */
    void reloadPickerWithList(List<ImageItem> selectedList);
}
