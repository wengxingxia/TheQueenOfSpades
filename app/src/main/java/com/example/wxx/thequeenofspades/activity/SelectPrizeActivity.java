package com.example.wxx.thequeenofspades.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.wxx.thequeenofspades.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPrizeActivity extends AppCompatActivity {

    @BindView(R.id.tv64)
    TextView mTv64;
    @BindView(R.id.tv128)
    TextView mTv128;
    @BindView(R.id.tv256)
    TextView mTv256;
    @BindView(R.id.tv512)
    TextView mTv512;
    @BindView(R.id.tv1024)
    TextView mTv1024;
    @BindView(R.id.tv2048)
    TextView mTv2048;
    @BindView(R.id.tv4096)
    TextView mTv4096;
    @BindView(R.id.tv8192)
    TextView mTv8192;
    @BindView(R.id.tv16384)
    TextView mTv16384;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_prize);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv64, R.id.tv128, R.id.tv256, R.id.tv512, R.id.tv1024, R.id.tv2048, R.id.tv4096, R.id.tv8192, R.id.tv16384})
    public void onViewClicked(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.tv64:
                clazz = Prize64Activity.class;
                break;
            case R.id.tv128:
                clazz = Prize128Activity.class;
                break;
            case R.id.tv256:
                clazz = Prize256Activity.class;
                break;
            case R.id.tv512:
                clazz = Prize512Activity.class;
                break;
            case R.id.tv1024:
                clazz = Prize1024Activity.class;
                break;
            case R.id.tv2048:
                clazz = Prize2048Activity.class;
                break;
            case R.id.tv4096:
                clazz = Prize4096Activity.class;
                break;
            case R.id.tv8192:
                clazz = Prize8192Activity.class;
                break;
            case R.id.tv16384:
                clazz = Prize16384Activity.class;
                break;
        }
        Intent intent = new Intent(SelectPrizeActivity.this, clazz);
        startActivity(intent);
        finish();
    }
}
