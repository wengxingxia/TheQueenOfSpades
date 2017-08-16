package com.example.wxx.thequeenofspades.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wxx.thequeenofspades.R;
import com.example.wxx.thequeenofspades.UIUtils;
import com.hanks.htextview.fall.FallTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 欢迎页
 */
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.ivRoll)
    ImageView mIvRoll;
    @BindView(R.id.tvFallText)
    FallTextView mTvFallText;
    private int mScreenWidth;

    private boolean isTextAnimationRunning;
    private int mTvWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.load).asGif().into(mIvRoll);

        mScreenWidth = UIUtils.getScreenWidth();
        getTvWidth();
        mTvFallText.setProgress(0);
        startAnimationIv();
    }

    /**
     * 启动加载图片动画
     */
    private void startAnimationIv() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIvRoll, "translationX", 0, mScreenWidth);
        animator.setDuration(3500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                if(value>200&&value<mScreenWidth/2){
                    mTvFallText.setProgress(value/(mScreenWidth/2));
                }else if(value>mScreenWidth/2){
                    if(!isTextAnimationRunning)
                    startAnimationTv();
                }

            }
        });
        animator.start();
    }

    /**
     * 启动文字动画
     */
    private void startAnimationTv() {
        isTextAnimationRunning = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvFallText, "translationX", 0, (mScreenWidth-mTvWidth)/2);
        animator.setDuration(2000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(WelcomeActivity.this,GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
        animator.start();
    }

    public void getTvWidth() {
        ViewTreeObserver vto2 = mTvFallText.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvFallText.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mTvWidth = mTvFallText.getWidth();
            }
        });
    }
}
