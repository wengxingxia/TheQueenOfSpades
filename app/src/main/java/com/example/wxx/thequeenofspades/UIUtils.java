package com.example.wxx.thequeenofspades;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;


/**
 * UI工具类
 * 作者：wengxingxia
 * 时间：2017/4/19 0019 22:29
 */

public class UIUtils {

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取资源文件中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * 通过布局文件id，加载布局文件的view
     *
     * @param layoutId
     * @return
     */
    public static View getXmlView(int layoutId) {
        try {
            return View.inflate(getContext(), layoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过布局文件id，加载布局文件的view
     *
     * @param layoutId
     * @return
     */
    public static View getXmlView(Context context, int layoutId) {
        try {
            return View.inflate(context, layoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单位转换 dp->px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);

    }

    /**
     * 单位转换 px->dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);

    }

    /**
     * 获取String.xml中的String数组
     *
     * @param arrId
     * @return
     */
    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    public static Context getContext() {
        Context context = BaseApplication.getContext();
        return context;
    }

    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

}
