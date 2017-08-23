package com.example.wxx.thequeenofspades.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wxx.thequeenofspades.R;
import com.example.wxx.thequeenofspades.UIUtils;
import com.example.wxx.thequeenofspades.dialog.AlertDialog;
import com.hanks.htextview.scale.ScaleTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.etAnswer)
    EditText mEtAnswer;
    @BindView(R.id.scaleTv1)
    ScaleTextView mScaleTv1;
    @BindView(R.id.scaleTv2)
    ScaleTextView mScaleTv2;
    @BindView(R.id.scaleTv3)
    ScaleTextView mScaleTv3;
    @BindView(R.id.scaleTv4)
    ScaleTextView mScaleTv4;
    @BindView(R.id.tvOk)
    TextView mTvOk;
    private Disposable mSubscribe;
    private AlertDialog mAnswerDialog;

    private ImageView mIvIcon;
    private TextView mTvTips;

    private boolean isRight;//true表示回答正确
    private long mExitTime;//双击退出程序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
//        mScaleTv.setText();
        mScaleTv1.setProgress(0);
        mScaleTv2.setProgress(0);
        mScaleTv3.setProgress(0);
        mScaleTv4.setProgress(0);

        mSubscribe = Observable.interval(0, 1, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                if (aLong > 2000) {
                    if (!mSubscribe.isDisposed()) {
                        mSubscribe.dispose();
                    }
                } else {
                    mScaleTv1.setProgress(aLong / 2000f);
                    mScaleTv2.setProgress(aLong / 2000f);
                    mScaleTv3.setProgress(aLong / 2000f);
                    mScaleTv4.setProgress(aLong / 2000f);
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

    @OnClick(R.id.tvOk)
    public void onViewClicked() {
        isRight = "我爱你".equals(mEtAnswer.getText().toString().trim());
        showAnswerDialog();
    }

    /**
     * 显示回答对话框
     */
    private void showAnswerDialog() {
        if (mAnswerDialog == null) {
            mAnswerDialog = new AlertDialog.Builder(this).setContentView(R.layout.dialog_answer).addDefaultAnimation().create();
            mIvIcon = mAnswerDialog.findViewById(R.id.ivIcon);
            mTvTips = mAnswerDialog.findViewById(R.id.tvTips);
            mAnswerDialog.setOnClickListener(R.id.tvOk, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAnswerDialog.dismiss();
                    if (isRight) {
                        Intent intent = new Intent(QuestionActivity.this, GameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        if (isRight) {
            Glide.with(this).load(R.mipmap.love).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop()
//                    .transformFrame(new CropCircleTransformation(this))
                    .transformFrame(new RoundedCornersTransformation(this, UIUtils.dp2px(10), 0))
                    .into(mIvIcon);
            mTvTips.setText("当然啦，我也爱你！\n乖，生日快乐!");
            mAnswerDialog.setText(R.id.tvOk,"游戏赢奖励");
        }else{
            Glide.with(this).load(R.mipmap.beat).asGif().centerCrop()
//                    .transformFrame(new CropCircleTransformation(this))
                    .transformFrame(new RoundedCornersTransformation(this, UIUtils.dp2px(10), 0))
                    .into(mIvIcon);
            mTvTips.setText("傻猪，笨猪，傻不拉几猪，回答错误");
            mAnswerDialog.setText(R.id.tvOk,"重新回答");
        }

        mAnswerDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(getApplicationContext(), "再按一次返回键退出程序",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
