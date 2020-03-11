package com.android.zjctools.appupdate;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZAppUpdateBean implements Serializable {
    public ZAppUpdateBean(String apkUrl, String apkDirectory) {
        this.apkUrl = apkUrl;
        this.apkDirectory = apkDirectory;
    }

    public int bgIcon=-1;
    public List<String> updateContents;
    public boolean isForce;
    public String apkUrl;
    public String apkDirectory;
    public String apkNewVersion;
    public int appLogoIcon=-1;
}
