package org.mercury.oschina.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.mercury.oschina.AppContext;
import org.mercury.oschina.R;
import org.mercury.oschina.bean.Friend;

import butterknife.BindView;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/15 17:45
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class FansViewHolder extends BasicHolder<Friend> {

    @BindView(R.id.iv_portrait)
    ImageView    mIvPortrait;
    @BindView(R.id.tv_fans_name)
    TextView     mTvFansName;
    @BindView(R.id.iv_fans_gender)
    ImageView    mIvFansGender;
    @BindView(R.id.tv_fans_from)
    TextView     mTvFansFrom;
    @BindView(R.id.tv_fans_expertise)
    TextView     mTvFansExpertise;
    @BindView(R.id.ll_fans_items)
    LinearLayout mLlFansItems;

    @Override
    protected View createView() {

        return View.inflate(AppContext.context, R.layout.item_fans, null);

    }

    @Override
    public void bindView(Friend appInfo) {
        //初始化ImageLoader
        mTvFansName.setText(appInfo.getName());
        mIvFansGender.setImageResource(appInfo.getGender() == 1 ? R.mipmap.userinfo_icon_male : R.mipmap.userinfo_icon_female);
        mTvFansExpertise.setText(appInfo.getExpertise());
        mTvFansFrom.setText(appInfo.getFrom());

        ImageLoader.getInstance().displayImage(appInfo.getPortrait(),mIvPortrait, mOptions);
    }
}
