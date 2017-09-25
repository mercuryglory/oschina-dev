package org.mercury.oschina.explorer.ui;

import android.content.Context;
import android.content.Intent;

import org.mercury.oschina.Constant;
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

    public static void show(Context context, String ident) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        intent.putExtra(Constant.IDENT, ident);
        context.startActivity(intent);
    }

}
