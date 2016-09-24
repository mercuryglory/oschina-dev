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

import org.lion.oschina.adapter.PSAdapter;
import org.lion.oschina.application.MyApplication;
import org.lion.oschina.bean.MessageList;
import org.lion.oschina.bean.Messages;
import org.lion.oschina.global.OsChinaApp;
import org.lion.oschina.tweet.activity.TweetDetailActivity;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.utils.OschinaUri;
import org.lion.oschina.utils.Utils;
import org.lion.oschina.utils.XmlUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 15:18
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class PSFragment extends BasicFragment implements AdapterView.OnItemClickListener {
    private PullToRefreshListView mPtrlistView;
    private List<Messages> mMessages;
    @Override
    public View createView() {
        ListView listView = new ListView(getContext());
        mPtrlistView =
                (PullToRefreshListView) View.inflate(OsChinaApp.context, R.layout.refresh_list_view_layout2, null);

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
    protected Object loadDataThread() {

        OkHttpUtils
                .get()
                .url(OschinaUri.P_S_EME_URL)

                .build()
                .execute(new StringCallback() {



                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(OsChinaApp.context, "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                    //    Toast.makeText(OsChinaApp.context, "请求成功!", Toast.LENGTH_SHORT).show();
                        MessageList messageList = XmlUtils.toBean(MessageList.class, s.getBytes());
                        mMessages = messageList.getList();


                        /*for (Messages  bean:Messages
                             ) {

                        }*/
                        Utils.runOnUIThread(new Runnable() {

                            private PSAdapter mPSAdapter;

                            @Override
                            public void run() {
                                mPSAdapter = new PSAdapter(mMessages);
                                mPSAdapter.setContext(getActivity());
                                mPtrlistView.setAdapter(mPSAdapter);
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
        Intent intent = new Intent(MyApplication.context, TweetDetailActivity.class);
        //  Tweet tweet = mActives.get((int) id);
        Messages messages = mMessages.get((int) id);
        intent.putExtra(Constant.TWEET_DETAIL, messages.getId());
        startActivity(intent);
    }
}