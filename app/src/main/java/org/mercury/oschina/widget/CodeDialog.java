package org.mercury.oschina.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.tweet.util.ToastUtil;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.FileUtil;
import org.mercury.oschina.utils.QrCodeUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wang.zhonghao on 2017/8/28.
 */

public class CodeDialog extends Dialog {

    private Bitmap bitmap;

    public CodeDialog(@NonNull Context context) {
        this(context, R.style.App_Theme_Dialog);
    }

    public CodeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_code, null);
        ImageView iv_code = (ImageView) contentView.findViewById(R.id.iv_code);
        try {
            bitmap = QrCodeUtils.Create2DCode(String.format("http://my.oschina.net/u/%s",
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

        //二维码长按保存到本地
        iv_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO: 2017/8/31 图片保存到本地后打开是黑色的
                String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(sdPath + File.separator + Constant.QRCODE_DIR);

                boolean canReadWrite = true;
                if (!file.exists()) {
                    canReadWrite = file.mkdirs();
                }
                if (canReadWrite) {
                    file = new File(file.getAbsoluteFile(), "qrcode.png");
                    if (file.exists())
                        file.delete();
                    try {
                        Log.i("file", file.getAbsolutePath());
                        FileUtil.saveImgToSD(getContext(), file.getAbsolutePath(), bitmap, 100);
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.showToast("保存失败,请检查是否开启相关权限");
                        return false;
                    }
                } else {
                    ToastUtil.showToast("保存失败,请检查是否开启相关权限");
                    return false;
                }

                CodeDialog.this.dismiss();
                ToastUtil.showToast("已保存到本地");
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
