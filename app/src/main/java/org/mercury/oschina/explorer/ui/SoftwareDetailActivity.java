package org.mercury.oschina.explorer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BasePresenterActivity;
import org.mercury.oschina.explorer.bean.SoftwareDetail;
import org.mercury.oschina.explorer.contract.SoftwareDetailContract;
import org.mercury.oschina.explorer.contract.SoftwarePresenter;
import org.mercury.oschina.main.activity.BrowserActivity;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.utils.SpUtils;
import org.mercury.oschina.widget.OWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 软件详情页面，承载截取的url尾部的identId，获取详情数据后展示
 */
public class SoftwareDetailActivity extends BasePresenterActivity<SoftwareDetailContract
        .Presenter, SoftwareDetail> implements SoftwareDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar          toolbar;
    @BindView(R.id.iv_label_recommend)
    ImageView        ivLabelRecommend;
    @BindView(R.id.iv_software_icon)
    ImageView        ivSoftwareIcon;
    @BindView(R.id.tv_software_name)
    TextView         tvSoftwareName;
    @BindView(R.id.tv_home)
    TextView         tvHome;
    @BindView(R.id.tv_document)
    TextView         tvDocument;
    @BindView(R.id.webView)
    OWebView         webView;
    @BindView(R.id.ll_content)
    LinearLayout     llContent;
    @BindView(R.id.tv_software_author_name)
    TextView         tvSoftwareAuthorName;
    @BindView(R.id.tv_software_protocol)
    TextView         tvSoftwareProtocol;
    @BindView(R.id.tv_software_language)
    TextView         tvSoftwareLanguage;
    @BindView(R.id.tv_software_system)
    TextView         tvSoftwareSystem;
    @BindView(R.id.tv_software_record_time)
    TextView         tvSoftwareRecordTime;
    @BindView(R.id.rv_recommend)
    RecyclerView     rvRecommend;
    @BindView(R.id.lay_nsv)
    NestedScrollView layNsv;
    @BindView(R.id.tv_comment_count)
    TextView         tvCommentCount;
    @BindView(R.id.ll_comment)
    LinearLayout     llComment;
    @BindView(R.id.iv_fav)
    ImageView        ivFav;
    @BindView(R.id.tv_fav_count)
    TextView         tvFavCount;
    @BindView(R.id.ll_fav)
    LinearLayout     llFav;
    @BindView(R.id.ll_share)
    LinearLayout     llShare;
    @BindView(R.id.lay_option)
    LinearLayout     layOption;
    @BindView(R.id.textView)
    TextView         textView;

    private String         mIdent;
    private SoftwareDetail mSoftwareDetail;
    private boolean isFavorite;

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
            mPresenter.refreshData(mIdent, Long.valueOf(SpUtils.get(this, Constant.USER_ID, "")
                    .toString()));
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
    public void refreshSuccess(final SoftwareDetail softwareDetail) {
        mSoftwareDetail = softwareDetail;
        webView.loadDataAsync(softwareDetail.getBody(), new OWebView.FinishTask() {
            @Override
            public void finishTask() {
                hideWaitDialog();
                updateView(softwareDetail);
            }
        });

    }

    private void updateView(SoftwareDetail software) {
        llContent.setVisibility(View.VISIBLE);
        GlideUtils.loadImage(this, software.getLogo(), ivSoftwareIcon, R.drawable
                .bg_send_error);
        tvSoftwareName.setText(software.getExtensionTitle());
        tvSoftwareAuthorName.setText(software.getTitle());
        tvSoftwareProtocol.setText(software.getLicense());
        tvSoftwareLanguage.setText(software.getLanguages());
        tvSoftwareSystem.setText(software.getOs());
        tvSoftwareRecordTime.setText(software.getRecordtime());
        if (software.getFavorite() == 1) {
            ivFav.setImageResource(R.drawable.ic_faved);
        }
        isFavorite = software.getFavorite() == 1;
    }


    @OnClick({R.id.tv_home, R.id.tv_document,R.id.ll_comment, R.id.ll_fav, R.id.ll_share})
    public void onClick(View view) {
        switch (view.getId()) {
            //软件官网
            case R.id.tv_home:
                if (mSoftwareDetail != null) {
                    BrowserActivity.show(this, mSoftwareDetail.getUrl());
                }
                break;
            //软件文档
            case R.id.tv_document:
                if (mSoftwareDetail != null) {
                    BrowserActivity.show(this, mSoftwareDetail.getDocument());
                }
                break;
            //评论
            case R.id.ll_comment:

                break;
            //收藏,取消收藏
            case R.id.ll_fav:
                if (mSoftwareDetail != null) {
                    if (isFavorite) {
                        mPresenter.deFavorite(mSoftwareDetail.getId(), Constant.SOFTWARE);
                    } else {
                        mPresenter.addFavorite(mSoftwareDetail.getId(), Constant.SOFTWARE);
                    }
                }
                break;
            //分享
            case R.id.ll_share:

                break;
        }
    }


    @Override
    public void favoriteSuccess() {
        showToast("收藏成功");
        isFavorite = true;
        ivFav.setImageResource(R.drawable.ic_faved);
    }

    @Override
    public void defavoriteSuccess() {
        showToast("取消收藏成功");
        isFavorite = false;
        ivFav.setImageResource(R.drawable.ic_fav);
    }
}
