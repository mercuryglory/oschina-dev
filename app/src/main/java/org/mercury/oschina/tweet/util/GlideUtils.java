package org.mercury.oschina.tweet.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.mercury.oschina.R;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/18
 * 描述:      ${TODO}
 */
public class GlideUtils {

    public static void loadCircleImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.widget_dface)
                .error(R.mipmap.widget_dface)
                .into(view);
    }
}
