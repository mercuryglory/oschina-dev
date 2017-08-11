package org.mercury.oschina.tweet.util;

import android.content.Context;
import android.widget.Toast;

import org.mercury.oschina.base.AppContext;


/**
 * 类名:      ToastUtil
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class ToastUtil {
    private static Toast toast;

    private static Toast sToast;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.context, text, Toast.LENGTH_SHORT);
        } else {

            toast.setText(text);
        }
        toast.show();

    }

    public static void showToast(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
