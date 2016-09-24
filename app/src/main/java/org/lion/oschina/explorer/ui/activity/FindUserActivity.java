package org.lion.oschina.explorer.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.lion.oschina.explorer.adapter.FindUserAdapter;
import org.lion.oschina.explorer.bean.FindUserBean;
import org.lion.oschina.general.api.HttpApi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindUserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv_list)
    ListView mLvList;
    private SearchView mSearchView;
    private static final String TAG = "====_FindUser";

    Callback<FindUserBean> mCallback = new Callback<FindUserBean>() {
        @Override
        public void onResponse(Call<FindUserBean> call, Response<FindUserBean> response) {
            Logger.t(TAG).i(response.body()+"");
            List<FindUserBean.ObjListBean> obj_list = response.body().getObj_list();
            if (obj_list!=null&&obj_list.size()>0){
                mLvList.setVisibility(View.VISIBLE);
                mAdapter.updateItem(obj_list);
            }else {
                mLvList.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Call<FindUserBean> call, Throwable t) {
        }
    };
    private FindUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        ButterKnife.bind(this);
        initActionbar();
        initView();
    }

    public void initView() {
        mAdapter = new FindUserAdapter();
        mLvList.setAdapter(mAdapter);
        mLvList.setOnItemClickListener(this);
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("好友圈");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //设置箭头点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search_content);
        mSearchView = (SearchView) search.getActionView();
        mSearchView.setIconifiedByDefault(false);
        setSearch();
        return super.onCreateOptionsMenu(menu);
    }

    private void setSearch() {
        mSearchView.setQueryHint("输入用户昵称");
        TextView textView = (TextView) mSearchView
                .findViewById(R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(0x90ffffff);

        mSearchView
                .setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Logger.i(query + "");
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Logger.i(newText + "");
                        if (newText!=null&& !TextUtils.isEmpty(newText)){
                            search(newText);
                        }else {
                            mLvList.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });
    }

    private void search(String newText) {
        HttpApi.getOscUser(newText,mCallback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FindUserBean.ObjListBean item = mAdapter.getItem(position);

    }
}
