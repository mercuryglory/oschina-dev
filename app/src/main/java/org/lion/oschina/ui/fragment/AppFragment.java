package org.lion.oschina.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.adapter.CollectAdapter;
import org.lion.oschina.bean.Favorite;
import org.lion.oschina.bean.FavoriteList;
import org.lion.oschina.global.OsChinaApp;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.ui.activity.AppDetailsActivity;
import org.lion.oschina.utils.OschinaUri;
import org.lion.oschina.utils.Utils;
import org.lion.oschina.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * @创建者 Administrator
 * @创建时间 2016/8/15 2:29
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class AppFragment extends BasicFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView mPtrlistView;
    private List<Favorite> list = new ArrayList<>();
    private List<Favorite> mFavorites;
    @Override
    public View createView() {
        System.out.println("这个方法走了");
        mPtrlistView =
                (PullToRefreshListView) View.inflate(OsChinaApp.context, R.layout.refresh_list_view_layout, null);
        // 设置模式
        mPtrlistView.setMode(PullToRefreshBase.Mode.BOTH);

        mPtrlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                System.out.println("当前监听");
                // 网络操作
                mMLoadPager.loadData();
            }
        });
        mPtrlistView.setOnItemClickListener(this);
        return mPtrlistView;
    }

    @Override
    public Object loadDataThread() {
       /* GetDatasForCollectActivity gdf = new GetDatasForCollectActivity(OschinaUri.COLLECT_1, mPtrlistView);
        List<Favorite> favorites = gdf.sendRequestData();
        if (favorites != null) {
            list.addAll(favorites);
        }
        System.out.println("这个方法走了");*/
        OkHttpUtils
                .get()
                .url(OschinaUri.COLLECT_1)

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
                        mFavorites = favoriteList.getList();

                        Utils.runOnUIThread(new Runnable() {

                            private CollectAdapter mCollectAdapter;

                            @Override
                            public void run() {
                                mCollectAdapter = new CollectAdapter(mFavorites);

                                mPtrlistView.setAdapter(mCollectAdapter);
                                //停止刷新

                                mPtrlistView.onRefreshComplete();
                            }
                        });

                    }
                });
        return "";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(), AppDetailsActivity.class);
        if (mFavorites == null) {
            ToastUtil.showToast("kong");

        } else {

            intent.putExtra("APPURL", mFavorites.get((int) id).getUrl());
            startActivity(intent);


        }
    }
}
