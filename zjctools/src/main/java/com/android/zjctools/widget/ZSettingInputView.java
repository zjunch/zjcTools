package com.android.zjctools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import static com.android.zjctools.utils.ZDimen.dp2px;
/**
 * created zjun 2020-03-11
 */

public class ZSettingInputView extends RelativeLayout {
    private final  int zjcMaxInputCount=30;//默认最多输入个数
    TextView textTitle;
    EditText input;
    View mContentView,bottomLine;
    boolean isShowLine;
    private  String title,hintText;
    private int  lineColor,titleColor,inputColor;
    private  int  maxInputCounts;
    int titleSize,descSize;
    int bottomLineTop;
    boolean isShowRightArrow=false;
    ImageView ivRightArrow;
    //左侧和输入框默认有  paddingTop 5 dp
    public ZSettingInputView(Context context) {
        this(context,null);
    }

    public ZSettingInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.z_widget_setting_input,this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZSettingInputView);
        isShowLine = typedArray.getBoolean(R.styleable.ZSettingInputView_zv_siv_Line_enable, false);
        lineColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_line_color, ZColor.byRes(context,R.color.app_divide));
        titleColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_title_color, ZColor.byRes(context,R.color.zGray3));
        inputColor = typedArray.getColor(R.styleable.ZSettingInputView_zv_siv_input_color, ZColor.byRes(context,R.color.zGray3));
        title= typedArray.getString(R.styleable.ZSettingInputView_zv_siv_title_text);
        hintText= typedArray.getString(R.styleable.ZSettingInputView_zv_siv_hint_text);
        maxInputCounts= typedArray.getInt(R.styleable.ZSettingInputView_zv_siv_max_counts, zjcMaxInputCount);
        titleSize= (int) typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_title_size, ZDimen.sp2px(context,14));
        descSize= (int) typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_input_size, ZDimen.sp2px(context,14));
        isShowRightArrow = (boolean)typedArray.getBoolean(R.styleable.ZSettingInputView_zv_siv_arrow_enable, false);
        bottomLineTop = (int)typedArray.getDimension(R.styleable.ZSettingInputView_zv_siv_bottom_line_top, dp2px(context, 5));
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
        ivRightArrow=mContentView.findViewById(R.id.ivRightArrow);
        input=mContentView.findViewById(R.id.input);
        bottomLine=mContentView.findViewById(R.id.line);
        bottomLine.setBackgroundColor(lineColor);
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
        textTitle.setTextColor(titleColor);
        input.setTextColor(inputColor);
        textTitle.getPaint().setTextSize(titleSize);
        input.getPaint().setTextSize(descSize);

        RelativeLayout.LayoutParams lineLp= (RelativeLayout.LayoutParams) bottomLine.getLayoutParams();
        lineLp.topMargin=bottomLineTop;
        bottomLine.setLayoutParams(lineLp);
//底部分割线
        if(isShowLine){
            bottomLine.setVisibility(VISIBLE);
        }else{
            bottomLine.setVisibility(INVISIBLE);
        }


        if(isShowRightArrow){
            ivRightArrow.setVisibility(VISIBLE);
        }else{
            ivRightArrow.setVisibility(INVISIBLE);
        }

        if(!TextUtils.isEmpty(title)){
            textTitle.setText(title);
        }
        if(!TextUtils.isEmpty(hintText)){
            input.setHint(hintText);
        }
    }

}