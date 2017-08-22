package com.example.wxx.thequeenofspades.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wxx.thequeenofspades.AlawysTextView;
import com.example.wxx.thequeenofspades.Item;
import com.example.wxx.thequeenofspades.R;
import com.example.wxx.thequeenofspades.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

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
    @BindView(R.id.tv)
    AlawysTextView mTv;
    @BindView(R.id.llBtn)
    LinearLayout mLlBtn;
    @BindView(R.id.ivSound)
    ImageView mIvSound;
    @BindView(R.id.tvRule)
    TextView mTvRule;
    private int mColumnCount = 4;
    private Item[][] mItems;
    private int mItemWidth;
    private int mDivider;
    private int mMargin;

    private List<Point> mPoints;

    private float mDownX;
    private float mDownY;

    private int mLastNum = -1;//储存上一个数
    private List<Integer> mNums;
    private int mMaxScore;//当前最高分数

    public static final String LEVEL_4 = "LEVEL4";
    public static final String LEVEL_5 = "LEVEL5";
    public static final String LEVEL_6 = "LEVEL6";

    private String mLevel = LEVEL_4;

    private Animation mAnimationScale;//动画

    private MediaPlayer mMediaPlayerMerge;
    private MediaPlayer mMediaPlayerMove;
    private MediaPlayer mMediaPlayerGameOver;

    private boolean isCloseSounds;//true表示关闭音乐

    private boolean isMerge;//true表示发生合并

    private int mSoundsId;
    private long mExitTime;//双击退出程序
    private AlertDialog mRuleDialog;

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
        mNums = new ArrayList<>();
        mPoints = new ArrayList<>();
        mDivider = getResources().getDimensionPixelSize(R.dimen.divider);//分割线宽度
        mMargin = getResources().getDimensionPixelOffset(R.dimen.margin);//边距
        mAnimationScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale_add_num);

    }

    //    初始化游戏面板
    private void initGame() {
        mGlBroad.removeAllViews();
        mItems = new Item[mColumnCount][mColumnCount];
        mTvHistoryMax.setText("历史最高：" + getMaxScore());

        int screenWidth = getResources().getDisplayMetrics().widthPixels;//屏幕宽度
        mItemWidth = (screenWidth - 2 * mMargin - (mColumnCount + 1) * mDivider) / mColumnCount;

        mGlBroad.setColumnCount(mColumnCount);
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
        int x = point.x;
        int y = point.y;
        mItems[y][x].setNum(num);//给指定的空白项设置随机数
        mItems[y][x].startAnimation(mAnimationScale);
        mPoints.remove(index);//从空白项集合中移除该项
    }

    /**
     * 随机添加数字
     *
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_UP:
                float dx = x - mDownX;//按下和抬起的水平移动距离
                float dy = y - mDownY;//按下和抬起的竖直移动距离
                if (Math.abs(dx) > 50 || Math.abs(dy) > 50) {//如果移动距离大于50时才移动
                    if (canMove()) {
                        char moveOrientation = getMoveOrientation(dx, dy);
                        if (move(moveOrientation)) {
                            addNum(1);
                            mTvCurrentMax.setText(String.format("当前最高：%d", mMaxScore));
                            if (isMerge) {
                                mSoundsId = R.raw.merge;
                            } else
                                mSoundsId = R.raw.move;
                        } else {
//                            游戏结束
                            gameOver();
                            mSoundsId = R.raw.gameover;
                        }
                        play();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 游戏结束
     */
    private void gameOver() {
        String msg = "";
        if (mMaxScore < 254) {
            msg = "你真的弱爆了！";
        } else if (mMaxScore < 512) {
            msg = "太弱了！";
        } else if (mMaxScore < 1024) {
            msg = "还凑合，继续加油吧";
        } else if (mMaxScore < 2048) {
            msg = "不错嘛，可以小炫耀下，不过小心被鄙视！";
        } else if (mMaxScore < 4096) {
            msg = "好崇拜啊！";
        } else if (mMaxScore < 8192) {
            msg = "你是我的偶像！";
        } else {
            msg = "你已超神！";
        }
//        new AlertDialog.Builder(this).setMessage(msg).setPositiveButton("从新开始", mOnClickListener)
//                .setNegativeButton("退出", mOnClickListener).show().setCancelable(false);
        saveScore();//保存分数
    }

    /**
     * 对话框点击监听
     */
    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            int column = 0;
            String level = "";
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    restart();//重新开始
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    saveScore();
                    finish();
                    break;
                case 0:
                    column = 4;
                    level = LEVEL_4;
                    break;
                case 1:
                    column = 5;
                    level = LEVEL_5;
                    break;
                case 2:
                    column = 6;
                    level = LEVEL_6;
                    break;
            }
            if (column > 0 && column != mColumnCount) {
                mColumnCount = column;
                mLevel = level;
                initGame();
            }
        }
    };

    /**
     * 获取移动的方向
     *
     * @param x
     * @param y
     * @return
     */
    private char getMoveOrientation(float x, float y) {
        if (Math.abs(x) > Math.abs(y)) {//水平移动
            if (x > 0) {//右移
                return 'r';
            }
            return 'l';//左移
        } else {
            if (y > 0)
                return 'b';//下移
            return 't';//上移
        }
    }

    /**
     * 判断是否能移动，true表示能移动
     *
     * @return
     */
    private boolean canMove() {
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                int num = mItems[i][j].getNum();
                if (num == 0) {
                    return true;
                }
                if (i - 1 >= 0 && num == mItems[i - i][j].getNum())
                    return true;
                if (i + 1 < mColumnCount && num == mItems[i + 1][j].getNum()) return true;
                if (j - 1 >= 0 && num == mItems[i][j - 1].getNum())
                    return true;
                if (j + 1 < mColumnCount && num == mItems[i][j + 1].getNum()) return true;
            }
        }
        return false;
    }

    /**
     * 移动
     *
     * @param moveOrientation
     * @return
     */
    private boolean move(char moveOrientation) {
        isMerge = false;
        switch (moveOrientation) {
            case 'l':
                return moveToLeft();
            case 't':
                return moveToTop();
            case 'r':
                return moveToRight();
            case 'b':
                return moveToBottom();
        }
        return false;
    }

    /**
     * 向上移动
     *
     * @return
     */
    private boolean moveToTop() {
        boolean f = false;

        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                int num = mItems[j][i].getNum();
                if (num != 0) {
                    if (mLastNum == -1) {
                        mLastNum = num;
                    } else {
                        if (mLastNum == num) {
                            isMerge = true;
                            f = true;
                            mNums.add(num * 2);
                            mLastNum = -1;
                        } else {
                            mNums.add(mLastNum);
                            mLastNum = num;
                        }
                    }
                } else {
                    f = true;
                }
            }
            if (mLastNum != -1) {
                mNums.add(mLastNum);
            }
            for (int j = 0; j < mColumnCount; j++) {
                if (j < mNums.size()) {
                    int num = mNums.get(j);
                    if (mMaxScore < num)
                        mMaxScore = num;
                    mItems[j][i].setNum(num);
                } else
                    mItems[j][i].setNum(0);
            }

            mLastNum = -1;
            mNums.clear();
        }
        return f;
    }

    /**
     * 向左移动
     *
     * @return
     */
    private boolean moveToLeft() {
        boolean f = false;

        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                int num = mItems[i][j].getNum();
                if (num != 0) {
                    if (mLastNum == -1) {
                        mLastNum = num;
                    } else {
                        if (mLastNum == num) {
                            isMerge = true;
                            f = true;
                            mNums.add(num * 2);
                            mLastNum = -1;
                        } else {
                            mNums.add(mLastNum);
                            mLastNum = num;
                        }
                    }
                } else {
                    f = true;
                }
            }
            if (mLastNum != -1) {
                mNums.add(mLastNum);
            }
            for (int j = 0; j < mColumnCount; j++) {
                if (j < mNums.size()) {
                    int num = mNums.get(j);
                    if (mMaxScore < num)
                        mMaxScore = num;
                    mItems[i][j].setNum(num);
                } else
                    mItems[i][j].setNum(0);
            }

            mLastNum = -1;
            mNums.clear();
        }
        return f;
    }

    /**
     * 向右移动
     *
     * @return
     */
    private boolean moveToRight() {
        boolean f = false;
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = mColumnCount - 1; j >= 0; j--) {
                int num = mItems[i][j].getNum();
                if (num != 0) {
                    if (mLastNum == -1) {
                        mLastNum = num;
                    } else {
                        if (mLastNum == num) {
                            isMerge = true;
                            f = true;
                            mNums.add(num * 2);
                            mLastNum = -1;
                        } else {
                            mNums.add(mLastNum);
                            mLastNum = num;
                        }
                    }
                } else {

                    f = true;
                }
            }
            if (mLastNum != -1) {
                mNums.add(mLastNum);
            }
            for (int j = 0; j < mColumnCount; j++) {
                if (mColumnCount - 1 - j < mNums.size()) {
                    int num = mNums.get(mColumnCount - 1 - j);
                    if (mMaxScore < num)
                        mMaxScore = num;
                    mItems[i][j].setNum(num);
                } else
                    mItems[i][j].setNum(0);
            }

            mLastNum = -1;
            mNums.clear();
        }
        return f;
    }

    /**
     * 向下移动
     *
     * @return
     */
    private boolean moveToBottom() {
        boolean f = false;
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = mColumnCount - 1; j >= 0; j--) {
                int num = mItems[j][i].getNum();
                if (num != 0) {
                    if (mLastNum == -1) {
                        mLastNum = num;
                    } else {
                        if (mLastNum == num) {
                            isMerge = true;
                            f = true;
                            mNums.add(num * 2);
                            mLastNum = -1;
                        } else {
                            mNums.add(mLastNum);
                            mLastNum = num;
                        }
                    }
                } else {

                    f = true;
                }
            }
            if (mLastNum != -1) {
                mNums.add(mLastNum);
            }
            for (int j = 0; j < mColumnCount; j++) {
                if (mColumnCount - 1 - j < mNums.size()) {
                    int num = mNums.get(mColumnCount - 1 - j);
                    if (mMaxScore < num)
                        mMaxScore = num;
                    mItems[j][i].setNum(num);
                } else
                    mItems[j][i].setNum(0);
            }

            mLastNum = -1;
            mNums.clear();
        }
        return f;
    }

    /**
     * 保存分数
     */
    private void saveScore() {
        int historyMax = getMaxScore();
        if (historyMax < mMaxScore) {
            SharedPreferences.Editor e = getSharedPreferences("level", MODE_PRIVATE).edit();
            e.putInt(mLevel, mMaxScore);
            e.commit();
        };
        mMaxScore = 0;
    }

    /**
     * 获取最大分数
     *
     * @return
     */
    private int getMaxScore() {
        int level = getSharedPreferences("level", MODE_PRIVATE).getInt(mLevel, 0);
        return level;
    }

    /**
     * 重新开始
     */
    private void restart() {
        saveScore();//保存分数
        mTvHistoryMax.setText("历史最高：" + getMaxScore());
        mGlBroad.setColumnCount(mColumnCount);
        for (int i = 0; i < mColumnCount; i++) {
            for (int j = 0; j < mColumnCount; j++) {
                mItems[i][j].setNum(0);
            }
        }
        addNum(2);//随机添加数字
    }

    /**
     * 选择列数
     */
    private void selectColumn() {
        new android.support.v7.app.AlertDialog.Builder(this).setItems(new String[]{"4x4", "5x5", "6x6"}, mOnClickListener).show().setCancelable(false);
        saveScore();//保存分数
    }

    @OnClick({R.id.btnRestart, R.id.btnSelectColumn, R.id.ivSound, R.id.tvRule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRestart:
                restart();//重新开始
                break;
            case R.id.btnSelectColumn:
                selectColumn();//选择列数
                break;
            case R.id.ivSound:
                if (isCloseSounds) {
                    isCloseSounds = false;
                    Glide.with(this).load(R.mipmap.sound_on).centerCrop().into(mIvSound);
                } else {
                    isCloseSounds = true;
                    Glide.with(this).load(R.mipmap.sound_off).centerCrop().into(mIvSound);
                }
                break;
            case R.id.tvRule:
                showRuleDialog();
                break;
        }
    }


    /**
     * 播放音乐
     */
    private void play() {
        try {
            if (isCloseSounds)
                return;
            switch (mSoundsId) {
                case R.raw.merge:
                    if (mMediaPlayerMerge == null) {
                        mMediaPlayerMerge = MediaPlayer.create(this, mSoundsId);
                    }
                    mMediaPlayerMerge.start();
                    break;
                case R.raw.move:
                    if (mMediaPlayerMove == null) {
                        mMediaPlayerMove = MediaPlayer.create(this, mSoundsId);
                    }
                    mMediaPlayerMove.start();
                    break;
                case R.raw.gameover:
                    if (mMediaPlayerGameOver == null) {
                        mMediaPlayerGameOver = MediaPlayer.create(this, mSoundsId);
                    }
                    mMediaPlayerGameOver.start();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayerMerge != null) {
            mMediaPlayerMerge.release();
            mMediaPlayerMerge = null;
        }
        if (mMediaPlayerMove != null) {
            mMediaPlayerMove.release();
            mMediaPlayerMove = null;
        }
        if (mMediaPlayerGameOver != null) {
            mMediaPlayerGameOver.release();
            mMediaPlayerGameOver = null;
        }
        super.onDestroy();
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

    /**
     * 显示回答对话框
     */
    private void showRuleDialog() {
        if (mRuleDialog == null) {
            mRuleDialog = new AlertDialog.Builder(this).setContentView(R.layout.dialog_rule).addDefaultAnimation().create();
            mRuleDialog.setOnClickListener(R.id.tvOk, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRuleDialog.dismiss();
                }
            });
        }
        mRuleDialog.show();
    }

}
