package org.mercury.oschina.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import org.mercury.oschina.R;
import org.mercury.oschina.tweet.util.ToastUtil;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.QrCodeUtils;

/**
 * Created by wang.zhonghao on 2017/8/28.
 */

public class CodeDialog extends Dialog {

    public CodeDialog(@NonNull Context context) {
        this(context, R.style.App_Theme_Dialog);
    }

    public CodeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_code, null);
        ImageView iv_code = (ImageView) contentView.findViewById(R.id.iv_code);
        try {
            Bitmap bitmap = QrCodeUtils.Create2DCode(String.format("http://my.oschina.net/u/%s",
                    AccessTokenHelper.getUserId()));
            iv_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CodeDialog.this.dismiss();
                return false;
            }
        });
        iv_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.showToast("已保存到本地");
                CodeDialog.this.dismiss();
                return true;
            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
    }

    protected CodeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener
            cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.CENTER);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }
}
