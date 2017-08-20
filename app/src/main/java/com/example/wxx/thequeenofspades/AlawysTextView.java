package com.example.wxx.thequeenofspades;

import android.content.Context;
import android.util.AttributeSet;

import com.hanks.htextview.rainbow.RainbowTextView;

/**
 * Created by Administrator on 2015/12/20.
 */
public class AlawysTextView extends RainbowTextView {

    public AlawysTextView(Context context) {
        super(context);
    }

    public AlawysTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlawysTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
