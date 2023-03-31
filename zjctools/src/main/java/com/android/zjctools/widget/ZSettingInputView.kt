package com.android.zjctools.widget

import android.content.Context
import android.text.InputFilter
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.zjctools.utils.ZColor.byRes
import com.android.zjctools.utils.ZDimen
import com.android.zjctools.utils.ZDimen.sp2px
import com.android.zjcutils.R

/**
 * created zjun 2023-03-11
 */
class ZSettingInputView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    private val zjcMaxInputCount = 30 //默认最多输入个数
    lateinit var textTitle: TextView
    lateinit var input: EditText
    var mContentView: View
    lateinit var bottomLine: View
    var isShowLine: Boolean
    private val title: String?
    private val hintText: String?
    private val lineColor: Int
    private val titleColor: Int
    private val inputColor: Int
    private var maxInputCounts: Int
    var titleSize: Int
    var descSize: Int
    var bottomLineTop: Int
    var isShowRightArrow = false
    var ivRightArrow: ImageView? = null






    //左侧和输入框默认有  paddingTop 5 dp
    init {
        mContentView = LayoutInflater.from(context).inflate(R.layout.z_widget_setting_input, this)
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZSettingInputView)
        isShowLine = typedArray.getBoolean(R.styleable.ZSettingInputView_zv_siv_Line_enable, false)
        lineColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_line_color, byRes(context, R.color.app_divide))
        titleColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_title_color, byRes(context, R.color.zGray3))
        inputColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_input_color, byRes(context, R.color.zGray3))
        title = typedArray.getString(R.styleable.ZSettingInputView_zv_siv_title_text)
        hintText = typedArray.getString(R.styleable.ZSettingInputView_zv_siv_hint_text)
        maxInputCounts = typedArray.getInt(R.styleable.ZSettingInputView_zv_siv_max_counts, zjcMaxInputCount)
        titleSize = typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_title_size, sp2px(context!!, 14).toFloat()).toInt()
        descSize = typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_input_size, sp2px(context, 14).toFloat()).toInt()
        isShowRightArrow = typedArray.getBoolean(R.styleable.ZSettingInputView_zv_siv_arrow_enable, false)
        bottomLineTop = typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_bottom_line_top, ZDimen.dp2px(context, 5)*1f).toInt()
        typedArray.recycle()
        initView()
        setViews()
    }

    private fun initView() {
        textTitle = mContentView.findViewById(R.id.text_title)
        ivRightArrow = mContentView.findViewById(R.id.ivRightArrow)
        input = mContentView.findViewById(R.id.input)
        bottomLine = mContentView.findViewById(R.id.line)
        bottomLine.setBackgroundColor(lineColor)
        input.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(maxInputCounts)))
    }


    private fun getContent():String=input!!.text.toString()

    private fun setContent(content:String){
        input.setText(content?:"")
    }

    /**
     * 设置最大字数
     * @param maxInputCounts
     */
    fun setMaxInputCounts(maxInputCounts: Int) {
        this.maxInputCounts = maxInputCounts
        input!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxInputCounts))
    }

    /**
     * 设置最大行数
     * @param maxLines
     */
    fun setMaxLines(maxLines: Int) {
        input!!.maxLines = maxLines
    }

    /**
     * 设置SingleLine
     * @param isSingleLine
     */
    fun setSingleLine(isSingleLine: Boolean) {
        input!!.isSingleLine = isSingleLine
    }

    private fun setViews() {
        textTitle!!.setTextColor(titleColor)
        input!!.setTextColor(inputColor)
        textTitle!!.paint.textSize = titleSize.toFloat()
        input!!.paint.textSize = descSize.toFloat()
        val lineLp = bottomLine!!.layoutParams as LayoutParams
        lineLp.topMargin = bottomLineTop
        bottomLine!!.layoutParams = lineLp
        //底部分割线
        if (isShowLine) {
            bottomLine!!.visibility = VISIBLE
        } else {
            bottomLine!!.visibility = INVISIBLE
        }
        if (isShowRightArrow) {
            ivRightArrow!!.visibility = VISIBLE
        } else {
            ivRightArrow!!.visibility = INVISIBLE
        }
        if (!TextUtils.isEmpty(title)) {
            textTitle!!.text = title
        }
        if (!TextUtils.isEmpty(hintText)) {
            input!!.hint = hintText
        }
    }


}