package org.mercury.oschina.explorer.ui;

import android.content.Context;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;

/**
 * 软件详情页面，承载截取的url尾部的identId，获取详情数据后展示
 */
public class SoftwareDetailActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_software_detail;
    }

    public static void show(Context context, String url) {

    }

}
