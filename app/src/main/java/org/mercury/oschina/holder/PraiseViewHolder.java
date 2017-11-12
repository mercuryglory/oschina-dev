package org.mercury.oschina.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.mercury.oschina.AppContext;
import org.mercury.oschina.R;
import org.mercury.oschina.bean.TweetLike;

import butterknife.BindView;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 16:27
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class PraiseViewHolder extends BasicHolder<TweetLike> {
    @BindView(R.id.iv_praise_pic)
    ImageView mIvPraisePic;
    @BindView(R.id.tv_praise_name)
    TextView  mTvPraiseName;
    @BindView(R.id.tv_praise_content)
    TextView  mTvPraiseContent;
    @BindView(R.id.tv_praise_time)
    TextView  mTvPraiseTime;
    @BindView(R.id.tv_praise_author)
    TextView  mTvPraiseAuthor;
    @BindView(R.id.tv_praise_desc)
    TextView  mTvPraiseDesc;

    @Override
    protected View createView() {

        return View.inflate(AppContext.context, R.layout.item_praise, null);
    }

    @Override
    public void bindView(TweetLike appInfo) {
        ImageLoader.getInstance().displayImage(appInfo.getUser().getPortrait(), mIvPraisePic, mOptions);
        mTvPraiseName.setText(appInfo.getUser().getName());
        mTvPraiseTime.setText(appInfo.getDatatime());
        mTvPraiseDesc.setText(appInfo.getTweet().getBody());
        mTvPraiseAuthor.setText(appInfo.getTweet().getAuthor()+" : ");
    }
}
