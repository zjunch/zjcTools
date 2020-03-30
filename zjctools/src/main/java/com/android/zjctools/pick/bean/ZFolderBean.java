package com.android.zjctools.pick.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create by zjun on 2019/05/16 21:51
 *
 * 文件夹实体类，存储图片文件夹信息
 */
public class ZFolderBean implements Serializable {

    public String name;  //当前文件夹的名字
    public String path;  //当前文件夹的路径
    public ZPictureBean cover;   //当前文件夹需要要显示的缩略图，默认为最近的一次图片
    public ArrayList<ZPictureBean> pictures;  //当前文件夹下所有图片的集合

    /**
     * 只要文件夹的路径和名字相同，就认为是相同的文件夹
     */
    @Override
    public boolean equals(Object o) {
        try {
            ZFolderBean other = (ZFolderBean) o;
            return this.path.equalsIgnoreCase(other.path) && this.name.equalsIgnoreCase(other.name);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
