package com.hencoder.hencoderpracticedraw7.practice.practice02;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice02HsvEvaluatorLayout extends RelativeLayout {
    Practice02HsvEvaluatorView view;
    Button animateBt;

    public Practice02HsvEvaluatorLayout(Context context) {
        super(context);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice02HsvEvaluatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
                // 使用自定义的 HsvEvaluator
                animator.setEvaluator(new HsvEvaluator());
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {

        private float[] startHsvValues = new float[3];

        private float[] endHsvValues = new float[3];

        private float[] curHsvValues = new float[3];

        /**
         * 重写 evaluate() 方法，让颜色按照 HSV 来变化
         *
         * @return
         */
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            Color.colorToHSV(startValue, startHsvValues);
            Color.colorToHSV(endValue, endHsvValues);
            curHsvValues[0] = startHsvValues[0] + (endHsvValues[0] - startHsvValues[0]) * fraction;
            curHsvValues[1] = startHsvValues[1] + (endHsvValues[1] - startHsvValues[1]) * fraction;
            curHsvValues[2] = startHsvValues[2] + (endHsvValues[2] - startHsvValues[2]) * fraction;
            return Color.HSVToColor(curHsvValues);
        }
    }
}
