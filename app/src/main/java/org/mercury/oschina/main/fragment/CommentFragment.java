package org.mercury.oschina.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.adapter.CommentAdapter;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.bean.Active;
import org.mercury.oschina.bean.ActiveList;
import org.mercury.oschina.tweet.activity.TweetDetailActivity;
import org.mercury.oschina.Constant;
import org.mercury.oschina.utils.OschinaUri;
import org.mercury.oschina.utils.Utils;
import org.mercury.oschina.utils.XmlUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 15:15
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class CommentFragment extends BasicFragment implements AdapterView.OnItemClickListener {
    private PullToRefreshListView mPtrlistView;
    private List<Active> mActives;
    @Override
    public View createView() {

        mPtrlistView =
                (PullToRefreshListView) View.inflate(AppContext.context, R.layout.refresh_list_view_layout2, null);

        // 设置模式
        mPtrlistView.setMode(PullToRefreshBase.Mode.BOTH);

        mPtrlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                // 网络操作
                mMLoadPager.loadData();
            }
        });
        mPtrlistView.setOnItemClickListener(this);
        return mPtrlistView;
    }

    @Override
    protected Object loadDataThread() {

        OkHttpUtils
                .get()
                .url(OschinaUri.CONMMENT_URL)

                .build()
                .execute(new StringCallback() {



                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(AppContext.context, "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                      //  Toast.makeText(AppContext.context, "请求成功!", Toast.LENGTH_SHORT).show();
                        ActiveList activeList = XmlUtils.toBean(ActiveList.class, s.getBytes());
                        mActives = activeList.getList();

                        Utils.runOnUIThread(new Runnable() {

                            private CommentAdapter mCommentAdapter;

                            @Override
                            public void run() {
                                mCommentAdapter = new CommentAdapter(mActives);

                                mPtrlistView.setAdapter(mCommentAdapter);
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
        Intent intent = new Intent(AppContext.context, TweetDetailActivity.class);
      //  Tweet tweet = mActives.get((int) id);
        Active active = mActives.get((int) id);
        intent.putExtra(Constant.TWEET_DETAIL, active.getId());
        startActivity(intent);
    }
}