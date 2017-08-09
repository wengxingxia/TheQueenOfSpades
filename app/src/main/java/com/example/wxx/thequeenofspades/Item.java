package com.example.wxx.thequeenofspades;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;

/**
 * 游戏面板格子
 *
 * @author wengxingxia
 * @time 2017/8/7 0007 11:19
 */
public class Item extends android.support.v7.widget.AppCompatTextView {

    private int mNum;//当前数字
    private int mBackgroundColor;//当前格子背景
    private Point mPoint;//记录当前Item第几行第几列

    private GridLayout.LayoutParams mLP;

    public Item(Context context) {
        this(context, null);
    }

    public Item(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLP = new GridLayout.LayoutParams();
        setGravity(Gravity.CENTER);//文字居中显示
        setLayoutParams(mLP);//设置属性
    }

    /**
     * 设置数字
     *
     * @param num
     */
    public void setNum(int num) {
        mNum = num;
        switch (mNum) {
            case 0:
                mBackgroundColor = R.color.bg_0;
                break;
            case 2:
                mBackgroundColor = R.color.bg_2;
                break;
            case 4:
                mBackgroundColor = R.color.bg_4;
                break;
            case 8:
                mBackgroundColor = R.color.bg_8;
                break;
            case 16:
                mBackgroundColor = R.color.bg_16;
                break;
            case 32:
                mBackgroundColor = R.color.bg_32;
                break;
            case 64:
                mBackgroundColor = R.color.bg_64;
                break;
            case 128:
                mBackgroundColor = R.color.bg_128;
                break;
            case 256:
                mBackgroundColor = R.color.bg_256;
                break;
            case 512:
                mBackgroundColor = R.color.bg_512;
                break;
            case 1024:
                mBackgroundColor = R.color.bg_1024;
                break;
            case 2048:
                mBackgroundColor = R.color.bg_2048;
                break;
            case 4096:
                mBackgroundColor = R.color.bg_4096;
                break;
            case 8192:
                mBackgroundColor = R.color.bg_8192;
                break;
            case 16384:
                mBackgroundColor = R.color.bg_16384;
                break;
            default:
                mBackgroundColor = R.color.bg_default;
                break;
        }
        if (mNum != 0) {
            setText(String.valueOf(mNum));
        } else
            setText("");
        setBackgroundResource(mBackgroundColor);
    }

    public Point getPoint() {
        return mPoint;
    }

    /**
     * 设置点的行和列
     * @param row
     * @param column
     */
    public void setPoint(int row, int column) {
        if (mPoint == null)
            mPoint = new Point();
        mPoint.x = column;
        mPoint.y = row;
//
        mLP.columnSpec = GridLayout.spec(column);
        mLP.rowSpec = GridLayout.spec(row);
    }

    /**
     * 设置大小
     * @param size 宽度
     * @param divider 边线距离
     * @param count 列数和行数
     */
    public void setSize(int size,int divider,int count){
        mLP.width = size;
        mLP.height = size;

        int halfDivider = (int) Math.ceil(divider/2f);

        //设置边距
        if(mPoint.x == 0){
            mLP.leftMargin = divider;
            mLP.rightMargin = halfDivider;
        }else if(mPoint.x ==count-1){
            mLP.rightMargin = divider;
            mLP.leftMargin = halfDivider;
        }else {
            mLP.leftMargin = halfDivider;
            mLP.rightMargin = halfDivider;
        }

        if(mPoint.y == 0){
            mLP.topMargin = divider;
            mLP.bottomMargin = halfDivider;
        }else if(mPoint.y == count-1 ){
            mLP.bottomMargin = divider;
            mLP.topMargin = halfDivider;
        }else {
            mLP.topMargin = halfDivider;
            mLP.bottomMargin = halfDivider;
        }
//        设置文字大小，单位是px,大小是size的3分之1
        setTextSize(TypedValue.COMPLEX_UNIT_PX,size/3);
    }

    /**
     * 获取数字
     * @return
     */
    public int getNum(){
        return mNum;
    }
}
