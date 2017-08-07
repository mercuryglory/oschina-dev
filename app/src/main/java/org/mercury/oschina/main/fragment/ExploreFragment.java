package org.mercury.oschina.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.qrcode.zxing.activity.QRCodeActivity;

import org.mercury.oschina.R;
import org.mercury.oschina.explorer.ui.activity.CampaignActivity;
import org.mercury.oschina.explorer.ui.activity.FindUserActivity;
import org.mercury.oschina.explorer.ui.activity.FriendCircleActivity;
import org.mercury.oschina.explorer.ui.activity.GenerateQRCodeActivity;
import org.mercury.oschina.explorer.ui.activity.ShakeActivity;
import org.mercury.oschina.main.BaseTitleFragment;

import butterknife.Bind;
import butterknife.OnClick;

import static org.mercury.oschina.R.id.ll_create_code;
import static org.mercury.oschina.R.id.ll_qr_code;
import static org.mercury.oschina.R.id.ll_shake;


/**
 * @创建者 Mercury
 * @创建时间 2016/8/14 19:19
 * @描述 发现模块 （朋友圈 找人 扫一扫 etc）
 */
public class ExploreFragment extends BaseTitleFragment implements View.OnClickListener {

    @Bind(R.id.ll_friend_circle)
    LinearLayout llFriendCircle;
    @Bind(R.id.ll_find_user)
    LinearLayout llFindUser;
    @Bind(R.id.ll_activity)
    LinearLayout llActivity;
    @Bind(ll_qr_code)
    LinearLayout llQrCode;
    @Bind(ll_create_code)
    LinearLayout llCreateCode;
    @Bind(ll_shake)
    LinearLayout llShake;

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
        LinearLayout llFriendCircle = (LinearLayout) root.findViewById(R.id.ll_friend_circle);
    }

    @OnClick({R.id.ll_friend_circle, R.id.ll_find_user, R.id.ll_activity, ll_qr_code,
            ll_create_code, ll_shake})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_friend_circle:
                startActivity(new Intent(getContext(), FriendCircleActivity.class));
                break;
            case R.id.ll_activity:
                startActivity(new Intent(getContext(), CampaignActivity.class));
                break;
            case ll_qr_code:
                startActivity(new Intent(getContext(), QRCodeActivity.class));
                break;
            case ll_shake:
                startActivity(new Intent(getContext(), ShakeActivity.class));
                break;
            case ll_create_code:
                startActivity(new Intent(getContext(), GenerateQRCodeActivity.class));
                break;
            case R.id.ll_find_user:
                startActivity(new Intent(getContext(), FindUserActivity.class));
                break;
        }
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_explore;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_explore;
    }

}
