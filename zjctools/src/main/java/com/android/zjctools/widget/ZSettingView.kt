package com.android.zjctools.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDimen
import com.android.zjcutils.R

/**
 * created zjun 2023-03-29
 * android:paddingTop="5dp"
 * android:paddingBottom="5dp"
 */
class ZSettingView : LinearLayout {
    var mContext:Context = context
    lateinit  var mContentView:View
    lateinit  var zlvContent:LinearLayout
    lateinit  var titleText:TextView
    lateinit  var tvCenter:TextView
    lateinit  var tvContent:TextView
    lateinit  var tvRightStar:TextView
    lateinit  var tvPoint:TextView
    lateinit  var bottomLine: View
    var isShowBottomLine = false
    var isShowPoint = false
    var isShowRightStar = false
    var isShowRightArrow  = false//是否显示分隔线、左侧的点，左侧文字右侧的星、右侧的进入箭头
    private var title: String? = null
    private var centerTitle: String? = null
    private var content: String? = null
    private var contentHint: String? = null
    var rightArrow  : ImageView? = null//右侧的箭头
    private var titleColor = 0
    private var centerColor = 0
    private var contentColor = 0
    private var contentHintColor= 0 //左侧的标题，中间的标题，右侧的描述内容 文字颜色
    var titleSize = 0
    var centerSize = 0
    var contentSize  = 0//左侧的标题，中间的标题，右侧的描述内容 文字大小
    var arrowResId = 0//右侧箭头图标resId
    var titleDrawPadding = 0 //左侧标题左侧的drawpadding
    var drawLeftResId= 0 //左侧图标id
    var leftTitleWidth = 0 //左侧宽度

