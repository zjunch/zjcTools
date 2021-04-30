package com.android.zjctools.widget.tabview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZColor;
import com.android.zjcutils.R;

public class ZTabItemView extends RelativeLayout {
    RelativeLayout mContentView;
    private int selectResId, unSelectResId;   //选中和未选中的图片资源id
    private int selectColorId, unSelectColorId;
    ImageView ivIcon;
    TextView tvTitle, tvRedPoint;

    public ZTabItemView(Context context) {
        this(context, null);
    }

    public ZTabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContentView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.z_widget_tabitemview, this);
        ivIcon = mContentView.findViewById(R.id.zjc_tab_icon);
        tvTitle = mContentView.findViewById(R.id.zjc_tab_title);
        tvRedPoint = mContentView.findViewById(R.id.zjc_tab_point);
    }

    /**
     * 绑定数据
     */
    public void setTabData(ZTabBean tabBean,int selectColorId,int unSelectColorId) {
        this.selectColorId = selectColorId;
        this.unSelectColorId = unSelectColorId;
        this.selectResId = tabBean.getSelectResId();
        this.unSelectResId = tabBean.getUnSelectResId();
        if(!TextUtils.isEmpty(tabBean.getTitle())){
            tvTitle.setText(tabBean.getTitle() );
        }
        setSelect(false);
        tvRedPoint.setVisibility(GONE);
    }

    public void setViewsSpace(int topPX, int centerPx, int bottomPx) {
        RelativeLayout.LayoutParams lp = (LayoutParams) ivIcon.getLayoutParams();
        lp.topMargin = topPX;
        ivIcon.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp1 = (LayoutParams) tvTitle.getLayoutParams();
        lp1.topMargin = centerPx;
        lp1.bottomMargin = bottomPx;
        tvTitle.setLayoutParams(lp1);
    }


    /**
     * 设置小红点左、上间距
     * @param leftPX
     * @param topPx
     */
    public void setPointSpace(int leftPX, int topPx) {
        RelativeLayout.LayoutParams lp = (LayoutParams) tvRedPoint.getLayoutParams();
        lp.topMargin = topPx;
        lp.leftMargin = leftPX;
        tvRedPoint.setLayoutParams(lp);
    }

    /**
     * 修改选中状态
     *
     * @param isSelect
     */
    public void setSelect(boolean isSelect) {
        ivIcon.setImageResource(isSelect ? selectResId : unSelectResId);
        tvTitle.setTextColor(isSelect ? ZColor.byRes(selectColorId) : ZColor.byRes(unSelectColorId));
    }


    public void setSImageSize(int wPx,int hPX) {
        RelativeLayout.LayoutParams lp = (LayoutParams) ivIcon.getLayoutParams();
        lp.width=wPx;
        lp.height=hPX;
        ivIcon.setLayoutParams(lp);
    }

    public  void setTextSize(int px){
        tvTitle.getPaint().setTextSize(px);
    }


    /**
     * 设置小红点大小
     * @param wPx
     * @param hPX
     * 此处需要注意，若tvRedPoint的px和字体px相差太小,会有文字无法居中情况
     */
    public void setPointSize( int wPx,int hPX,int textSizePx) {
        tvRedPoint.getPaint().setTextSize(textSizePx);
        if (tvRedPoint.getText() != null) {
            tvRedPoint.setText(tvRedPoint.getText().toString());
        }
        RelativeLayout.LayoutParams lp = (LayoutParams) tvRedPoint.getLayoutParams();
        lp.width=wPx;
        lp.height=hPX;
        tvRedPoint.setLayoutParams(lp);
        tvRedPoint.setVisibility(VISIBLE);

    }

    /**
     * 设置小红点个数
     */
    public void setPointCount(int counts){
        if(counts==0){
            tvRedPoint.setText("");
        }else{
            tvRedPoint.setText(String.valueOf(counts));
        }
    }

    /**
     * 设置小红点隐藏或者显示
     * @param isShow
     */
    public void setPointVisible(boolean isShow){
        tvRedPoint.setVisibility(isShow?VISIBLE:GONE);
    }




}
