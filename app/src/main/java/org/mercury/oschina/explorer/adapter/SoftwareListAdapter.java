package org.mercury.oschina.explorer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.explorer.bean.Project;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mercury on 2017/9/18.
 */

public class SoftwareListAdapter extends BaseRecyclerAdapter<Project> {

    public SoftwareListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Project data, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.tvName.setText(data.getName());
        holder.tvDesc.setText(data.getDescription());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_software, null));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
