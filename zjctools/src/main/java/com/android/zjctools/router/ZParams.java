package com.android.zjctools.router;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/24.
 *
 * Activity 间用于传递参数的可序列化对象，这里参数命名形式参考 Handler 的 Message 对象
 */
public class ZParams implements Parcelable {
    public static final String VM_ROUTER_PARAMS = "vm_router_params";

    public int what = -1;
    public int arg0;
    public int arg1;
    public String str0;
    public String str1;
    public List<String> strList;
    public Parcelable obj;

    public static final Creator<ZParams> CREATOR = new Creator<ZParams>() {
        @Override
        public ZParams createFromParcel(Parcel in) {
            ZParams p = new ZParams();
            p.what = in.readInt();
            p.arg0 = in.readInt();
            p.arg1 = in.readInt();
            p.str0 = in.readString();
            p.str1 = in.readString();
            p.strList = in.createStringArrayList();
            p.obj = in.readParcelable(Parcelable.class.getClassLoader());
            return p;
        }

        @Override
        public ZParams[] newArray(int size) {
            return new ZParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        dest.writeInt(arg0);
        dest.writeInt(arg1);
        dest.writeString(str0);
        dest.writeString(str1);
        dest.writeStringList(strList);
        dest.writeParcelable(obj, flags);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
