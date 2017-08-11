package org.mercury.oschina.synthesis.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.synthesis.bean.HotListBean;
import org.mercury.oschina.synthesis.utils.GeneralUtils;

import butterknife.Bind;

/**
 * Created by more on 2016-08-15 21:11:55.
 */
public class HotHolder extends BasicHolder<HotListBean.ObjListBean> {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    @Bind(R.id.tv_description)
    TextView mTvDescription;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.iv_info_comment)
    ImageView mIvInfoComment;
    @Bind(R.id.tv_comment_count)
    TextView mTvCommentCount;

    @Override
    public TextView getTitleView() {
        return mTvTitle;
    }

    @Override
    public void bindView(HotListBean.ObjListBean objListBean) {
        mTvTitle.setText(objListBean.getTitle());
        mTvDescription.setText(objListBean.getBody());
        mTvCommentCount.setText(objListBean.getComment_count()+"");
        mTvTime.setText(objListBean.getPub_date());


        String href = objListBean.getHref();
        if (AppContext.mAllVisitedItem.containsKey(href)) {
            mTvTitle.setTextColor(GeneralUtils.getColor(R.color.item_clicked));
        } else {
            mTvTime.setTextColor(GeneralUtils.getColor(R.color.project_listitem_someinfo));
            mTvCommentCount.setTextColor(GeneralUtils.getColor(R.color.project_listitem_someinfo));
            mTvTitle.setTextColor(GeneralUtils.getColor(R.color.project_listitem_title));
            mTvDescription.setTextColor(GeneralUtils.getColor(R.color.project_listitem_description));
        }
    }

    @Override
    public View createView() {
        return View.inflate(AppContext.context, R.layout.item_list_news, null);
    }
}
