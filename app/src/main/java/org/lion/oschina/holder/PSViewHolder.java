package org.lion.oschina.holder;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.lion.oschina.adapter.PSAdapter;
import org.lion.oschina.bean.Messages;
import org.lion.oschina.global.OsChinaApp;
import org.lion.oschina.tweet.util.ToastUtil;

import butterknife.Bind;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 17:50
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class PSViewHolder extends BasicHolder<Messages> {
    @Bind(R.id.iv_ps_pic)
    ImageView      mIvPsPic;
    @Bind(R.id.tv_ps_name)
    TextView       mTvPsName;
    @Bind(R.id.tv_praise_content)
    TextView       mTvPraiseContent;
    @Bind(R.id.tv_ps_time)
    TextView       mTvPsTime;
    @Bind(R.id.tv_ps_ps)
    TextView       mTvPsPs;
    @Bind(R.id.rl_ps_content)
    RelativeLayout mRlPsContent;
    private View mView;

    public PSViewHolder() {
        super();
    }

    public PSViewHolder(PSAdapter adapter, Context context) {
        super();
        mAdapter = adapter;
        mContext = context;
    }

    PSAdapter mAdapter;

    private Context mContext;


    @Override
    protected View createView() {
        mView = View.inflate(OsChinaApp.context, R.layout.item_ps, null);
        return mView;
    }

    @Override
    public void bindView(final Messages appInfo) {

        ImageLoader.getInstance().displayImage(appInfo.getPortrait(), mIvPsPic, mOptions);
        mTvPsName.setText(appInfo.getFriendName());
        mTvPraiseContent.setText(appInfo.getContent());
        mTvPsTime.setText(appInfo.getPubDate());
        mTvPsPs.setText(appInfo.getMessageCount() + "条留言");

        mRlPsContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showDialog(appInfo);
                return true;
            }
        });
    }

    private void showDialog(final Messages message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(OsChinaApp.context,
                R.layout.dialog_long_click, null);
        builder.setView(view);

        // 显示出来
        final AlertDialog dialog = builder.show();

        // 查找控件
        RelativeLayout tv_popup_copy = (RelativeLayout) view.findViewById(R.id.tv_popup_copy);
        RelativeLayout tv_popup_delete = (RelativeLayout) view.findViewById(R.id.tv_popup_delete);
        RelativeLayout tv_popup_long_cancel = (RelativeLayout) view.findViewById(R.id.tv_popup_long_cancel);
        tv_popup_long_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_popup_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

                cm.setText(message.getContent());
                ToastUtil.showToast("复制成功");

            }
        });
        tv_popup_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnsureDeleteDialog(message);
                dialog.dismiss();
                // mAdapter.deleteMessage(message);
            }
        });
    }

    private void showEnsureDeleteDialog(final Messages message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(OsChinaApp.context,
                R.layout.dialog_ensure_delete, null);
        builder.setView(view);

        // 显示出来
        final AlertDialog dialog = builder.show();

        // 查找控件
        TextView tv_dialog_info = (TextView) view.findViewById(R.id.tv_dialog_info);
        tv_dialog_info.setText("确定要删除" + message.getFriendName() + "的所有留言？");
        Button bt_dialog_ok = (Button) view.findViewById(R.id.bt_dialog_ok);
        Button bt_dialog_cancel = (Button) view.findViewById(R.id.bt_dialog_cancel);
        bt_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapter.deleteMessage(message);
                dialog.dismiss();
            }
        });
    }
}
