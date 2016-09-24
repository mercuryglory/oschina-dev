package org.lion.oschina.explorer.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @创建者 LY
 * @创建时间 2016/8/16 12:59
 * @描述 ${TODO}
 */
public class ApiHttp {
    public static void getData(String url ,StringCallback stringCallback) {
        OkHttpUtils
                .get()//如果是Post请求，用.post()
                .url(url)
                .build()
                .execute(stringCallback);
    }
}
