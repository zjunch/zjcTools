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

import com.android.zjcutils.R;

/**
 *  created zjun 2019-09-23
 */

public class SettingView extends LinearLayout {

    TextView tvTitle,tvDesc,tvRightStar,tvPoint;
    View mContentView,line;
    boolean isShowLine,isShowPoint,isShowRightStar,isShowRightArrow;
    private  String title,desc,descHint;
    ImageView ivRightArrow;
    private  int descColorId,titleColorId;

    public SettingView(Context context) {
        super(context);
    }

    public SettingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.layout_setting_view,this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingView);
        isShowLine = typedArray.getBoolean(R.styleable.SettingView_isShowLine, true);
        isShowPoint = typedArray.getBoolean(R.styleable.SettingView_isShowPoint, false);
        isShowRightStar = typedArray.getBoolean(R.styleable.SettingView_isShowRightStart, false);
        isShowRightArrow = typedArray.getBoolean(R.styleable.SettingView_isShowRightArrow, true);
        descColorId = typedArray.getColor(R.styleable.SettingView_descColor, getResources().getColor(R.color.zjcGray3));
        titleColorId=typedArray.getColor(R.styleable.SettingView_titleColor, getResources().getColor(R.color.zjcGray3));
        title= typedArray.getString(R.styleable.SettingView_titleText);
        descHint= typedArray.getString(R.styleable.SettingView_descHint);
        desc= typedArray.getString(R.styleable.SettingView_descText);
        typedArray.recycle();
        initView();
        setViews();
    }

    private void initView() {
        tvTitle=mContentView.findViewById(R.id.tvTitle);
        tvDesc=mContentView.findViewById(R.id.tvDesc);
        line=mContentView.findViewById(R.id.line);
        tvPoint=mContentView.findViewById(R.id.tvPoint);
        tvRightStar=mContentView.findViewById(R.id.tvRightStar);
        ivRightArrow=mContentView.findViewById(R.id.ivRightArrow);
    }

    private void setViews() {
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
     * 设置右侧内容
     * @param desc
     */
    public void setText(String desc){
        if(tvDesc!=null){
            tvDesc.setText(desc);
        }
    }


    /**
     * 获取右侧的描述
     * @return
     */
    public  String getText(){
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

}
