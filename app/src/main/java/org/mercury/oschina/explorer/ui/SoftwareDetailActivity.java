package org.mercury.oschina.explorer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BasePresenterActivity;
import org.mercury.oschina.explorer.bean.SoftwareDetail;
import org.mercury.oschina.explorer.contract.SoftwareDetailContract;
import org.mercury.oschina.explorer.contract.SoftwarePresenter;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.widget.OWebView;

import butterknife.Bind;

/**
 * 软件详情页面，承载截取的url尾部的identId，获取详情数据后展示
 */
public class SoftwareDetailActivity extends BasePresenterActivity<SoftwareDetailContract
        .Presenter, SoftwareDetail> implements SoftwareDetailContract.View {


    @Bind(R.id.toolbar)
    Toolbar          toolbar;
    @Bind(R.id.iv_label_recommend)
    ImageView        ivLabelRecommend;
    @Bind(R.id.iv_software_icon)
    ImageView        ivSoftwareIcon;
    @Bind(R.id.tv_software_name)
    TextView         tvSoftwareName;
    @Bind(R.id.tv_home)
    TextView         tvHome;
    @Bind(R.id.tv_document)
    TextView         tvDocument;
    @Bind(R.id.webView)
    OWebView         webView;
    @Bind(R.id.lay_webview)
    FrameLayout      layWebview;
    @Bind(R.id.tv_software_author_name)
    TextView         tvSoftwareAuthorName;
    @Bind(R.id.tv_software_protocol)
    TextView         tvSoftwareProtocol;
    @Bind(R.id.tv_software_language)
    TextView         tvSoftwareLanguage;
    @Bind(R.id.tv_software_system)
    TextView         tvSoftwareSystem;
    @Bind(R.id.tv_software_record_time)
    TextView         tvSoftwareRecordTime;
    @Bind(R.id.rv_recommend)
    RecyclerView     rvRecommend;
    @Bind(R.id.lay_nsv)
    NestedScrollView layNsv;
    @Bind(R.id.tv_comment_count)
    TextView         tvCommentCount;
    @Bind(R.id.ll_comment)
    LinearLayout     llComment;
    @Bind(R.id.iv_fav)
    ImageView        ivFav;
    @Bind(R.id.tv_fav_count)
    TextView         tvFavCount;
    @Bind(R.id.ll_fav)
    LinearLayout     llFav;
    @Bind(R.id.ll_share)
    LinearLayout     llShare;
    @Bind(R.id.lay_option)
    LinearLayout     layOption;

    private String mIdent;

    @Override
    protected int getContentView() {
        return R.layout.activity_software_detail;
    }

    public static void show(Context context, String ident) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        intent.putExtra(Constant.IDENT, ident);
        context.startActivity(intent);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (bundle != null) {
            mIdent = bundle.getString(Constant.IDENT, "");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(mIdent)) {
            showWaitDialog();
            mPresenter.refreshData(mIdent);
        }

    }

    @Override
    protected void initWidget() {
        mPresenter = new SoftwarePresenter(this);
        toolbar.setTitle("软件详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }


    @Override
    public void refreshSuccess(SoftwareDetail software) {
        hideWaitDialog();
        GlideUtils.loadImage(this, software.getLogo(), ivSoftwareIcon, R.drawable
                .bg_send_error);
        tvSoftwareName.setText(software.getExtensionTitle());
        webView.loadDataAsync(software.getBody(), new OWebView.FinishTask() {
            @Override
            public void finishTask() {

            }
        });
        tvSoftwareAuthorName.setText(software.getTitle());
        tvSoftwareProtocol.setText(software.getLicense());
        tvSoftwareLanguage.setText(software.getLanguages());
        tvSoftwareSystem.setText(software.getOs());
        tvSoftwareRecordTime.setText(software.getRecordtime());

    }
}
