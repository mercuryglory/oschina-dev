package org.lion.oschina.tweet.util;

import android.widget.Toast;

import org.lion.oschina.application.MyApplication;


/**
 * 类名:      ToastUtil
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.context, text, Toast.LENGTH_SHORT);
        } else {

            toast.setText(text);
        }
        toast.show();

    }
}
