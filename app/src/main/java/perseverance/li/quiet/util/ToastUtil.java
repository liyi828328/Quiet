/*
* -----------------------------------------------------------------
* Copyright (C) 2012-2015, by www.dianhua.cn, Beijing, All rights reserved.
* -----------------------------------------------------------------
* Author: LiYi
* Create: 2015-7-23
*
* Changes (from 2015-7-23)
* -----------------------------------------------------------------
* 2015-7-23 : 创建 ToastUtil.java (作者:LiYi);
* 2015-9-10 : 1. 添加Toast管理线程及相关,使其在任何环境中都可以打开．
*             2. 添加带图片的Toast接口．
*             (作者:WanXin);
* -----------------------------------------------------------------
*/

package perseverance.li.quiet.util;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 15-10-30 14:32
 * ---------------------------------------------------------------
 * Describe: Toast统一管理工具类，使用场景与原生Toast相同
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 15-10-27 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class ToastUtil {

    /**
     * Toast 对象
     */
    private static Toast mToast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, Serializable message) {
        if (context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (null == mToast) {
            mToast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        if (message instanceof String) {
            mToast.setText((String) message);
        } else if (message instanceof Integer) {
            mToast.setText((Integer) message);
        }
        mToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, Serializable message) {
        if (context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (null == mToast) {
            mToast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG);
        } else {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        if (message instanceof String) {
            mToast.setText((String) message);
        } else if (message instanceof Integer) {
            mToast.setText((Integer) message);
        }
        mToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, Serializable message, int duration) {
        if (context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (null == mToast) {
            mToast = Toast.makeText(applicationContext, "", duration);
        } else {
            mToast.setDuration(duration);
        }
        if (message instanceof String) {
            mToast.setText((String) message);
        } else if (message instanceof Integer) {
            mToast.setText((Integer) message);
        }
        mToast.show();
    }


    /**
     * 取消toast，当前管理的任何toast
     */
    public static void hideToast() {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
    }
}
