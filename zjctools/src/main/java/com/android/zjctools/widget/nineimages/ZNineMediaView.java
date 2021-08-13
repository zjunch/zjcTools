package com.android.zjctools.widget.nineimages;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zjctools.model.ZMedia;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;


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
public class ZNineMediaView extends RelativeLayout {
    int mWidth, mHeight;         //view 宽高，注意这里没兼容padding ，so 请用margin
    private List<ZMedia> mMedias;
    private int picWidth, picHeight;        //正常图片的宽高
    private boolean average2Enable = false; //只有两张图的时候，每一张占屏幕的一半
    private boolean average4Enable = false; //四张图的时候，每一张占屏幕的一半
    private int viewMaxCounts = 9;//默认展示个数
    private boolean isShowText = false;
    private boolean viewShowText = false;//是否显示未展示个数
    private boolean viewFullOne = false;
    private boolean viewFullHalf = false;  //1张图和两张平分的的时候 一样大
    int viewXSpaceSize = 5, viewYSpaceSize = 5;   //图片间横向/垂直间隔距离   默认5dp
    private double scale = 1.0 / 1.0;   //高宽比
    private double fullOneScale = 16.0 / 9.0;   //单个图片宽高比
    private int oldSize;  //原图片尺寸
    private int viewCountTextSize;  //未显示数量字体大小
    private int viewCountTextColor;//未显示数量字体颜色
    boolean view12Enable = false;  //三张图，是否开启左边一个右边两个
    int view12_1Height, view12_2Height; //开启左边一个右边两个 左边一个image宽高
    int view12_1Width, view12_2Width;//开启左边一个右边两个 右边两个image宽高

    Context mContext;
    public ZNineMediaView(Context context) {
        this(context, null);
    }

