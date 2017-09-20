package org.mercury.oschina.explorer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.explorer.bean.SoftwareCatalog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mercury on 2017/9/19.
 */

public class SoftwareCatalogAdapter extends BaseAdapter {

    protected List<SoftwareCatalog> mData = new ArrayList<>();
    protected Context mContext;

    public SoftwareCatalogAdapter() {

    }

    public SoftwareCatalogAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<SoftwareCatalog> list) {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public SoftwareCatalog getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_project_catalog, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SoftwareCatalog catalog = getItem(position);
        holder.tvCatalog.setText(catalog.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvCatalog;

        ViewHolder(View view) {
            tvCatalog = (TextView) view.findViewById(R.id.tv_catalog);
        }
    }

}
