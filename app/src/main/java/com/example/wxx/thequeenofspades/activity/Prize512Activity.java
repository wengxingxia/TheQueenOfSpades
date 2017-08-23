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

public class Prize512Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_prize512);
        ButterKnife.bind(this);

        mTv.setText(
                "    还记得这辆单车吗，我的专属车模，那时候，每天载着你，来回于考研室和宿舍之间，很开心，从不觉得累，很怀念你在我背后偷偷咬我。");
        mTv.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvShow)
    public void onViewClicked() {
        mTv.setVisibility(View.VISIBLE);
        mTvShow.setVisibility(View.GONE);

    }
}
