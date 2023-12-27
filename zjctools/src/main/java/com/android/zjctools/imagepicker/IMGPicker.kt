package com.vmloft.develop.library.common.image

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.android.zjctools.image.ZImgLoader
import com.android.zjctools.imagepicker.adapter.PickerItemAdapter
import com.android.zjctools.imagepicker.bean.ImageItem
import com.android.zjctools.imagepicker.bean.selectconfig.BaseSelectConfig
import com.android.zjctools.imagepicker.data.ICameraExecutor
import com.android.zjctools.imagepicker.data.IReloadExecutor
import com.android.zjctools.imagepicker.data.ProgressSceneEnum
import com.android.zjctools.imagepicker.presenter.IPickerPresenter
import com.android.zjctools.imagepicker.views.PickerUiConfig
import com.android.zjctools.imagepicker.views.PickerUiProvider
import com.android.zjctools.imagepicker.views.base.*
import com.android.zjctools.imagepicker.views.wx.WXTitleBar
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDimen
import com.android.zjctools.utils.ZToastUtils
import com.android.zjctools.widget.dialog.ZPDialog
import com.android.zjcutils.R

import java.util.*

/**
 * Create by zjun on 2022/8/4 18:05
 * 描述：
 */
class IMGPicker : IPickerPresenter {

    var mThemeColor=R.color.app_theme_color



    /**
     * 定义选择器样式
     */
    override fun getUiConfig(
        context: Context?
    ): PickerUiConfig {
        val uiConfig = PickerUiConfig()
        //设置主题色
        uiConfig.themeColor = ZColor.byRes(mThemeColor)
        //设置是否显示状态栏
        uiConfig.isShowStatusBar = true
        //设置状态栏颜色
        uiConfig.statusBarColor = ZColor.byRes(R.color.z_app_bg)
        //设置选择器背景
        uiConfig.pickerBackgroundColor = ZColor.byRes(R.color.z_app_bg)
        //设置单图剪裁背景色
        uiConfig.singleCropBackgroundColor = ZColor.byRes(R.color.z_app_bg)
        //设置预览页面背景色
        uiConfig.previewBackgroundColor = ZColor.byRes(R.color.z_app_bg)
        //设置选择器文件夹打开方向
        uiConfig.folderListOpenDirection = PickerUiConfig.DIRECTION_BOTTOM
        //设置文件夹列表距离顶部/底部边距
        uiConfig.folderListOpenMaxMargin = 0
        //设置小红书剪裁区域的背景色
        uiConfig.cropViewBackgroundColor = ZColor.byRes(R.color.z_app_bg)
        //设置文件夹列表距离底部/顶部的最大间距。通俗点就是设置文件夹列表的高
        if (context != null) {
            uiConfig.folderListOpenMaxMargin = ZDimen.dp2px(16)
        }

        //自定义选择器标题栏，底部栏，item，文件夹列表item，预览页面，剪裁页面
        uiConfig.pickerUiProvider = object : PickerUiProvider() {
            // 定制选择器标题栏，默认实现为 WXTitleBar
            override fun getTitleBar(context: Context): PickerControllerView {
                val titleBar = super.getTitleBar(context) as WXTitleBar
                titleBar.centerTitle()
                titleBar.setBackIconID(R.drawable.z_ic_arrow_back)
                titleBar.setBackgroundColor(ZColor.byRes(R.color.z_app_bg))
                titleBar.setTitleTextColor(ZColor.byRes(R.color.zGray3))
                titleBar.setCompleteBackground(null, null)
                titleBar.setCompleteTextColor(ZColor.byRes(R.color.app_theme_color), ZColor.byRes(R.color.app_theme_color))
                return titleBar
            }

            // 定制选择器底部栏，返回null即代表没有底部栏，默认实现为 WXBottomBar
            override fun getBottomBar(context: Context): PickerControllerView {
                val bottomBar = super.getBottomBar(context)
                bottomBar.setBackgroundColor(ZColor.byRes(R.color.app_theme_color))
                return bottomBar
            }

            // 定制选择器item,默认实现为 WXItemView
            override fun getItemView(context: Context): PickerItemView {
                return super.getItemView(context)
            }

            // 定制选择器文件夹列表item,默认实现为 WXFolderItemView
            override fun getFolderItemView(context: Context): PickerFolderItemView {
                return super.getFolderItemView(context)
            }

            // 定制选择器预览页面,默认实现为 WXPreviewControllerView
            override fun getPreviewControllerView(context: Context): PreviewControllerView {
                return super.getPreviewControllerView(context)
            }

            // 定制选择器单图剪裁页面,默认实现为 WXSingleCropControllerView
            override fun getSingleCropControllerView(context: Context): SingleCropControllerView {
                return super.getSingleCropControllerView(context)
            }
        }
        return uiConfig
    }

