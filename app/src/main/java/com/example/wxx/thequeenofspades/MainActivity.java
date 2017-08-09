package com.example.wxx.thequeenofspades;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    private int mDivider;
    private int mMargin;

    private List<Point> mPoints;

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
        mPoints = new ArrayList<>();
        mDivider = getResources().getDimensionPixelSize(R.dimen.divider);//分割线宽度
        mMargin = getResources().getDimensionPixelOffset(R.dimen.margin);//边距
        int screenWidth = getResources().getDisplayMetrics().widthPixels;//屏幕宽度
        mItemWidth = (screenWidth - 2 * mMargin - (mColumnCount + 1) * mDivider) / mColumnCount;
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
                mItems[i][j].setSize(mItemWidth, mDivider, mColumnCount);
                mGlBroad.addView(mItems[i][j]);
            }
        }
        addNum(2);//随机添加数字
    }

    /**
     * 检查为空的项
     */
    private void checkNull() {
        mPoints.clear();
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                int num = mItems[i][j].getNum();
                if (num == 0) {
                    mPoints.add(mItems[i][j].getPoint());
                }
            }
        }
    }

    /**
     * 创建一个随机数
     */
    private void createNum() {
        int index = (int) (Math.random() * mPoints.size());//随机得到空白项的下标
        Point point = mPoints.get(index);
        int num = Math.random() > 0.1 ? 2 : 4;//产生随机数 ， 如果大于0.1 为2 否则是 4
        mItems[point.x][point.y].setNum(num);//给指定的空白项设置随机数
        mPoints.remove(index);//从空白项集合中移除该项
    }

    /**
     * 随机添加数字
     * @param count 随机添加的个数
     */
    private void addNum(int count) {
        checkNull();//检查所有空点

        for (int i = 0; i < count; i++) {
            if (mPoints.size() > 0) {
                createNum();//添加随机数
            }
        }
    }
}
