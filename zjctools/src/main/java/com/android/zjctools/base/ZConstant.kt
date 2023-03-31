package com.android.zjctools.base

object ZConstant {
    /**
     * 申请app安装权限
     */
    const val INSTALL_PERMISSION = 8001
    const val INSTALL_RESULT = 8002


    const val CACHE_IMAGES = "images"
    const val CACHE_APK = "apks"

    const val APK_DIRECTORY = "z_apk_directory"
    const val APK_URL = "z_apk_url"
    const val APP_LOGO = "z_app_logo"

    const val App_UPDATE_PROGRESS = "z_update_progress" //app下载更新进度

    const val APP_DOWNLOAD_FINISH = "z_app_download_finish" //app下载完成

    const val App_INSTALL_APP = "z_app_start_install" //安装app


    /**
     * 权限传参的 key
     */
    const val Z_KEY_PERMISSION_ENABLE_DIALOG = "z_key_permission_enable_dialog"
    const val Z_KEY_PERMISSION_TITLE = "z_key_permission_title"
    const val Z_KEY_PERMISSION_MSG = "z_key_permission_msg"
    const val Z_KEY_PERMISSION_LIST = "z_key_permission_list"
    const val Z_KEY_PERMISSION_AGAIN = "z_key_permission_again"
    const val Z_KEY_PERMISSION_REJECT_DIALOG = "z_key_permission_reject_dialog"
    const val Z_KEY_PERMISSION_SETTING_DIALOG = "z_key_permission_setting_dialog"

    /**
     * 图片选择器相关静态变量
     */
    // 选择器请求入口
    const val z_PICK_REQUEST_CODE = 10000

    // 请求拍照
    const val Z_PICK_REQUEST_CODE_TAKE = 10001

    // 剪切照片
    const val Z_PICK_REQUEST_CODE_CROP = 10002

    // 预览图片
    const val Z_PICK_REQUEST_CODE_PREVIEW = 10003

    // 选择图片返回
    const val Z_PICK_RESULT_CODE_BACK = 20000

    // 选择完成返回起始页
    const val Z_PICK_RESULT_CODE_PICTURES = 20001

    // 是否选中原图
    const val Z_KEY_PICK_IS_ORIGIN = "z_key_pick_is_origin"

    // 选择器传参
    const val Z_KEY_PICK_PICTURES = "z_key_pick_pictures"

    //public static final String Z_KEY_PICK_TAKE_PICTURE = "z_key_pick_take_picture";
    // 选择结果
    const val KEY_PICK_RESULT_PICTURES = "key_pick_result_pictures"

    // 当前选择位置
    const val KEY_PICK_CURRENT_SELECTED_POSITION = "key_pick_current_selected_position"

    // 是否预览全部
    const val KEY_PICK_PREVIEW_ALL = "key_pick_preview_all"

    // 通过拍照获取的图片路径集合
    const val KEY_PICK_CAMERA_PATHS = "key_pick_camera_paths"
}