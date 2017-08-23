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

public class Prize4096Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_prize4096);
        ButterKnife.bind(this);

        mTv.setText("    我嘴笨，情商低，做事拖拉，脑子经常短路，还健忘，很感激你，这三年多以来的包容。" +
                "我们经常吵架，到时始终没有变化的就是，发起点都是我，都是我笨手笨脚，脑袋迟钝，" +
                "我也在一直努力，努力改变，让自己更加符合你的标准，虽然到现在还是没有显著的改变，但是，我会keep going，我们会越来越好");
        mTv.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvShow)
    public void onViewClicked() {
        mTv.setVisibility(View.VISIBLE);
        mTvShow.setVisibility(View.GONE);

    }
}
