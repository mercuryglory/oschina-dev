package org.lion.oschina.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.lion.oschina.explorer.ui.activity.Activitys;
import org.lion.oschina.explorer.ui.activity.FindUserActivity;
import org.lion.oschina.explorer.ui.activity.FriendQActivity;
import org.lion.oschina.explorer.ui.activity.GenerateQRCodeActivity;
import org.lion.oschina.explorer.ui.activity.QRCodeActivity;
import org.lion.oschina.explorer.ui.activity.ShakeActivity;


/**
 * @创建者 LY
 * @创建时间 2016/8/14 19:19
 * @描述 ${TODO}
 */
public class ExploreFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mLl_more_friend;
    private LinearLayout ll_activity;
    private LinearLayout ll_qr_code;
    private LinearLayout ll_shake;
    private LinearLayout ll_create_code;
    private LinearLayout ll_find_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_fregment, null);
        mLl_more_friend = (LinearLayout) view.findViewById(R.id.ll_more_friend);
        ll_activity = (LinearLayout) view.findViewById(R.id.ll_activity);
        ll_qr_code = (LinearLayout) view.findViewById(R.id.ll_qr_code);
        ll_shake = (LinearLayout) view.findViewById(R.id.ll_shake);
        ll_create_code = (LinearLayout) view.findViewById(R.id.ll_create_code);
        ll_find_user = (LinearLayout) view.findViewById(R.id.ll_find_user);
        init();
        return view;
    }

    public void init() {
        mLl_more_friend.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_qr_code.setOnClickListener(this);
        ll_shake.setOnClickListener(this);
        ll_create_code.setOnClickListener(this);
        ll_find_user.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_more_friend:
                startActivity(new Intent(getContext(), FriendQActivity.class));
                break;
            case R.id.ll_activity:
                startActivity(new Intent(getContext(), Activitys.class));
                break;
            case R.id.ll_qr_code:
                startActivity(new Intent(getContext(), QRCodeActivity.class));
                break;
            case R.id.ll_shake:
                startActivity(new Intent(getContext(), ShakeActivity.class));
                break;
            case R.id.ll_create_code:
                startActivity(new Intent(getContext(), GenerateQRCodeActivity.class));
                break;
            case R.id.ll_find_user:
                startActivity(new Intent(getContext(), FindUserActivity.class));
                break;
        }
    }
}
