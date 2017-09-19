package org.mercury.oschina.explorer.adapter;

import org.mercury.oschina.base.BaseListAdapter;
import org.mercury.oschina.base.ViewHolder;
import org.mercury.oschina.explorer.bean.SoftwareCatalog;

/**
 * Created by Mercury on 2017/9/19.
 */

public class SofawareCatalogAdapter extends BaseListAdapter<SoftwareCatalog> {

    public SofawareCatalogAdapter(Callback callback) {
        super(callback);
    }

    @Override
    protected void convert(ViewHolder vh, SoftwareCatalog item, int position) {

    }

    @Override
    protected int getLayoutId(int position, SoftwareCatalog item) {
        return 0;
    }
}