    public ZNineMediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        intValues(attrs);
    }

    private void intValues(AttributeSet attrs) {
        mMedias = new ArrayList<>();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZNineMediaView);
        viewXSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.ZNineMediaView_zMedia_nine_spacex, viewXSpaceSize));
        viewYSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.ZNineMediaView_zMedia_nine_spacey, viewYSpaceSize));
        average2Enable = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_average2_enable, false);
        average4Enable = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_average4_enable, false);
        viewShowText = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_show_un_counts, false);
        viewFullOne = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_full_One, false);
        viewFullHalf = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_half_One, false);
        view12Enable = typedArray.getBoolean(R.styleable.ZNineMediaView_zMedia_nine_12_Enable, false);
        viewMaxCounts = typedArray.getInt(R.styleable.ZNineMediaView_zMedia_nine_max_counts, 9);
        int viewCountSizeSP = typedArray.getInt(R.styleable.ZNineMediaView_zMedia_nine_un_counts_textSzie, 12);
        viewCountTextSize = ZDimen.dp2px(viewCountSizeSP);
        viewCountTextColor = typedArray.getColor(R.styleable.ZNineMediaView_zMedia_nine_un_counts_textColor, Color.BLACK);
        typedArray.recycle();
    }

    /**
     * 正常图片宽高比
     *
     * @param scale
     * @return
     */
    public ZNineMediaView setScale(double scale) {
        this.scale = scale;
        return this;
    }

    /**
     * 一张图片宽高比
     *
     * @param scale
     * @return
     */
    public ZNineMediaView setFullOneScale(double scale) {
        this.fullOneScale = scale;
        return this;
    }


    /**
     * 添加图片urls
     *
     * @param medias
     * @return
     */
    public ZNineMediaView setMedias(List<ZMedia> medias) {
        this.mMedias.clear();
        isShowText = false;
        removeAllViews();
        if (medias == null || medias.size() == 0) {
            return this;
        }
        oldSize = medias.size();
        if (medias.size() > viewMaxCounts && viewShowText) {//超过最大显示数
            //超过数量是否显示文本提示   提示文本
            isShowText = true;
            for (int i = 0; i < viewMaxCounts; i++) {
                this.mMedias.add(medias.get(i));
            }
        } else {
            this.mMedias.addAll(medias);
        }
        for (int i = 0; i < mMedias.size(); i++) {
            View childView=LayoutInflater.from(mContext).inflate(R.layout.layout_item_media,null);
            addView(childView);
        }
        if (isShowText) {  //添加一个覆盖在最后一个imageview的数字文本
            ZCountTextView textView = new ZCountTextView(getContext(), oldSize - mMedias.size());
            textView.setTextColor(viewCountTextColor);
            textView.setTextSize(viewCountTextSize);
            addView(textView);
        }
        return this;
    }


    /**
     * 获取图片宽高
     */
    private void mesurePic() {
        if (mMedias == null || mMedias.size() == 0) {
            return;
        }
        if (viewFullOne == true && mMedias.size() == 1) {//只有一个的时候宽度满屏宽
            picWidth = mWidth;
            picHeight = (int) (picWidth / fullOneScale);  //一行两个，沾满屏宽
        } else if(viewFullHalf == true && mMedias.size() == 1){
            picWidth = (mWidth - viewXSpaceSize) / 2;
            picHeight = (int) (picWidth / scale);
        }else if ((average2Enable && mMedias.size() == 2) || (average4Enable && mMedias.size() == 4)) { //2/4张图直接平分屏幕宽度
            picWidth = (mWidth - viewXSpaceSize) / 2;
            picHeight = (int) (picWidth / scale);
        } else if (mMedias.size() == 3 && view12Enable) {
            view12_2Width = view12_1Width = (mWidth - viewXSpaceSize) / 2;
            view12_1Height = (int) (view12_1Width / scale);
            view12_2Height = (view12_1Height - viewYSpaceSize) / 2;
        } else {                                       //一行三个，沾满屏宽
            picWidth = (mWidth - 2 * viewXSpaceSize) / 3;
            picHeight = (int) (picWidth / scale);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mesurePic();
        mHeight = getSelfHeight();//重新计算高度
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(0).measure(widthMeasureSpec,heightMeasureSpec);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 0 && getChildCount() >= mMedias.size()) {
            //每行两个的是时候
            if ((average2Enable && mMedias.size() == 2) || (average4Enable && mMedias.size() == 4)) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    average2ChildLayout(i, child);
                }

            } else if (view12Enable && mMedias.size() == 3) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    average12ChildLayout(i, child);
                }
            } else {
                //每行三个的是时候
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    average3childLayout(i, child);
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
    private void average12ChildLayout(int index, View child) {
        if (index == 0) {
            child.layout(0, 0, view12_1Width, view12_1Height);
        } else if (index == 1) {
            child.layout(view12_1Width + viewXSpaceSize, 0, view12_1Width + viewXSpaceSize + view12_2Width, view12_2Height);
        } else if (index == 2) {
            child.layout(view12_1Width + viewXSpaceSize, view12_2Height + viewYSpaceSize, view12_1Width + viewXSpaceSize + view12_2Width, view12_2Height + viewYSpaceSize + view12_2Height);
        } else if (isShowText && getChildAt(mMedias.size()) instanceof ZCountTextView) {
            ZCountTextView textView = (ZCountTextView) getChildAt(getChildCount() - 1);
            textView.layout(view12_1Width + viewXSpaceSize, view12_2Height + viewYSpaceSize, view12_1Width + viewXSpaceSize + view12_2Width, view12_2Height + viewYSpaceSize + view12_2Height);
        }
        if (child instanceof RelativeLayout) {
            RelativeLayout childView = (RelativeLayout) child;
            loadItemView(childView, index, picWidth, picHeight);

        }
    }


    /**
     * 2/4张   每行2个
     *
     * @param index
     * @param child
     */
    private void average2ChildLayout(int index, View child) {
        int widthIndex = index % 2;//第几列
        int heightIndex = index / 2;//第几行
        if (child instanceof RelativeLayout) {
            int left = widthIndex * picWidth + widthIndex * viewXSpaceSize;
            int top = heightIndex * viewYSpaceSize + heightIndex * picHeight;
            int right = widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize);
            int bottom = heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight;
            child.layout(left, top, right, bottom);
            RelativeLayout childView = (RelativeLayout) child;
            loadItemView(childView, index, picWidth, picHeight);
        } else if (isShowText && child instanceof ZCountTextView) {//最后一个图片且有CountTextView, 覆盖最后图片上面
            ZCountTextView textView = (ZCountTextView) child;
            index -= 1;
            widthIndex = index % 2;//第几列
            heightIndex = index / 2;//第几行
            textView.layout(widthIndex * picWidth + widthIndex * viewXSpaceSize,
                    heightIndex * viewYSpaceSize + heightIndex * picHeight,
                    widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                    heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight);
        }
    }


    /**
     * 每行3个
     *
     * @param index
     * @param child
     */
    private void average3childLayout(int index, View child) {
        int widthIndex = index % 3;//第几列
        int heightIndex = index / 3;//第几行
        if (child instanceof RelativeLayout) {
            child.layout(widthIndex * picWidth + widthIndex * viewXSpaceSize,
                    heightIndex * viewYSpaceSize + heightIndex * picHeight,
                    widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                    heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight);
            RelativeLayout childView = (RelativeLayout) child;

            loadItemView(childView, index, picWidth, picWidth);
        } else if (isShowText && child instanceof ZCountTextView) {
            index -= 1;
            widthIndex = index % 3;//第几列
            heightIndex = index / 3;//第几行
            ZCountTextView textView = (ZCountTextView) child;
            textView.layout(widthIndex * picWidth + widthIndex * viewXSpaceSize,
                    heightIndex * viewYSpaceSize + heightIndex * picHeight,
                    widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                    heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight);
        }

    }


    /**
     * 需要在设置imageurl之前设置
     *
     * @param itemView
     * @param index
     * @param picWidth
     * @param picHeight
     */
    private void loadItemView(final ViewGroup itemView, final int index, int picWidth, int picHeight) {
        if (mediaLoadListener != null) {
            mediaLoadListener.onImgLoad(itemView, mMedias.get(index), index, picWidth, picHeight);
        }
        itemView.setOnClickListener(v -> {
            if (onclickItemListenr != null) {
                onclickItemListenr.OnclickItem(itemView, index);
            }
        });
    }


    private int getSelfHeight() {
        if (mMedias == null || mMedias.size() == 0) {
            return 0;
        }
        int resultHeight = 0;
        int heightIndex = 0;
        int allCounts = getChildCount();
        if (allCounts > viewMaxCounts) {
            allCounts -= 1;
        }
        //一排两张图片
        if ((average2Enable && allCounts == 2) || average4Enable && allCounts == 4) {
            heightIndex = (int) Math.ceil((double) (mMedias.size()) / 2.0);//行数
        } else {
            //一排三张图片
            heightIndex = (int) Math.ceil((double) (mMedias.size()) / 3.0);//行数
        }
        if (viewFullOne && mMedias.size() == 1) {
            resultHeight = (int) (picWidth / fullOneScale);
        } else if (view12Enable && mMedias.size() == 3) {
            resultHeight = view12_1Height;
        } else {
            resultHeight = (heightIndex - 1) * viewYSpaceSize + heightIndex * picHeight;
        }
        return resultHeight;
    }


    public void setMediaLoadListener(MediaLoadListener imgLoadUrlListener) {
        this.mediaLoadListener = imgLoadUrlListener;
    }

    MediaLoadListener mediaLoadListener;

    public interface MediaLoadListener {
        void onImgLoad(ViewGroup  itemView, ZMedia media, int index, int viewWidth, int viewHeight);
    }


    public void setOnclickItemListenr(OnclickItemListenr onclickItemListenr) {
        this.onclickItemListenr = onclickItemListenr;
    }

    OnclickItemListenr onclickItemListenr;


    public interface OnclickItemListenr {
        void OnclickItem(ViewGroup view, int position);
    }
}