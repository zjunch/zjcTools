package com.android.zjctools.utils;

public class ZTimeUtils {


    /**
     * 获取时间  00：12：23   如时评播放时长
     * @param currentMillis
     * @return
     */
    public  static  String  getRecordTime(long currentMillis){
        int totalSeconds = (int) (currentMillis / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        String time= hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
        return time;
    }
}
