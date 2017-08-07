package com.example.wxx.thequeenofspades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvCurrentMax)
    TextView mTvCurrentMax;
    @BindView(R.id.tvHistoryMax)
    TextView mTvHistoryMax;
    @BindView(R.id.btnRestart)
    Button mBtnRestart;
    @BindView(R.id.btnSelectColumn)
    Button mBtnSelectColumn;
    @BindView(R.id.glBroad)
    GridLayout mGlBroad;
    private int mColumnCount = 4;
    private Item[][] mItems;
    private int mItemWidth;
    private int mDivider ;
    private int mMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initGame();
    }

//    初始化宽度等数据
    private void init() {
        mDivider = getResources().getDimensionPixelSize(R.dimen.divider);//分割线宽度
        mMargin = getResources().getDimensionPixelOffset(R.dimen.margin);//边距
        int screenWidth = getResources().getDisplayMetrics().widthPixels;//屏幕宽度
        mItemWidth = (screenWidth-2*mMargin-(mColumnCount+1)*mDivider)/mColumnCount;
    }

    //    初始化游戏面板
    private void initGame() {
        mGlBroad.setColumnCount(mColumnCount);
        mItems = new Item[mColumnCount][mColumnCount];
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                mItems[i][j] = new Item(this);
                mItems[i][j].setNum(0);
                mItems[i][j].setPoint(i, j);
                mItems[i][j].setSize(mItemWidth,mDivider,mColumnCount);
                mGlBroad.addView(mItems[i][j]);
            }
        }
    }
}
