package org.mercury.oschina.general.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.general.bean.BlogListBean;
import org.mercury.oschina.general.utils.GeneralUtils;

import butterknife.Bind;

/**
 * Created by more on 2016-08-15 21:53:14.
 */
public class BlogHolder extends BasicHolder<BlogListBean.ResultBean.ItemsBean> {
    @Bind(R.id.tv_item_blog_title)
    TextView mTvItemBlogTitle;
    @Bind(R.id.tv_item_blog_body)
    TextView mTvItemBlogBody;
    @Bind(R.id.tv_item_blog_history)
    TextView mTvItemBlogHistory;
    @Bind(R.id.iv_info_view)
    ImageView mIvInfoView;
    @Bind(R.id.tv_info_view)
    TextView mTvInfoView;
    @Bind(R.id.iv_info_comment)
    ImageView mIvInfoComment;
    @Bind(R.id.tv_info_comment)
    TextView mTvInfoComment;
    @Bind(R.id.textView)
    View mTextView;

    @Override
    public TextView getTitleView() {
        return mTvItemBlogTitle;
    }

    @Override
    public void bindView(BlogListBean.ResultBean.ItemsBean itemsBean) {
        mTvItemBlogTitle.setText(itemsBean.getTitle());
        mTvItemBlogBody.setText(itemsBean.getBody());
        mTvItemBlogHistory.setText(itemsBean.getPubDate());
        mTvInfoView.setText(itemsBean.getViewCount() + "");
        mTvInfoComment.setText(itemsBean.getCommentCount() + "");
        String href = itemsBean.getHref();
        if (AppContext.mAllVisitedItem.containsKey(href)) {
            mTvItemBlogTitle.setTextColor(GeneralUtils.getColor(R.color.item_clicked));
        } else {
            mTvItemBlogHistory.setTextColor(GeneralUtils.getColor(R.color.project_listitem_someinfo));
            mTvInfoView.setTextColor(GeneralUtils.getColor(R.color.project_listitem_someinfo));
            mTvInfoComment.setTextColor(GeneralUtils.getColor(R.color.project_listitem_someinfo));
            mTvItemBlogTitle.setTextColor(GeneralUtils.getColor(R.color.project_listitem_title));
            mTvItemBlogBody.setTextColor(GeneralUtils.getColor(R.color.project_listitem_description));
        }
    }

    @Override
    public View createView() {
        return View.inflate(AppContext.context, R.layout.fragment_item_blog, null);
    }
}
