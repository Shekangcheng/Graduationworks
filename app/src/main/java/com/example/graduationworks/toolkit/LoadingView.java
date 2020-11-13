package com.example.graduationworks.toolkit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.graduationworks.R;

public class LoadingView extends AppCompatImageView {

    //旋转的角度
    private int rotateDegree = 0;
    //
    private boolean mNeedRotate = false;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置图片
        setImageResource(R.mipmap.ic_loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //
        mNeedRotate = true;
        //绑定到Window的时候
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 30;
                //
                rotateDegree = rotateDegree <= 360 ? rotateDegree : 0;
                //
                invalidate();
                //是否继续旋转
                if (mNeedRotate) {
                    postDelayed(this, 40);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //从window中解绑
        mNeedRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 第一个参数：旋转角度
         * 第二个参数：旋转的X轴坐标
         * 第三格参数：旋转的Y坐标
         */
        canvas.rotate(rotateDegree, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);

    }
}