    var pointMarginRightSpace = 0
    private var bottomLineTop = 20 //（此处已经有desc的 padding 6dp）
    private var titlePaddingTop = 20 //底部分割线 距离 上面内容的距离
    private var leftPadding = 0 //左侧间距
    private var rightPadding = 0 //右侧侧间距

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.z_layout_setting_view, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZSettingView)
        //是否显示底部分割线
        isShowBottomLine = typedArray.getBoolean(R.styleable.ZSettingView_zsv_show_bottomLine, true)
        //是否显示圆点
        isShowPoint = typedArray.getBoolean(R.styleable.ZSettingView_zsv_show_point, false)
        //圆点距离标题的宽度
        pointMarginRightSpace = typedArray.getDimension(R.styleable.ZSettingView_zsv_left_point_right_space, ZDimen.dp2px(context, 8).toFloat()).toInt()
        //是否显示星星
        isShowRightStar = typedArray.getBoolean(R.styleable.ZSettingView_zsv_showStar, false)
        //是否显示右侧箭头
        isShowRightArrow = typedArray.getBoolean(R.styleable.ZSettingView_zsv_show_arrow, true)
        //内容的颜色
        contentColor = typedArray.getColor(R.styleable.ZSettingView_zsv_desc_color, ZColor.byRes(context, R.color.zGray3))
        //左侧标题的颜色
        titleColor = typedArray.getColor(R.styleable.ZSettingView_zsv_title_color, ZColor.byRes(context, R.color.zGray3))
        //中间标题颜色
        centerColor = typedArray.getColor(R.styleable.ZSettingView_zsv_center_color, ZColor.byRes(context, R.color.zGray3))
        //内容的hintColor
        contentHintColor = typedArray.getColor(R.styleable.ZSettingView_zsv_desc_hint_color, ZColor.byRes(context, R.color.zGray9))
        //左侧标题
        title = typedArray.getString(R.styleable.ZSettingView_zsv_title_text)
        //中间标题
        centerTitle = typedArray.getString(R.styleable.ZSettingView_zsv_center_text)
        //内容的hint
        contentHint = typedArray.getString(R.styleable.ZSettingView_zsv_desc_Hint)
        //内容
        content = typedArray.getString(R.styleable.ZSettingView_zsv_desc_text)
        //标题字体大小
        titleSize = typedArray.getDimension(R.styleable.ZSettingView_zsv_title_size, ZDimen.sp2px(context, 14).toFloat()).toInt()
        //中间标题大小
        centerSize = typedArray.getDimension(R.styleable.ZSettingView_zsv_center_size, ZDimen.sp2px(context, 14).toFloat()).toInt()
        //内容文字带下哦
        contentSize = typedArray.getDimension(R.styleable.ZSettingView_zsv_desc_size, ZDimen.sp2px(context, 14).toFloat()).toInt()
        //右侧箭头图标id
        arrowResId = typedArray.getResourceId(R.styleable.ZSettingView_zsv_arrow_resId, R.drawable.z_ic_arrow_right_gray)
        //左侧标题的drawPadding
        titleDrawPadding = typedArray.getDimension(R.styleable.ZSettingView_zsv_title_drawPadding, ZDimen.dp2px(context, 10).toFloat()).toInt()
        //左侧图标id
        drawLeftResId = typedArray.getResourceId(R.styleable.ZSettingView_zsv_title_drawLeft_resId, -1)
        //左侧标题的框
        leftTitleWidth = typedArray.getDimension(R.styleable.ZSettingView_zsv_left_title_width, -1f).toInt()
        //左侧标题距离顶部的高度
        titlePaddingTop = typedArray.getDimension(R.styleable.ZSettingView_zsv_padding_top, ZDimen.dp2px(context, 5).toFloat()).toInt()
        //分割线距离底部内容文字的高度
        bottomLineTop = typedArray.getDimension(R.styleable.ZSettingView_zsv_bottom_line_top, ZDimen.dp2px(context, 5).toFloat()).toInt()
        //左边距
        leftPadding = typedArray.getDimension(R.styleable.ZSettingView_zsv_padding_left, ZDimen.dp2px(context, 0).toFloat()).toInt()
        //又编剧
        rightPadding = typedArray.getDimension(R.styleable.ZSettingView_zsv_padding_right, ZDimen.dp2px(context, 0).toFloat()).toInt()
        typedArray.recycle()
        initView()
        setViews()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        zlvContent = mContentView!!.findViewById(R.id.zlvContent)
        titleText = mContentView!!.findViewById(R.id.tvTitle)
        tvContent = mContentView!!.findViewById(R.id.tvDesc)
        tvCenter = mContentView!!.findViewById(R.id.tvCenter)
        bottomLine = mContentView!!.findViewById(R.id.bottomLine)
        tvPoint = mContentView!!.findViewById(R.id.tvPoint)
        tvRightStar = mContentView!!.findViewById(R.id.tvRightStar)
        rightArrow = mContentView!!.findViewById(R.id.ivRightArrow)
    }

    /**
     * 填充数据
     */
    private fun setViews() {
        //左侧标题宽度  等于-1 自适应
        zlvContent!!.setPadding(leftPadding, 0, rightPadding, 0)
        setLeftTitleView()
        setRightArrowView()
        setTextPointView()
        setRightContentView()
        setBottomLineView()
        tvCenter!!.paint.textSize = centerSize.toFloat()
        tvCenter!!.setTextColor(centerColor)
        if (!TextUtils.isEmpty(centerTitle)) titleText!!.text = centerTitle
        tvRightStar!!.visibility = if (isShowRightStar) VISIBLE else GONE
    }


    /**
     * 设置左侧标题
     */
    private  fun setLeftTitleView(){
        //设置宽度
         if (leftTitleWidth != -1) {
             val lp = titleText!!.layoutParams as RelativeLayout.LayoutParams
             lp.width = leftTitleWidth
             titleText!!.layoutParams = lp
         }
        titleText!!.paint.textSize = titleSize.toFloat()
        titleText!!.setTextColor(titleColor)
        titleText!!.setPadding(0, titlePaddingTop, 0, 0)
        if (!TextUtils.isEmpty(title))titleText!!.text = title
        //设置图标
         if (drawLeftResId != -1) {
             val drawable = resources.getDrawable(drawLeftResId)
             drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
             titleText!!.compoundDrawablePadding = titleDrawPadding
             titleText!!.setCompoundDrawables(drawable, null, null, null)
         } else {
             titleText!!.setCompoundDrawables(null, null, null, null)
         }
     }


    /**
     * 设置右侧箭头
     */
   private fun setRightArrowView(){
        val ivRightArrowLp = rightArrow!!.layoutParams as RelativeLayout.LayoutParams
        ivRightArrowLp.topMargin = titlePaddingTop + ZDimen.dp2px(mContext, 3)
        rightArrow!!.layoutParams = ivRightArrowLp
        rightArrow!!.visibility = if (isShowRightArrow) VISIBLE else GONE
        rightArrow!!.setImageResource(arrowResId)
    }

    /**
     * 左侧标题右侧原点
     */
    private fun  setTextPointView(){
        //左侧点距离左侧标题的margin
        val pointLp = tvPoint!!.layoutParams as RelativeLayout.LayoutParams
        pointLp.rightMargin = pointMarginRightSpace
        tvPoint!!.layoutParams = pointLp
        tvPoint!!.visibility = if (isShowPoint) VISIBLE else GONE
    }


    /**
     * 设置右侧内容
     */
    private fun setRightContentView(){
        tvContent!!.setPadding(0, titlePaddingTop, 0, 0)
        tvContent!!.paint.textSize = contentSize.toFloat()
        tvContent!!.setTextColor(contentColor)
        tvContent!!.setHintTextColor(contentHintColor)
        if (!TextUtils.isEmpty(content)) {
            tvContent!!.text = content
        } else if (!TextUtils.isEmpty(contentHint)) {
            tvContent!!.hint = contentHint
        } else {
            tvContent!!.hint = ""
        }
    }

    private fun setBottomLineView(){
        bottomLine!!.visibility = if (isShowBottomLine) VISIBLE else INVISIBLE
        val lp = bottomLine!!.layoutParams as LayoutParams
        lp.topMargin = bottomLineTop
        bottomLine!!.layoutParams = lp
    }

    /**
     * 设置标题内容
     * @param title
     */
    fun setTitle(title: String?) {
        titleText?.text=title?:""
    }

    /**
     * 左侧小红点背景
     */
    private fun setLeftPointBgR(resId: Int) {
        tvPoint!!.setBackgroundResource(resId)
    }

    /**
     * 中间的textview
     * @param desc
     */
    fun setCenterText(desc: String?) {
        if (tvCenter != null) {
            tvCenter!!.text = desc
        }
    }

    /**
     * 设置右侧内容
     * @param desc
     */
    fun setContext(desc: String?) {
        tvContent?.text=desc?:""
    }

    /**
     * 获取右侧的描述
     * @return
     */
    fun getDesc(): String {
        return if (tvContent!!.text == null) "" else tvContent!!.text.toString()
    }

    /**
     * 设置右侧箭头隐藏显示
     * @param isShow
     */
    fun setRightArrowShow(isShow: Boolean) {
        rightArrow!!.visibility = if (isShow) VISIBLE else GONE
    }

    fun setDescShow(isShow: Boolean) {
        tvContent!!.visibility = if (isShow) VISIBLE else GONE
    }
}