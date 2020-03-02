package com.android.zjctools.widget.nineimages;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zjctools.glide.GlideApp;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;



/**
 * created zjun 2019-12-23
 *              九宫格图片
 *             app:viewYSpaceSize="10"
 *             app:viewXSpaceSize="10"
 *             app:average2Enable="false"
 *             app:average4Enable="true"
 *             app:viewMaxCounts="7"
 *             app:viewShowText="true"
 *             app:view12Enable="true"
 *             app:viewCountTextColor="@color/white"
 *             app:viewCountTextSize="30"
 *             app:viewFullOne="true"
 */
public class NinePicturesView extends RelativeLayout {
    int mWidth, mHeight;         //view 宽高，注意这里没兼容padding ，so 请用margin
    private List<String> imageUrls;
    private int picWidth, picHeight;        //正常图片的宽高
    private boolean average2Enable = false; //只有两张图的时候，每一张占屏幕的一半
    private boolean average4Enable = false; //四张图的时候，每一张占屏幕的一半
    private int viewMaxCounts = 9;//默认展示个数
    private boolean isShowText=false;
    private  boolean viewShowText=false;//是否显示未展示个数
    private boolean viewFullOne=false;
    int viewXSpaceSize = 5, viewYSpaceSize = 5;   //图片间横向/垂直间隔距离   默认5dp
    private double scale = 1.0 / 1.0;   //高宽比
    private double fullOneScale = 16.0 / 9.0;   //单个图片宽高比
    private int oldSize;  //原图片尺寸
    private int viewCountTextSize;  //未显示数量字体大小
    private int viewCountTextColor;//未显示数量字体颜色
    boolean view12Enable=false;  //三张图，是否开启左边一个右边两个
    int view12_1Height,view12_2Height; //开启左边一个右边两个 左边一个image宽高
    int view12_1Width,view12_2Width;//开启左边一个右边两个 右边两个image宽高


    private  int placeHolderId=R.drawable.img_default_match;  //预加载图id


    public NinePicturesView(Context context) {
        this(context, null);
    }

