package com.android.zjctools.utils.widgetUtils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 金额/ 后面两位 小数
 */
public class ZDecimalInputFilter implements InputFilter {

    private String mRegularExpression;

    public ZDecimalInputFilter() {
        this(5);
    }

    public ZDecimalInputFilter(int firstLength) {
        this(firstLength, 2);
    }

    public ZDecimalInputFilter(int firstLength, int lastLength) {
        mRegularExpression = String.format("(\\d{0,%d}(\\.\\d{0,%d})?)", firstLength, lastLength);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        boolean delete = false;
        StringBuilder builder = new StringBuilder(dest);

        if (TextUtils.isEmpty(source)) {
            delete = true;
            builder.delete(dstart, dend);
        } else {
            builder.insert(dstart, source);
        }

        String value = builder.toString();

        return value.matches(mRegularExpression) ? null : delete ? "." : "";
    }
}

