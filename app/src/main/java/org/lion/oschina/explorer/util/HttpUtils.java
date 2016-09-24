package org.lion.oschina.explorer.util;

import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.base.AppContext;
import org.lion.oschina.explorer.bean.XmlUtils;

import okhttp3.Call;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 21:04
 * @描述 ${TODO}
 */
public class HttpUtils {
    //okhttpUtils 网络请求 获取xml 数据
    public static <T> T getContent(String url , final Class<T> object){
       // String url = "http://192.168.26.31:8080/oschina/list/active_list1/page0.xml";
        final T[] t = null;
        OkHttpUtils
                .get()//如果是Post请求，用.post()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e , int i ) {
                        //可以直接在这里进行UI的操作
                        //网络访问错误
                        Toast.makeText(AppContext.context(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response , int i ) {
                        //可以直接在这里进行UI的操作
                        //网络正常逻辑
                        Toast.makeText(AppContext.context(), "数据获取成功", Toast.LENGTH_SHORT).show();
                        t[0] = XmlUtils.toBean(object, response.getBytes());
                        //ActiveList list = XmlUtils.toBean(ActiveList.class, response.getBytes());
                    }
                });
        return null;
    }


}
