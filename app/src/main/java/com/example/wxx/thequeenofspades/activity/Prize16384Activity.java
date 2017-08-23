package com.example.wxx.thequeenofspades.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.wxx.thequeenofspades.R;
import com.hanks.htextview.fade.FadeTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Prize16384Activity extends AppCompatActivity {

    @BindView(R.id.tvFade1)
    FadeTextView mTvFade1;
    @BindView(R.id.tvFade2)
    FadeTextView mTvFade2;
    @BindView(R.id.tvFade3)
    FadeTextView mTvFade3;
    @BindView(R.id.tvFade4)
    FadeTextView mTvFade4;
    private Disposable mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prize16384);
        ButterKnife.bind(this);
        mSubscribe = Observable.interval(0, 1, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                if (aLong > 3000) {
                    if (!mSubscribe.isDisposed()) {
                        mSubscribe.dispose();
                    }
                } else {
                    mTvFade1.setProgress(aLong / 3000f);
                    mTvFade2.setProgress(aLong / 3000f);
                    mTvFade3.setProgress(aLong / 3000f);
                    mTvFade4.setProgress(aLong / 3000f);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
