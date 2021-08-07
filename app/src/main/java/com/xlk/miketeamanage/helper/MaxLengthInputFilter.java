package com.xlk.miketeamanage.helper;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.view.PasswordActivity;

import es.dmoral.toasty.Toasty;

/**
 * @author Created by xlk on 2021/8/6.
 * @desc EditText限制输入字数限制功能类
 * 使用示例： xxx.setFilters(new InputFilter[]{new MaxLengthFilter(100)});
 */
public class MaxLengthInputFilter implements InputFilter {

    private final int mMax;//限制字数
    private final Context mContext;

    public MaxLengthInputFilter(Context context, int max) {
        mContext = context;
        mMax = max;
    }

    /**
     * @param source 输入的源字符
     * @param start  输入的起始位置
     * @param end    输入的结束位置
     * @param dest   当前输入框中的内容 已经存在的
     * @param dstart 输入框中的内容被替换的起始位置
     * @param dend   输入框中的内容被替换的结束位置
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.length() <= 0) return null;//一般是删除
        if (source.length() + dest.length() > mMax) {
            Toasty.warning(mContext, mContext.getString(R.string.err_max_length, String.valueOf(mMax)), Toast.LENGTH_SHORT, true).show();
            //可输入的字数
            int canAddLength = mMax - dest.length();
            //可输入的文本
            String substring = source.toString().substring(0, canAddLength);
            return substring;
        }
        return null;
    }
}
