package com.android.zjctools.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.zjcutils.R
import kotlinx.android.synthetic.main.z_widget_custom_toast.view.*


object ZToastUtils {
     fun show(context: Context,
              text: String="加载中",
              layoutId:Int=R.layout.z_widget_custom_toast,
              duration: Int=Toast.LENGTH_SHORT,
              gravity: Int=Gravity.CENTER,) {
         val v: View = LayoutInflater.from(context).inflate(layoutId, null)
         v?.zToastContent?.let { it.text = text }
         var toast  = Toast(context)
         toast.duration = duration
         toast.setGravity(gravity, 0, 0)
         toast.view = v
         toast.show()
    }

}