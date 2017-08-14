package org.mercury.oschina.utils;

import android.graphics.BitmapFactory;

/**
 * Created by wang.zhonghao on 2017/8/14.
 */

public class BitmapUtil {

    /**
     * 创建一个图片处理的option
     * @return
     */
    public static BitmapFactory.Options createOption() {
        return new BitmapFactory.Options();
    }

    /**
     * 获取图片的真实后缀
     * @param filePath
     * @return
     */
    public static String getExtension(String filePath) {
        BitmapFactory.Options option = createOption();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, option);
        String outMimeType = option.outMimeType;
        return outMimeType.substring(outMimeType.lastIndexOf(".") + 1);
    }

}
