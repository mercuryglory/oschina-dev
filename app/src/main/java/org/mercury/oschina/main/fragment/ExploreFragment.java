package org.mercury.oschina.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.qrcode.zxing.activity.CaptureActivity;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseTitleFragment;
import org.mercury.oschina.explorer.ui.ShakeActivity;
import org.mercury.oschina.explorer.ui.SoftwareActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static org.mercury.oschina.R.id.ll_qr_code;
import static org.mercury.oschina.R.id.ll_shake;


/**
 * @创建者 Mercury
 * @创建时间 2016/8/14 19:19
 * @描述 发现模块 （开源软件 扫一扫 摇一摇 etc）
 */
public class ExploreFragment extends BaseTitleFragment implements View.OnClickListener {

    @BindView(ll_qr_code)
    LinearLayout llQrCode;
    @BindView(ll_shake)
    LinearLayout llShake;

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
        LinearLayout llFriendCircle = (LinearLayout) root.findViewById(R.id.ll_qr_code);
    }

    @OnClick({R.id.ll_software, ll_qr_code, ll_shake})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开源软件
            case R.id.ll_software:
                startActivity(new Intent(getContext(), SoftwareActivity.class));
                break;
            //扫一扫
            case ll_qr_code:
                startActivity(new Intent(getContext(), CaptureActivity.class));
                break;
            //摇一摇
            case ll_shake:
                startActivity(new Intent(getContext(), ShakeActivity.class));
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
