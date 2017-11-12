package org.mercury.oschina.explorer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.explorer.bean.Project;
import org.mercury.oschina.utils.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mercury on 2017/9/18.
 * 软件列表
 */

public class SoftwareListAdapter extends BaseRecyclerAdapter<Project> {

    public SoftwareListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final Project data, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.tvName.setText(data.getName());
        holder.tvDesc.setText(data.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showUrlRedirect(mContext, data.getUrl());
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_software, null));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
