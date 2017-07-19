package org.mercury.oschina.explorer.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.bean.FindUserBean;
import org.mercury.oschina.general.holder.BasicHolder;

import butterknife.Bind;

/**
 * Created by Mercury on 2016-08-19 12:59:24.
 */
public class FindUserHolder extends BasicHolder<FindUserBean.ObjListBean> {

    @Bind(R.id.iv_avatar)
    ImageView mIvAvatar;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;
    @Bind(R.id.top)
    LinearLayout mTop;
    @Bind(R.id.tv_from)
    TextView mTvFrom;
    @Bind(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    public TextView getTitleView() {
        return mTvName;
    }

    @Override
    public void bindView(FindUserBean.ObjListBean objListBean) {
        mTvName.setText(objListBean.getName());
        mTvFrom.setText(objListBean.getFrom());
        if (objListBean.getGender()!=1){
            mIvGender.setBackgroundResource(R.mipmap.userinfo_icon_female);
        }
        ImageLoader.getInstance().displayImage(objListBean.getPortrait(),mIvAvatar);
    }

    @Override
    public View createView() {
        return View.inflate(AppContext.mContext, R.layout.list_cell_friend, null);
    }
}
