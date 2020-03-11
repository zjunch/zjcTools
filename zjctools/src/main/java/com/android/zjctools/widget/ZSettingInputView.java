package com.android.zjctools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.base.ZConstant;
import com.android.zjctools.utils.ZColor;
import com.android.zjcutils.R;

/**
 * created zjun 2020-03-11
 */

public class ZSettingInputView extends RelativeLayout {
    private final  int zjcMaxInputCount=30;//默认最多输入个数
    TextView textTitle;
    EditText input;
    View mContentView,line;
    boolean isShowLine;
    private  String title,hintText;
    private int  lineColor;
    private  int  maxInputCounts;
    public ZSettingInputView(Context context) {
        this(context,null);
    }

    public ZSettingInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.zjc_widget_setting_input,this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZSettingInputView);
        isShowLine = typedArray.getBoolean(R.styleable.ZSettingInputView_zjc_siv_Line_enable, false);
        lineColor = typedArray.getColor(R.styleable.ZSettingInputView_zjc_siv_line_color, ZColor.byRes(R.color.app_divide));
        title= typedArray.getString(R.styleable.ZSettingInputView_zjc_siv_title_text);
        hintText= typedArray.getString(R.styleable.ZSettingInputView_zjc_siv_hint_text);
        maxInputCounts= typedArray.getInt(R.styleable.ZSettingInputView_zjc_siv_max_counts, zjcMaxInputCount);
        typedArray.recycle();
        initView();
        setViews();
    }


    public   String getText(){
        return input.getText().toString().trim();
    }


    public   void setText(String text){
        if(text!=null){
            input.setText(text);
        }else{
            input.setText("");
        }
    }

    private void initView() {
        textTitle=mContentView.findViewById(R.id.text_title);
        input=mContentView.findViewById(R.id.input);
        line=mContentView.findViewById(R.id.line);
        line.setBackgroundColor(lineColor);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputCounts)});
    }


    /**
     * 设置最大字数
     * @param maxInputCounts
     */
    public   void setMaxInputCounts(int maxInputCounts){
        this.maxInputCounts=maxInputCounts;
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputCounts)});
    }


    /**
     * 设置最大行数
     * @param maxLines
     */
    public void setMaxLines(int maxLines){
        input.setMaxLines(maxLines);
    }

    /**
     * 设置SingleLine
     * @param isSingleLine
     */
    public void setSingleLine(boolean isSingleLine){
        input.setSingleLine(isSingleLine);
    }


    private void setViews() {
        if(isShowLine){
            line.setVisibility(VISIBLE);
        }else{
            line.setVisibility(GONE);
        }
        if(!TextUtils.isEmpty(title)){
            textTitle.setText(title);
        }
        if(!TextUtils.isEmpty(hintText)){
            input.setHint(hintText);
        }
    }

}