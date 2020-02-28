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


public class SettingView extends LinearLayout {

    TextView tvTitle,tvDesc,tvRightStar;
    View mContentView,line;
    boolean isShowLine,isShowPoint,isShowRightStar,isShowRightArrow;
    private  String title,desc,descHint;
    ImageView ivPoint,ivRightArrow;
    private  int descColorId;

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
        ivPoint=mContentView.findViewById(R.id.ivPoint);
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
        }
        tvDesc.setTextColor(descColorId);
        ivPoint.setVisibility(isShowPoint?View.VISIBLE:View.GONE);
        line.setVisibility(isShowLine?View.VISIBLE:View.GONE);
        tvRightStar.setVisibility(isShowRightStar?View.VISIBLE:View.GONE);
        ivRightArrow.setVisibility(isShowRightArrow?View.VISIBLE:View.GONE);
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
