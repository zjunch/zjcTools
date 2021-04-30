package com.android.zjctools.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

/**
 * created zjun 2020-03-11
 */
public class ZSearchView extends RelativeLayout {

    View mContentView;
    ImageView ivClear,ivLeftSearchIcon,ivRightSearChIcon;
    TextView tvInput,tvRightSearch;
    EditText evInput;
    InputListener inputListener;
    RelativeLayout rvLeftSearch;
    RelativeLayout rvRightSearch;
    private boolean isShowClear;
    private boolean isCanInput;
    private boolean isShowTvRightSearch ;
    private boolean isShowIvRightSearch ;
    private int leftSearchIcon;
    private int rightSearchIcon;
    private String searchHint;

    public ZSearchView(Context context) {
        this(context, null);
    }

    public ZSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContentView = LayoutInflater.from(context).inflate(R.layout.z_widget_search_layout, this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZSearchView);
        isShowClear = typedArray.getBoolean(R.styleable.ZSearchView_zv_srv_clear_enable, false);
        isShowTvRightSearch = typedArray.getBoolean(R.styleable.ZSearchView_zv_srv_right_tv_search_enable, false);
        isShowIvRightSearch = typedArray.getBoolean(R.styleable.ZSearchView_zv_srv_right_iv_search_enable, false);
        isCanInput = typedArray.getBoolean(R.styleable.ZSearchView_zv_srv_search_input_enable, true);
        searchHint = typedArray.getString(R.styleable.ZSearchView_zv_srv_search_hint);
        leftSearchIcon= typedArray.getResourceId(R.styleable.ZSearchView_zv_srv_left_iv_search_icon, R.drawable.zjc_ic_search);
        rightSearchIcon= typedArray.getResourceId(R.styleable.ZSearchView_zv_srv_right_iv_search_icon, R.drawable.zjc_ic_search);
        initViews();
        setViews();
        setLinsenter();
    }




    private void initViews(){
        ivLeftSearchIcon=findViewById(R.id.zjc_iv_search_icon);
        ivClear=findViewById(R.id.zjc_iv_clear);
        evInput = findViewById(R.id.zjc_ev_Input);
        tvInput = findViewById(R.id.zjc_tv_input);
        rvLeftSearch=findViewById(R.id.zjc_rv_left_search);
        rvRightSearch=findViewById(R.id.zjc_rv_search_right);
        tvRightSearch=findViewById(R.id.zjc_tv_search);
        ivRightSearChIcon=findViewById(R.id.zjc_iv_search);
    }


    private void setViews() {
        ivClear.setVisibility(GONE);
        if(isShowTvRightSearch){
            tvRightSearch.setVisibility(VISIBLE);
        }
        if(isShowIvRightSearch){
            ivRightSearChIcon.setVisibility(VISIBLE);
            ivRightSearChIcon.setImageResource(rightSearchIcon);
        }
        if(!isCanInput){
            setInputEnable(false);
        }
        ivLeftSearchIcon.setImageResource(leftSearchIcon);

        if(!TextUtils.isEmpty(searchHint)){
            tvInput.setHint(searchHint);
            evInput.setHint(searchHint);
        }else{
            tvInput.setHint("");
            evInput.setHint("");
        }
    }


    /**
     * 左侧搜索的layout
     * @return
     */
    public  RelativeLayout getLeftRvSearch(){
        return  rvLeftSearch;
    }

    /**
     * 左侧搜索的layout
     * @return
     */
    public  RelativeLayout getRightRvSearch(){
        return  rvRightSearch;
    }

    public  void   setInputEnable(boolean isInput){
        if(!isInput){
            evInput.setVisibility(GONE);
            tvInput.setVisibility(VISIBLE);
        }else{
            evInput.setVisibility(VISIBLE);
            tvInput.setVisibility(GONE);
        }

    }


    //设置输入类型
    public  void    setInputType(int  type){
        evInput.setInputType(type);
    }
    /**
     * 设置输入框文字大小
     *
     * @param size
     */
    public void setTextSize(int size) {
        evInput.setTextSize(ZDimen.sp2px(size));
        tvInput.setTextSize(ZDimen.sp2px(size));
    }

    /**
     * 设置输入提示内容
     */
    public void setHint(String hint) {
        evInput.setHint(hint);
        tvInput.setHint(hint);
    }

    public void setFocus(boolean  bl) {
        evInput.setFocusable(bl);
        evInput.setFocusableInTouchMode(bl);
        evInput.requestFocus();
        Activity activity= (Activity) getContext();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }



    /**
     * 右侧是否要显示清空按钮
     *
     * @param isShow
     */
    public void setClearViewShow(boolean isShow) {
        this.isShowClear = isShow;
    }

    /**
     * 输入框监听
     * @param inputListener
     */
    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }


    public  void clearInput(){
        evInput.setText("");
    }

    /**
     * 输入框文本监听
     */
    private void setLinsenter() {
        ivClear.setOnClickListener(v -> evInput.setText(""));
        evInput.setOnEditorActionListener((v, actionId, event) -> {
            if (inputListener != null) {
                hideSoftKey();
                return inputListener.onDoneAction();
            }
            return false;
        });
        //根据输入框输入值的改变来过滤搜索
        evInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivClear.setVisibility(GONE);
                } else if (isShowClear && ivClear.getVisibility() == View.GONE) {
                    ivClear.setVisibility(VISIBLE);
                }
                if (inputListener != null) {
                    inputListener.onTextChange(s.toString());
                }
            }
        });
    }



    private  void hideSoftKey(){
        Activity activity= (Activity) getContext();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(evInput.getWindowToken(), 0); //强制隐藏键盘
    }

    public interface InputListener {
        void onTextChange(String text);

        boolean onDoneAction();
    }
}
