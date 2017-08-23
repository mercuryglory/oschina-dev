package org.mercury.oschina.main.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.adapter.FansAdapter;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.bean.Friend;
import org.mercury.oschina.bean.FriendsList;
import org.mercury.oschina.utils.Utils;
import org.mercury.oschina.utils.XmlUtils;

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
public class ConcertFragment extends BasicFragment implements View.OnClickListener {
    private FansAdapter           mFansAdapter;
    private  PullToRefreshListView mPtrlistView;
    @Override
    public View createView() {

        mPtrlistView =
                (PullToRefreshListView)View.inflate(AppContext.context, R.layout.refresh_list_view_layout, null);

        // 设置模式
        mPtrlistView.setMode(PullToRefreshBase.Mode.BOTH);

        mPtrlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView)
            {

                // 网络操作
                mMLoadPager.loadData();
            }
        });
        mPtrlistView.setOnClickListener(this);
        return mPtrlistView;
    }
    @Override
    protected Object loadDataThread() {
        OkHttpUtils
                .get()
                .url("http://www.oschina.net/action/api/friends_list")
                // .id(101)
                .addParams("uid", "993896")
                .addParams("pageIndex", "0")
                .addParams("pageSize", "20")
                .addParams("relation", "0")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(getActivity(), "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                 //       Toast.makeText(getActivity(), "请求成功!", Toast.LENGTH_SHORT).show();
                        FriendsList friendsList = XmlUtils.toBean(FriendsList.class, s.getBytes());
                        final List<Friend> friendlist = friendsList.getFriendlist();



                        Utils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {

                                mFansAdapter = new FansAdapter(friendlist);
                                mPtrlistView.setAdapter(mFansAdapter);
                                mPtrlistView.onRefreshComplete();
                            }
                        });
                    }
                });
        return "";
    }


    @Override
    public void onClick(View v) {

    }
}
