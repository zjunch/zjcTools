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
    public String url;        //图片的网络地址
    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间
    public String compressPath;   //图片的压缩路径，如果有值，则已经压缩，防止重复压缩
    public  boolean isCamera;//是否是拍照的图片
    public String cropPath;   //剪切后的图片，如果不是空,则已经剪切过不需要重复剪切

    protected ZPictureBean(Parcel in) {
        url = in.readString();
        name = in.readString();
        path = in.readString();
        size = in.readLong();
        width = in.readInt();
        height = in.readInt();
        mimeType = in.readString();
        addTime = in.readLong();
        compressPath = in.readString();
        isCamera = in.readByte() != 0;
        cropPath = in.readString();
    }

    public static final Creator<ZPictureBean> CREATOR = new Creator<ZPictureBean>() {
        @Override
        public ZPictureBean createFromParcel(Parcel in) {
            return new ZPictureBean(in);
        }

        @Override
        public ZPictureBean[] newArray(int size) {
            return new ZPictureBean[size];
        }
    };

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

        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(mimeType);
        dest.writeLong(addTime);
        dest.writeString(compressPath);
        dest.writeByte((byte) (isCamera ? 1 : 0));
        dest.writeString(cropPath);
    }


    public ZPictureBean() {
    }



}