    /**
     * 弹出提示
     *
     * @param msg     提示文本
     */
    override fun tip(context: Context?, msg: String) {
        context?.let {
            ZToastUtils.show(context,msg)
        }
    }

    /**
     * 选择超过数量限制提示
     *
     * @param maxCount 最大数量
     */
    override fun overMaxCountTip(context: Context?, maxCount: Int) {
        tip(context, "最多选择 ${maxCount} 个")
    }

    /**
     * 显示loading加载框，注意需要调用show方法
     * 当progressSceneEnum==当ProgressSceneEnum.loadMediaItem 时，代表在加载媒体文件时显示加载框
     * 目前框架内规定，当文件夹内媒体文件少于1000时，强制不显示加载框，大于1000时才会执行此方法
     * 当progressSceneEnum==当ProgressSceneEnum.crop 时，代表是剪裁页面的加载框
     *
     * @param activity 启动加载框的 activity
     * @param scene {@link ProgressSceneEnum}
     *
     * @return DialogInterface 对象，用于关闭加载框，返回null代表不显示加载框
     */
    override fun showProgressDialog(activity: Activity?, scene: ProgressSceneEnum): DialogInterface? {
        if (activity == null) {
            return null
        }
        val dialog = AppCompatDialog(activity)
        val loadView = ZPDialog(activity, ZPDialog.Type.PROGRESS)
        dialog.setContentView(loadView)
        dialog.show()
        return dialog
    }

    /**
     * 拦截选择器取消操作，用于弹出二次确认框
     *
     * @param activity 当前选择器页面
     * @param selectedList 当前已经选择的文件列表
     * @return true:则拦截选择器取消， false，不处理选择器取消操作
     */
    override fun interceptPickerCancel(activity: Activity?, selectedList: ArrayList<ImageItem>): Boolean {
        return false
    }

    /**
     * <p>
     * 图片点击事件拦截，如果返回true，则不会执行选中操纵，
     * 如果要拦截此事件并且要执行选中请调用：adapter.preformCheckItem()
     * 此方法可以用来跳转到任意一个页面，比如自定义的预览
     *
     * @param activity        上下文
     * @param imageItem       当前图片
     * @param selectImageList 当前选中列表
     * @param allSetImageList 当前文件夹所有图片
     * @param selectConfig    选择器配置项，如果是微信样式，则selectConfig继承自MultiSelectConfig
     *                        如果是小红书剪裁样式，则继承自CropSelectConfig
     * @param adapter         当前列表适配器，用于刷新数据
     * @param isClickCheckBox 是否点击item右上角的选中框
     * @param reloadExecutor  刷新器
     * @return 是否拦截
     */
    override fun interceptItemClick(
        activity: Activity?,
        imageItem: ImageItem,
        selectImageList: ArrayList<ImageItem>,
        allSetImageList: ArrayList<ImageItem>,
        selectConfig: BaseSelectConfig,
        adapter: PickerItemAdapter,
        isClickCheckBox: Boolean,
        reloadExecutor: IReloadExecutor?
    ): Boolean {
        return false
    }

    /**
     * 拍照点击事件拦截
     *
     * @param activity  当前activity
     * @param takePhoto 拍照接口
     * @return 是否拦截
     */
    override fun interceptCameraClick(activity: Activity?, takePhoto: ICameraExecutor): Boolean {
        return false
    }

    /**
     * 图片加载，在安卓10上，外部存储的图片路径只能用Uri加载，私有目录的图片可以用绝对路径加载
     * 所以这个方法务必需要区分有uri和无uri的情况
     * 一般媒体库直接扫描出来的图片是含有uri的，而剪裁生成的图片保存在私有目录中，因此没有uri，只有绝对路径
     * 所以这里需要做一个兼容处理
     *
     * @param view        imageView
     * @param item        图片信息
     * @param size        加载尺寸
     * @param isThumbnail 是否是缩略图
     */
    override fun displayImage(view: View, item: ImageItem, size: Int, isThumbnail: Boolean) {
        var res = if (item.uri != null) item.uri else item.path
        if (isThumbnail) {
            ZImgLoader.loadThumbnail(view as ImageView, res)
        } else {
            ZImgLoader.loadCover(view as ImageView, res, defaultResId = 0)
        }
    }

    /**
     * 拦截选择器完成按钮点击事件
     *
     * @param activity     当前选择器activity
     * @param selectedList 已选中的列表
     * @return true:则拦截选择器完成回调， false，执行默认的选择器回调
     */
    override fun interceptPickerCompleteClick(
        activity: Activity?,
        selectedList: ArrayList<ImageItem>,
        selectConfig: BaseSelectConfig
    ): Boolean {
        return false
    }

}