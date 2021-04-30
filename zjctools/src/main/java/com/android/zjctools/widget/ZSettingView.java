package com.android.zjctools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import androidx.annotation.Nullable;

/**
 *  created zjun 2019-09-23
 */

public class ZSettingView extends LinearLayout {

    TextView tvTitle,tvCenter,tvDesc,tvRightStar,tvPoint;//左侧标题 中间标题 有侧描述 左侧文字右侧的星  左侧的点
    View mContentView,line;
    boolean isShowLine,isShowPoint,isShowRightStar,isShowRightArrow; //是否显示分隔线、左侧的点，左侧文字右侧的星、右侧的进入箭头
    private  String title,desc,descHint;
    ImageView ivRightArrow; //右侧的箭头
    private  int titleColor,centerColor,descColor;//左侧的标题，中间的标题，右侧的描述内容 文字颜色
    int titleSize,centerSize,descSize; //左侧的标题，中间的标题，右侧的描述内容 文字大小
    int arrowResId;   //右侧箭头图标resId
    int titleDrawPadding ;//左侧标题左侧的drawpadding
    int drawLeftResId;   //左侧图标id
    public ZSettingView(Context context) {
        super(context);
    }

    public ZSettingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.z_layout_setting_view,this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZSettingView);
        isShowLine = typedArray.getBoolean(R.styleable.ZSettingView_zv_sv_Line_enable, true);
        isShowPoint = typedArray.getBoolean(R.styleable.ZSettingView_zv_sv_point_enable, false);
        isShowRightStar = typedArray.getBoolean(R.styleable.ZSettingView_zv_sv_star_enable, false);
        isShowRightArrow = typedArray.getBoolean(R.styleable.ZSettingView_zv_sv_arrow_enable, true);
        descColor = typedArray.getColor(R.styleable.ZSettingView_zv_sv_desc_color, ZColor.byRes(context,R.color.zGray3));
        titleColor=typedArray.getColor(R.styleable.ZSettingView_zv_sv_title_color, ZColor.byRes(context,R.color.zGray3));
        centerColor=typedArray.getColor(R.styleable.ZSettingView_zv_sv_center_color, ZColor.byRes(context,R.color.zGray3));
        title= typedArray.getString(R.styleable.ZSettingView_zv_sv_title_text);
        descHint= typedArray.getString(R.styleable.ZSettingView_zv_sv_desc_Hint);
        desc= typedArray.getString(R.styleable.ZSettingView_zv_sv_desc_text);
        titleSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zv_sv_title_size, ZDimen.sp2px(context,14));
        centerSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zv_sv_center_size, ZDimen.sp2px(context,14));
        descSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zv_sv_desc_size, ZDimen.sp2px(context,14));
        arrowResId=typedArray.getResourceId(R.styleable.ZSettingView_zv_sv_arrow_resId,R.drawable.arrow);
        titleDrawPadding= (int) typedArray.getDimension(R.styleable.ZSettingView_zv_sv_title_drawPadding, ZDimen.dp2px(context,10));
        drawLeftResId=typedArray.getResourceId(R.styleable.ZSettingView_zv_sv_title_drawLeft_resId,-1);
        typedArray.recycle();
        initView();
        setViews();
    }

    private void initView() {
        tvTitle=mContentView.findViewById(R.id.tvTitle);
        tvDesc=mContentView.findViewById(R.id.tvDesc);
        tvCenter=mContentView.findViewById(R.id.tvCenter);
        line=mContentView.findViewById(R.id.line);
        tvPoint=mContentView.findViewById(R.id.tvPoint);
        tvRightStar=mContentView.findViewById(R.id.tvRightStar);
        ivRightArrow=mContentView.findViewById(R.id.ivRightArrow);
    }

    private void setViews() {
        tvTitle.getPaint().setTextSize(titleSize);
        tvDesc.getPaint().setTextSize(descSize);
        tvCenter.getPaint().setTextSize(centerSize);
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }

        if(!TextUtils.isEmpty(desc)){
            tvDesc.setText(desc);
        }else if(!TextUtils.isEmpty(descHint)){
            tvDesc.setHint(descHint);
        }else{
            tvDesc.setHint("");
        }
        tvTitle.setTextColor(titleColor);
        tvDesc.setTextColor(descColor);
        tvCenter.setTextColor(centerColor);
        tvPoint.setVisibility(isShowPoint?View.VISIBLE:View.GONE);
        line.setVisibility(isShowLine?View.VISIBLE:View.GONE);
        tvRightStar.setVisibility(isShowRightStar?View.VISIBLE:View.GONE);
        ivRightArrow.setVisibility(isShowRightArrow?View.VISIBLE:View.GONE);
        ivRightArrow.setImageResource(arrowResId);
        if(drawLeftResId!=-1){
            Drawable drawable=getResources().getDrawable(drawLeftResId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvTitle.setCompoundDrawablePadding(titleDrawPadding);
            tvTitle.setCompoundDrawables(drawable,null,null,null);
        }else{
            tvTitle.setCompoundDrawables(null,null,null,null);
        }

    }


    /**
     * 设置标题内容
     * @param title
     */
    public void setTitle(String title){
        if(tvTitle!=null){
            tvTitle.setText(title);
        }
    }


    /**
     *左侧小红点背景
     */
    private  void setLeftPointBgR(int resId){
        tvPoint.setBackgroundResource(resId);
    }


    /**
     * 中间的textview
     * @param desc
     */
    public void setCenterText(String desc){
        if(tvCenter!=null){
            tvCenter.setText(desc);
        }
    }


    /**
     * 设置右侧内容
     * @param desc
     */
    public void setDesc(String desc){
        if(tvDesc!=null){
            tvDesc.setText(desc);
        }
    }


    /**
     * 获取右侧的描述
     * @return
     */
    public  String getDesc(){
        if(tvDesc.getText()==null){
            return  "";
        }
        return tvDesc.getText().toString();
    }

    /**
     * 设置右侧箭头隐藏显示
     * @param isShow
     */
   public   void  setRightArrowShow(boolean isShow){
       ivRightArrow.setVisibility(isShow?View.VISIBLE:View.GONE);
   }


    public  void  setDescShow(boolean isShow){
        tvDesc.setVisibility(isShow?View.VISIBLE:View.GONE);
    }


    public  TextView getTitleText(){
       return  tvTitle;
    }

    public  TextView getTvCenter(){
        return  tvCenter;
    }

    public  TextView getTvDesc(){
        return  tvDesc;
    }

    public  ImageView getRightArrow(){
        return  ivRightArrow;
    }

    public  TextView getTvPoint(){
        return  tvPoint;
    }

    public  TextView getTvRightStar(){
        return  tvRightStar;
    }


}
