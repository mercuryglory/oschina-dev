package org.mercury.oschina.tweet.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import net.oschina.common.widget.Loading;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by wang.zhonghao on 2017/8/14.
 * 多张图片放大查看
 */

public class ImageGalleryActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        EasyPermissions.PermissionCallbacks {

    @Bind(R.id.vp_image)
    ViewPager vpImage;
    @Bind(R.id.tv_index)
    TextView  tvIndex;
    @Bind(R.id.iv_download)
    ImageView ivDownload;

    public static final String KEY_IMAGE    = "image";
    public static final String KEY_POSITION = "position";
    public static final String KEY_SAVE     = "save";

    private String[]  mImages;
    private int       mCurrentPos;
    private boolean   mNeedSaveLocal;
    private boolean[] mDownloadStatus;

    @Override
    protected int getContentView() {
        return R.layout.activity_image_gallery;
    }

    public static void show(Context context, String[] images) {
        //缺省位置和缓存策略
        show(context, images, true);
    }

    public static void show(Context context, String[] images, int index) {
        //缺省缓存策略
        show(context, images, index, true);
    }

    public static void show(Context context, String[] images, boolean saveLocal) {
        //缺省位置
        show(context, images, 0, saveLocal);
    }

    public static void show(Context context, String[] images, int index, boolean saveLocal) {
        if (images == null || images.length == 0)
            return;

        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra(KEY_IMAGE, images);
        intent.putExtra(KEY_POSITION, index);
        intent.putExtra(KEY_SAVE, saveLocal);
        context.startActivity(intent);

    }

    @Override
    protected void initBundle(Bundle bundle) {
        mImages = bundle.getStringArray(KEY_IMAGE);
        mCurrentPos = bundle.getInt(KEY_POSITION, 0);
        mNeedSaveLocal = bundle.getBoolean(KEY_SAVE, true);

        //初始化下载状态
        if (mImages != null) {
            mDownloadStatus = new boolean[mImages.length];
        }
    }

    @Override
    protected void initData() {
        int len = mImages.length;
        if (len == 1) {
            tvIndex.setVisibility(View.GONE);
        }

        if (mCurrentPos < 0 || mCurrentPos >= len) {
            mCurrentPos = 0;
        }
        vpImage.addOnPageChangeListener(this);
        //先绑定适配器再滑动到开始所点的位置
        vpImage.setAdapter(new ViewPagerAdapter());
        vpImage.setCurrentItem(mCurrentPos);

        //初始的时候如果是从第一个图片点进来的,setCurrentItem不会走onPageSelected
        tvIndex.setText(String.format("%s/%s", mCurrentPos + 1, mImages.length));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPos = position;
        tvIndex.setText(String.format("%s/%s", position + 1, mImages.length));
        //下载按钮是否可见
        changeSaveBtnStatus(mDownloadStatus[position]);

    }

    private void changeSaveBtnStatus(boolean downloadStatu) {
        if (mNeedSaveLocal)
            ivDownload.setVisibility(downloadStatu ? View.VISIBLE : View.GONE);
        else
            ivDownload.setVisibility(View.GONE);
    }

    private void updateSaveBtnStatus(int pos, boolean downloadStatu) {
        mDownloadStatus[pos] = downloadStatu;
        if (mCurrentPos == pos) {
            changeSaveBtnStatus(downloadStatu);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static final int PERMISSION_ID = 0x0001;

    @OnClick(R.id.iv_download)
    @AfterPermissionGranted(PERMISSION_ID)
    public void onClick() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            showToast("有权限");
        } else {
            EasyPermissions.requestPermissions(this, "请先授予保存图片到SD卡的权限", PERMISSION_ID, permissions);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private View.OnClickListener mFinishListener;

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout
                    .layout_viewpager_image, container, false);
            PhotoView ivPreview = (PhotoView) view.findViewById(R.id.iv_preview);
            ivPreview.setOnClickListener(getListener());

            Loading loading = (Loading) view.findViewById(R.id.loading);
            loadImage(position, mImages[position], ivPreview, loading);
            container.addView(view);
            return view;
        }

        private View.OnClickListener getListener() {
            if (mFinishListener == null) {
                mFinishListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                };
            }
            return mFinishListener;
        }

        private void loadImage(final int position, String image, PhotoView ivPreview, final
        Loading loading) {
            DrawableRequestBuilder<String> builder = getImageLoader()
                    .load(image)
                    .error(R.drawable.ic_preview_split_graph)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable>
                                target, boolean isFirstResource) {

                            loading.stop();
                            loading.setVisibility(View.GONE);
                            updateSaveBtnStatus(position, false);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model,
                                                       Target<GlideDrawable> target, boolean
                                                               isFromMemoryCache, boolean
                                                               isFirstResource) {
                            loading.stop();
                            loading.setVisibility(View.GONE);
                            updateSaveBtnStatus(position, true);
                            return false;
                        }
                    });
            builder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
            builder.into(ivPreview);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showToast("您已经禁止了相关权限,请到设置界面重新开启");
        } else {
            showToast("您没有授予SD卡读写权限");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
