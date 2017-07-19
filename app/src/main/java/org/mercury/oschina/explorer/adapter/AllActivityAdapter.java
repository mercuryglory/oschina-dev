package org.mercury.oschina.explorer.adapter;


import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.holder.AllActivityHolder;

import java.util.List;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/15 21:49
 * @描述 ${TODO}
 */
public class AllActivityAdapter extends BasicAdapter<Event> {
    public AllActivityAdapter(List<Event> list) {
        super(list);
    }

    @Override
    public BasicHolder createHolder(int position) {
        return new AllActivityHolder();
    }
   /* List<Event> mEvents = new ArrayList<>();

    public AllActivityAdapter(List<Event> list) {
        mEvents = list;
        System.out.println("mEvents" + mEvents);
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Event getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.all_activity_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Event item = getItem(position);
        holder.mTvAllTitle.setText(item.getTitle());
        holder.mTvAllTime.setText(item.getEndTime());
        String url = item.getUrl();

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.ic_all_icon)
        ImageView mIcAllIcon;
        @Bind(R.id.tv_all_title)
        TextView mTvAllTitle;
        @Bind(R.id.tv_all_time)
        TextView mTvAllTime;
        @Bind(R.id.tv_all_content)
        TextView mTvAllContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }*/
}