    public NinePicturesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intValues(attrs);
    }

    private void intValues(AttributeSet attrs) {
        imageUrls = new ArrayList<>();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NinePicturesView);
        viewXSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.NinePicturesView_viewXSpaceSize, viewXSpaceSize));
        viewYSpaceSize = ZDimen.dp2px(typedArray.getInt(R.styleable.NinePicturesView_viewYSpaceSize, viewYSpaceSize));
        average2Enable = typedArray.getBoolean(R.styleable.NinePicturesView_average2Enable, false);
        average4Enable = typedArray.getBoolean(R.styleable.NinePicturesView_average4Enable, false);
        viewShowText = typedArray.getBoolean(R.styleable.NinePicturesView_viewShowText, false);
        viewFullOne = typedArray.getBoolean(R.styleable.NinePicturesView_viewFullOne, false);
        view12Enable = typedArray.getBoolean(R.styleable.NinePicturesView_view12Enable, false);
        viewMaxCounts = typedArray.getInt(R.styleable.NinePicturesView_viewMaxCounts, 9);
        int viewCountSizeSP = typedArray.getInt(R.styleable.NinePicturesView_viewCountTextSize, 12);
        viewCountTextSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,viewCountSizeSP,getResources().getDisplayMetrics());
        viewCountTextColor= typedArray.getColor(R.styleable.NinePicturesView_viewCountTextColor, Color.BLACK);
        typedArray.recycle();
    }

    /**
     * 正常图片宽高比
     * @param scale
     * @return
     */
    public NinePicturesView setScale(double scale) {
        this.scale = scale;
        return this;
    }

    /**
     * 一张图片宽高比
     * @param scale
     * @return
     */
    public NinePicturesView setFullOneScale(double scale) {
        this.fullOneScale = scale;
        return this;
    }

    /**
     * 设置预加载图
     * @param imageId
     */
    public void setPlaceHolderId(int  imageId){
        this.placeHolderId=imageId;
    }


    /**
     * 添加图片urls
     * @param imgUrls
     * @return
     */
    public NinePicturesView setImageUrls(List<String> imgUrls) {
        this.imageUrls.clear();
        isShowText=false;
        removeAllViews();
        if (imgUrls == null || imgUrls.size() == 0) {
            return this;
        }
        oldSize=imgUrls.size();
        if (imgUrls.size() > viewMaxCounts&&viewShowText) {//超过最大显示数
            //超过数量是否显示文本提示   提示文本
            isShowText=true;
            for (int i = 0; i < viewMaxCounts; i++) {
                this.imageUrls.add(imgUrls.get(i));
            }
        } else {
            this.imageUrls.addAll(imgUrls);
        }
        for (int i = 0; i < imageUrls.size(); i++) {
            ImageView imageView=new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.drawable.img_default_match);
            addView(imageView);
        }
        if (isShowText) {  //添加一个覆盖在最后一个imageview的数字文本
            CountTextView textView = new CountTextView(getContext(),oldSize-imageUrls.size());
            textView.setTextColor(viewCountTextColor);
            textView.setTextSize(viewCountTextSize);
            addView(textView);
        }
        return this;
    }


    /**
     * 获取图片宽高
     */
    private void mesurePic(){
        if(imageUrls==null||imageUrls.size()==0){
            return;
        }
        if(viewFullOne==true&&imageUrls.size()==1){//只有一个的时候宽度满屏宽
            picWidth = mWidth  ;
            picHeight = (int) (picWidth / fullOneScale);  //一行两个，沾满屏宽
        }else  if ((average2Enable && imageUrls.size() == 2) || (average4Enable && imageUrls.size() == 4)) { //2/4张图直接平分屏幕宽度
            picWidth = (mWidth  - viewXSpaceSize) / 2;
            picHeight = (int) (picWidth / scale);
        } else if(imageUrls.size()==3&&view12Enable){
            view12_2Width=view12_1Width = (mWidth  - viewXSpaceSize) / 2;
            view12_1Height = (int) (view12_1Width / scale);
            view12_2Height=(view12_1Height-viewYSpaceSize)/2;
        } else {                                       //一行三个，沾满屏宽
            picWidth = (mWidth  - 2 * viewXSpaceSize) / 3;
            picHeight = (int) (picWidth / scale);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        mWidth= MeasureSpec.getSize(widthMeasureSpec);
        mHeight=getSelfHeight();//重新计算高度
        mesurePic();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 0 && getChildCount() >= imageUrls.size()) {
            //每行两个的是时候
            if ((average2Enable && imageUrls.size() == 2) || (average4Enable && imageUrls.size() == 4)) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    average2ChildLayout(i, child);
                }

            }else if(view12Enable&&imageUrls.size()==3){
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    average12ChildLayout(i, child);
                }
            }else{
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
     * @param index
     * @param child
     */
    private void average12ChildLayout(int index, View child){
        if(index==0){
            child.layout(0,0, view12_1Width, view12_1Height);
        }else if(index==1){
            child.layout(view12_1Width+viewXSpaceSize,0, view12_1Width+viewXSpaceSize+view12_2Width, view12_2Height);
        }else if(index==2){
            child.layout(view12_1Width+viewXSpaceSize,view12_2Height+viewYSpaceSize, view12_1Width+viewXSpaceSize+view12_2Width, view12_2Height+viewYSpaceSize+view12_2Height);
        }else if(isShowText&&getChildAt(imageUrls.size())instanceof CountTextView){
            CountTextView textView= (CountTextView) getChildAt(getChildCount()-1);
            textView.layout(view12_1Width+viewXSpaceSize,view12_2Height+viewYSpaceSize, view12_1Width+viewXSpaceSize+view12_2Width, view12_2Height+viewYSpaceSize+view12_2Height);
        }
        if (child instanceof ImageView) {
            ImageView imageView = (ImageView) child;
            loadImageView(imageView, index,picWidth,picHeight);
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
        if(child instanceof ImageView){
            int left=widthIndex * picWidth + widthIndex * viewXSpaceSize;
            int top=heightIndex * viewYSpaceSize + heightIndex * picHeight;
            int right=widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize);
            int bottom=heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight;
            child.layout(left,top,right,bottom);
            ImageView imageView = (ImageView) child;
            loadImageView(imageView, index,picWidth,picHeight);
        }else if(isShowText&&child instanceof CountTextView){//最后一个图片且有CountTextView, 覆盖最后图片上面
           CountTextView textView= (CountTextView) child;
            index-=1;
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
     * @param index
     * @param child
     */
    private void average3childLayout(int index, View child) {
        int widthIndex = index % 3;//第几列
        int heightIndex = index / 3;//第几行
        if(child instanceof  ImageView){
            child.layout(widthIndex * picWidth + widthIndex * viewXSpaceSize,
                    heightIndex * viewYSpaceSize + heightIndex * picHeight,
                    widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                    heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight);
            ImageView imageView = (ImageView) child;
            loadImageView(imageView, index,picWidth,picHeight);
        }else if(isShowText&&child instanceof CountTextView){
            index-=1;
            widthIndex = index % 3;//第几列
            heightIndex = index / 3;//第几行
          CountTextView textView= (CountTextView) child;
            textView.layout(widthIndex * picWidth + widthIndex * viewXSpaceSize,
                    heightIndex * viewYSpaceSize + heightIndex * picHeight,
                    widthIndex * picWidth + (picWidth + widthIndex * viewXSpaceSize),
                    heightIndex * viewYSpaceSize + heightIndex * picHeight + picHeight);
        }

    }




    private void loadImageView(final  ImageView imageView, final int index,int picWidth,int picHeight) {
//        Glide.with(getContext()).load(imageUrls.get(index)).placeholder(placeHolderId).into(imageView);
        GlideApp.with(getContext())
                .load(imageUrls.get(index))
                .centerCrop()
                .placeholder(placeHolderId)
                .into(imageView);
        imageView.setOnClickListener(v -> {
            if (onclickItemListenr != null) {
                onclickItemListenr.OnclickItem(imageView, index);
            }
        });
    }


    private int getSelfHeight() {

        if(imageUrls==null||imageUrls.size()==0){
            return  0;
        }
        int resultHeight=0;
        int heightIndex = 0;
        int allCounts=getChildCount();
        if(allCounts>viewMaxCounts){
            allCounts-=1;
        }
        //一排两张图片
        if ((average2Enable && allCounts == 2) || average4Enable &&allCounts == 4) {
            heightIndex = (int) Math.ceil((double) (imageUrls.size()) / 2.0);//行数
        } else {
            //一排三张图片
            heightIndex = (int) Math.ceil((double) (imageUrls.size()) / 3.0);//行数
        }
        if(viewFullOne&&imageUrls.size()==1){
            resultHeight = (int) (picWidth / fullOneScale);
        }else if(view12Enable&&imageUrls.size()==3){
            resultHeight = view12_1Height;
        }else {
            resultHeight = (heightIndex - 1) * viewYSpaceSize + heightIndex * picHeight;
        }
        return resultHeight;
    }


    public void setOnclickItemListenr(OnclickItemListenr onclickItemListenr) {
        this.onclickItemListenr = onclickItemListenr;
    }

    OnclickItemListenr onclickItemListenr;

    public interface OnclickItemListenr {
        void OnclickItem(View view, int position);
    }
}
