package com.android.zjctools.pick.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Create by zjun on 2019/05/16 21:51
 *
 * 图片实体类，存储图片文件夹信息
 */
public class ZPictureBean implements Serializable, Parcelable {

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间
    public String compressPath;   //图片的压缩路径，防止重复压缩

    /**
     * 图片的路径和创建时间相同就认为是同一张图片
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ZPictureBean) {
            ZPictureBean bean = (ZPictureBean) o;
            return this.path.equalsIgnoreCase(bean.path);
        }

        return super.equals(o);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
    }

    public ZPictureBean() {
    }

    protected ZPictureBean(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
    }

    public static final Creator<ZPictureBean> CREATOR = new Creator<ZPictureBean>() {
        @Override
        public ZPictureBean createFromParcel(Parcel source) {
            return new ZPictureBean(source);
        }

        @Override
        public ZPictureBean[] newArray(int size) {
            return new ZPictureBean[size];
        }
    };
}
