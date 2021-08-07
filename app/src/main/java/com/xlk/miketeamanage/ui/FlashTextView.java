package com.xlk.miketeamanage.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Created by xlk on 2021/8/6.
 * @desc
 */
public class FlashTextView extends AppCompatTextView {
    private Paint mPaint;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTransalte;

    public FlashTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();//获得当前绘制的Paint对象
                mLinearGradient = new LinearGradient(
                        0,//渐变起始点x坐标
                        0,//渐变起始点y坐标
                        mViewWidth,//渐变结束点x点坐标
                        0,//渐变结束点y坐标
                        new int[]{
                                Color.BLUE, 0xffffffff,
                                Color.BLUE, Color.RED, Color.YELLOW},//颜色的int数组
                        null,//相对位置的颜色数组,可为null, 若为null,可为null,颜色沿渐变线均匀分布
                        Shader.TileMode.MIRROR);//平铺模式
                mPaint.setShader(mLinearGradient);//给这个paint设置linearFradient属性
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mGradientMatrix != null) {
            mTransalte += mViewWidth / 5;
            if (mTransalte > 2 * mViewWidth) {
                mTransalte -= mViewWidth;
            }
            mGradientMatrix.setTranslate(mTransalte, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);//通过矩阵的方式不断平移产生渐变效果
            postInvalidateDelayed(100);

        }

    }
}
