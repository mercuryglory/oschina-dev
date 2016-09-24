package org.lion.oschina.tweet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomDialog extends Dialog {

    private View customView;

    int     layoutRes;//布局文件
    Context context;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;

    }

    /**
     * 自定义布局的构造方法
     * @param context
     * @param themeResId
     */
    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.layoutRes = themeResId;
        customView = View.inflate(context, themeResId, null);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        window.setAttributes(layoutParams);

    }

    public View getCustomView() {
        return customView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);

    }
}