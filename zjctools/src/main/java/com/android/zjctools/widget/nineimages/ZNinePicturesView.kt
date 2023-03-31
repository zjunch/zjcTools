package com.android.zjctools.widget.nineimages

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.android.zjctools.utils.ZDimen
import com.android.zjcutils.R
import java.util.*

/**
 * created zjun 2019-12-23
 *
 * 九宫格图片
 * app:zv_nine_full_One="true"
 * app:zv_nine_max_counts="9"
 * app:zv_nine_average2_enable="true"
 * app:zv_nine_show_un_counts="true"
 * app:zv_nine_spacex="4"
 * app:zv_nine_spacey="4"
 * .........
 */
class ZNinePicturesView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    var mWidth = 0
    var mHeight  = 0//view 宽高，注意这里没兼容padding ，so 请用margin

    private var imageUrls: MutableList<String>? = null
    private var picWidth = 0
    private var picHeight= 0 //正常图片的宽高

    private var average2Enable = false //只有两张图的时候，每一张占屏幕的一半
    private var average4Enable = false //四张图的时候，每一张占屏幕的一半
    private var viewMaxCounts = 9 //默认展示个数
    private var isShowText = false
    private var viewShowText = false //是否显示未展示个数
    private var viewFullOne = false
    private var viewFullHalf = false //1张图和两张平分的的时候 一样大
    var viewXSpaceSize = 5
    var viewYSpaceSize = 5 //图片间横向/垂直间隔距离   默认5dp
    private var scale = 1.0 / 1.0 //高宽比
    private var fullOneScale = 16.0 / 9.0 //单个图片宽高比
    private var oldSize= 0 //原图片尺寸

    private var viewCountTextSize = 0 //未显示数量字体大小

    private var viewCountTextColor = 0 //未显示数量字体颜色

    var view12Enable = false //三张图，是否开启左边一个右边两个
    var view12_1Height = 0
    var view12_2Height  = 0 //开启左边一个右边两个 左边一个image宽高

    var view12_1Width = 0
    var view12_2Width  = 0//开启左边一个右边两个 右边两个image宽高
    var mOnItemClickListener: OnItemClickListener? = null
    init {
        intValues(attrs)
    }

    private fun intValues(attrs: AttributeSet?) {
        imageUrls = ArrayList()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZNinePicturesView)
        viewXSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.ZNinePicturesView_zv_nine_spacex, viewXSpaceSize))
        viewYSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.ZNinePicturesView_zv_nine_spacey, viewYSpaceSize))
        average2Enable = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_average2_enable, false)
        average4Enable = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_average4_enable, false)
        viewShowText = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_show_un_counts, false)
        viewFullOne = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_full_One, false)
        viewFullHalf = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_half_One, false)
        view12Enable = typedArray.getBoolean(R.styleable.ZNinePicturesView_zv_nine_12_Enable, false)
        viewMaxCounts = typedArray.getInt(R.styleable.ZNinePicturesView_zv_nine_max_counts, 9)
        val viewCountSizeSP = typedArray.getInt(R.styleable.ZNinePicturesView_zv_nine_un_counts_textSzie, 12)
        viewCountTextSize = ZDimen.dp2px(viewCountSizeSP)
        viewCountTextColor = typedArray.getColor(R.styleable.ZNinePicturesView_zv_nine_un_counts_textColor, Color.BLACK)
        typedArray.recycle()
    }

    /**
     * 正常图片宽高比
     *
     * @param scale
     * @return
     */
    fun setScale(scale: Double): ZNinePicturesView {
        this.scale = scale
        return this
    }

    /**
     * 一张图片宽高比
     *
     * @param scale
     * @return
     */
    fun setFullOneScale(scale: Double): ZNinePicturesView {
        fullOneScale = scale
        return this
    }

    /**
     * 添加图片urls
     *
     * @param imgUrls
     * @return
     */
    fun setImageUrls(imgUrls: List<String>?): ZNinePicturesView {
        imageUrls!!.clear()
        isShowText = false
        removeAllViews()
        if (imgUrls == null || imgUrls.isEmpty()) {
            return this
        }
        oldSize = imgUrls.size
        if (imgUrls.size > viewMaxCounts && viewShowText) { //超过最大显示数
            //超过数量是否显示文本提示   提示文本
            isShowText = true
            for (i in 0 until viewMaxCounts) {
                imageUrls!!.add(imgUrls[i])
            }
        } else {
            imageUrls!!.addAll(imgUrls)
        }
        for (i in imageUrls!!.indices) {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            addView(imageView)
        }
        if (isShowText) {  //添加一个覆盖在最后一个imageview的数字文本
            val textView = ZCountTextView(context, oldSize - imageUrls!!.size)
            textView.setTextColor(viewCountTextColor)
            textView.setTextSize(viewCountTextSize)
            addView(textView)
        }
        return this
    }

    /**
     * 获取图片宽高
     */
    private fun mesurePic() {
        if (imageUrls == null || imageUrls!!.size == 0) return
        if (viewFullOne && imageUrls!!.size == 1) { //只有一个的时候宽度满屏宽
            picWidth = mWidth
            picHeight = (picWidth / fullOneScale).toInt() //一行两个，沾满屏宽
        } else if (viewFullHalf && imageUrls!!.size == 1) {
            picWidth = (mWidth - viewXSpaceSize) / 2
            picHeight = (picWidth / scale).toInt()
        } else if (average2Enable && imageUrls!!.size == 2 || average4Enable && imageUrls!!.size == 4) { //2/4张图直接平分屏幕宽度
            picWidth = (mWidth - viewXSpaceSize) / 2
            picHeight = (picWidth / scale).toInt()
        } else if (imageUrls!!.size == 3 && view12Enable) {
            view12_1Width = (mWidth - viewXSpaceSize) / 2
            view12_2Width = view12_1Width
            view12_1Height = (view12_1Width / scale).toInt()
            view12_2Height = (view12_1Height - viewYSpaceSize) / 2
        } else {                                       //一行三个，沾满屏宽
            picWidth = (mWidth - 2 * viewXSpaceSize) / 3
            picHeight = (picWidth / scale).toInt()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mesurePic()
        mHeight = selfHeight //重新计算高度
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (childCount > 0 && childCount >= imageUrls!!.size) {
            //每行两个的是时候
            if (average2Enable && imageUrls!!.size == 2 || average4Enable && imageUrls!!.size == 4) {
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    average2ChildLayout(i, child)
                }
            } else if (view12Enable && imageUrls!!.size == 3) {
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    average12ChildLayout(i, child)
                }
            } else {
                //每行三个的是时候
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    average3childLayout(i, child)
                }
            }
        }
    }

    /**
     * 3张 左边一个右边两个
     *
     * @param index
     * @param child
     */
    private fun average12ChildLayout(index: Int, child: View) {
        if (index == 0) {
            child.layout(0, 0, view12_1Width, view12_1Height)
        } else if (index == 1) {
            child.layout(
                view12_1Width + viewXSpaceSize,
                0,
                view12_1Width + viewXSpaceSize + view12_2Width,
                view12_2Height
            )
        } else if (index == 2) {
            child.layout(
                view12_1Width + viewXSpaceSize,
                view12_2Height + viewYSpaceSize,
                view12_1Width + viewXSpaceSize + view12_2Width,
                view12_2Height + viewYSpaceSize + view12_2Height
            )
        } else if (isShowText && getChildAt(imageUrls!!.size) is ZCountTextView) {
            val textView = getChildAt(childCount - 1) as ZCountTextView
            textView.layout(
                view12_1Width + viewXSpaceSize,
                view12_2Height + viewYSpaceSize,
                view12_1Width + viewXSpaceSize + view12_2Width,
                view12_2Height + viewYSpaceSize + view12_2Height
            )
        }
        if (child is ImageView) {
            loadImageView(child, index, picWidth, picHeight)
        }
    }

    /**
     * 2/4张   每行2个
     *
     * @param index
     * @param child
     */
    private fun average2ChildLayout(index: Int, child: View) {
        var index = index
        var widthIndex = index % 2 //第几列
        var heightIndex = index / 2 //第几行
        if (child is ImageView) {
            val left = widthIndex * picWidth + widthIndex * viewXSpaceSize
            val top = heightIndex * viewYSpaceSize + heightIndex * picHeight
            val right = widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize)
            val bottom = heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight
            child.layout(left, top, right, bottom)
            loadImageView(child, index, picWidth, picHeight)
        } else if (isShowText && child is ZCountTextView) { //最后一个图片且有CountTextView, 覆盖最后图片上面
            index -= 1
            widthIndex = index % 2 //第几列
            heightIndex = index / 2 //第几行
            child.layout(
                widthIndex * picWidth + widthIndex * viewXSpaceSize,
                heightIndex * viewYSpaceSize + heightIndex * picHeight,
                widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight
            )
        }
    }

    /**
     * 每行3个
     *
     * @param index
     * @param child
     */
    private fun average3childLayout(index: Int, child: View) {
        var index = index
        var widthIndex = index % 3 //第几列
        var heightIndex = index / 3 //第几行
        if (child is ImageView) {
            child.layout(
                widthIndex * picWidth + widthIndex * viewXSpaceSize,
                heightIndex * viewYSpaceSize + heightIndex * picHeight,
                widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight
            )
            loadImageView(child, index, picWidth, picHeight)
        } else if (isShowText && child is ZCountTextView) {
            index -= 1
            widthIndex = index % 3 //第几列
            heightIndex = index / 3 //第几行
            child.layout(
                widthIndex * picWidth + widthIndex * viewXSpaceSize,
                heightIndex * viewYSpaceSize + heightIndex * picHeight,
                widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight
            )
        }
    }

    /**
     * 需要在设置imageurl之前设置
     *
     * @param imageView
     * @param index
     * @param picWidth
     * @param picHeight
     */
    private fun loadImageView(imageView: ImageView, index: Int, picWidth: Int, picHeight: Int) {
        mImgLoadUrlListener?.onImgLoad(imageView, imageUrls!![index], index, picWidth, picHeight)
        imageView.setOnClickListener { v: View? ->
            mOnItemClickListener?.onClickItem(imageView, index)
        }
    }

    private val selfHeight: Int
        private get() {
            if (imageUrls == null || imageUrls!!.size == 0) {
                return 0
            }
            var resultHeight = 0
            var heightIndex = 0
            var allCounts = childCount
            if (allCounts > viewMaxCounts) {
                allCounts -= 1
            }
            //一排两张图片
            heightIndex =
                if (average2Enable && allCounts == 2 || average4Enable && allCounts == 4) {
                    Math.ceil(imageUrls!!.size.toDouble() / 2.0).toInt() //行数
                } else {
                    //一排三张图片
                    Math.ceil(imageUrls!!.size.toDouble() / 3.0).toInt() //行数
                }
            resultHeight = if (viewFullOne && imageUrls!!.size == 1) {
                (picWidth / fullOneScale).toInt()
            } else if (view12Enable && imageUrls!!.size == 3) {
                view12_1Height
            } else {
                (heightIndex - 1) * viewYSpaceSize + heightIndex * picHeight
            }
            return resultHeight
        }




    fun setImgLoadUrlListener(imgLoadUrlListener: ImgLoadUrlListener?) {
        this.mImgLoadUrlListener = imgLoadUrlListener
    }

    var mImgLoadUrlListener: ImgLoadUrlListener? = null

    interface ImgLoadUrlListener {
        fun onImgLoad(imageView: ImageView, url: String?,
            index: Int, viewWidth: Int, viewHeight: Int)
    }


    /**
     * 点击监听
     */
    fun setItemClickListener(onclickItemListener: OnItemClickListener?) {
        this.mOnItemClickListener = onclickItemListener
    }


    interface OnItemClickListener {
        fun onClickItem(view: View?, position: Int)
    }


}