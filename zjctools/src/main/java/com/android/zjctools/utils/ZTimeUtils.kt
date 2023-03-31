package com.android.zjctools.utils

object ZTimeUtils {
    /**
     * 获取时间  00：12：23   如时评播放时长
     * @param currentMillis
     * @return
     */
    fun getRecordTime(currentMillis: Long): String {
        val totalSeconds = (currentMillis / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format("%02d:%02d:%02d", hours, minutes, seconds) else String.format("%02d:%02d", minutes, seconds)
    }
}