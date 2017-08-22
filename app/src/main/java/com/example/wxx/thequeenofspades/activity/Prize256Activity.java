package com.example.wxx.thequeenofspades.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chaychan.viewlib.expandabletextview.ExpandableTextView;
import com.example.wxx.thequeenofspades.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Prize256Activity extends AppCompatActivity {

    @BindView(R.id.tv)
    ExpandableTextView mTv;
    @BindView(R.id.tvShow)
    TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prize256);
        ButterKnife.bind(this);

        mTv.setText("古今成功之士，大凡都有宽阔的胸襟。一代名臣左宗棠，自小擅长棋艺，" +
                "一次出征前看到街边有一老人，以“天下第一棋手”自称，便上前挑战，" +
                "结果老人连输几盘，于是叫老人把招牌给砸了。当左宗棠出征胜利归来，" +
                "老人招牌还在，不禁大怒又上前挑战，结果被老人打得落花流水，宗棠疑惑，" +
                "问老人原因。老人说，我知你是大将军，将要出征，我不想有损你征战的信心。" +
                "宗棠大悟，谢了老人，感慨万分。老人有如此胸襟，自己却因一点小事大放狂言，" +
                "自愧不如。从此不再高傲自大，而是以宽阔的胸怀对待周边的人和事，" +
                "赢得了人们的赞许。");
        mTv.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvShow)
    public void onViewClicked() {
        mTv.setVisibility(View.VISIBLE);
        mTvShow.setVisibility(View.GONE);

    }
}
