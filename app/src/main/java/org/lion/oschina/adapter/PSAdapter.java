package org.lion.oschina.adapter;

import android.content.Context;

import org.lion.oschina.bean.Messages;
import org.lion.oschina.holder.BasicHolder;
import org.lion.oschina.holder.PSViewHolder;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 17:49
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class PSAdapter extends BasicAdapter<Messages> {
    public PSAdapter(List<Messages> showItems) {
        super(showItems);
    }

    Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public BasicHolder createViewHolder(int postion) {
        PSViewHolder psViewHolder = new PSViewHolder(this,mContext);
        return psViewHolder;
    }

    public  void deleteMessage(Messages messages){
        if (mShowItems!=null&&mShowItems.contains(messages)){
            mShowItems.remove(messages);
            notifyDataSetChanged();
        }
    }
}
