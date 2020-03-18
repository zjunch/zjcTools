package com.android.zjctools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

/**
 *  created zjun 2019-09-23
 */

public class ZSettingView extends LinearLayout {

    TextView tvTitle,tvCenter,tvDesc,tvRightStar,tvPoint;
    View mContentView,line;
    boolean isShowLine,isShowPoint,isShowRightStar,isShowRightArrow;
    private  String title,desc,descHint;
    ImageView ivRightArrow;
    private  int descColorId,titleColorId,centerColorId;
    int titleSize,centerSize,descSize;

    public ZSettingView(Context context) {
        super(context);
    }

    public ZSettingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.zjc_layout_setting_view,this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZSettingView);
        isShowLine = typedArray.getBoolean(R.styleable.ZSettingView_zjc_sv_Line_enable, true);
        isShowPoint = typedArray.getBoolean(R.styleable.ZSettingView_zjc_sv_point_enable, false);
        isShowRightStar = typedArray.getBoolean(R.styleable.ZSettingView_zjc_sv_star_enable, false);
        isShowRightArrow = typedArray.getBoolean(R.styleable.ZSettingView_zjc_sv_arrow_enable, true);
        descColorId = typedArray.getColor(R.styleable.ZSettingView_zjc_sv_desc_color, getResources().getColor(R.color.zjcGray3));
        titleColorId=typedArray.getColor(R.styleable.ZSettingView_zjc_sv_title_Color, getResources().getColor(R.color.zjcGray3));
        centerColorId=typedArray.getColor(R.styleable.ZSettingView_zjc_sv_center_color, getResources().getColor(R.color.zjcGray3));
        title= typedArray.getString(R.styleable.ZSettingView_zjc_sv_title_text);
        descHint= typedArray.getString(R.styleable.ZSettingView_zjc_sv_desc_Hint);
        desc= typedArray.getString(R.styleable.ZSettingView_zjc_sv_desc_text);
        titleSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zjc_sv_title_size, ZDimen.sp2px(14));
        centerSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zjc_sv_center_size, ZDimen.sp2px(14));
        descSize= (int) typedArray.getDimension(R.styleable.ZSettingView_zjc_sv_desc_size, ZDimen.sp2px(14));
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
        tvTitle.setTextColor(titleColorId);
        tvDesc.setTextColor(descColorId);
        tvCenter.setTextColor(centerColorId);
        tvPoint.setVisibility(isShowPoint?View.VISIBLE:View.GONE);
        line.setVisibility(isShowLine?View.VISIBLE:View.GONE);
        tvRightStar.setVisibility(isShowRightStar?View.VISIBLE:View.GONE);
        ivRightArrow.setVisibility(isShowRightArrow?View.VISIBLE:View.GONE);
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
}
