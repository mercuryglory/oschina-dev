package org.lion.oschina.tweet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lion.oschina.application.MyApplication;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class MyTweetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(MyApplication.context(),R.layout.fragment_tweet_my, null);
//        GifView iv_gif = (GifView) view.findViewById(R.id.iv_gif);
//        iv_gif.play();
        return view;
    }


}
