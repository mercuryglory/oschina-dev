package org.lion.oschina.utils;

import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.adapter.CollectAdapter;
import org.lion.oschina.bean.Favorite;
import org.lion.oschina.bean.FavoriteList;
import org.lion.oschina.global.OsChinaApp;

import java.util.List;

import okhttp3.Call;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/16 17:17
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class GetDatasForCollectActivity {

    private static String                mUrl;
    // private static ListView mLv;
    private static PullToRefreshListView mLv;
    private static List<Favorite>        favorites;

    public GetDatasForCollectActivity(String url, PullToRefreshListView lv) {

        mUrl = url;
        mLv = lv;
    }

    public static List<Favorite> sendRequestData() {
        OkHttpUtils
                .get()
                .url(mUrl)

                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(OsChinaApp.context, "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        // Toast.makeText(OsChinaApp.context, "请求成功!", Toast.LENGTH_SHORT).show();
                        FavoriteList favoriteList = XmlUtils.toBean(FavoriteList.class, s.getBytes());
                        final List<Favorite> favorites = favoriteList.getList();

                        Utils.runOnUIThread(new Runnable() {

                            private CollectAdapter mCollectAdapter;

                            @Override
                            public void run() {
                                mCollectAdapter = new CollectAdapter(favorites);

                                mLv.setAdapter(mCollectAdapter);
                                //停止刷新

                                mLv.onRefreshComplete();
                            }
                        });

                    }
                });
        return favorites;

    }
}
