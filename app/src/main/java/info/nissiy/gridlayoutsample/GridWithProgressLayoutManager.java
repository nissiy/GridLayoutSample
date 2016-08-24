package info.nissiy.gridlayoutsample;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class GridWithProgressLayoutManager extends GridLayoutManager {

    public GridWithProgressLayoutManager(Context context,
                                         final int spanCount,
                                         final RecyclerBaseAdapter adapter) {
        super(context, spanCount);

        SpanSizeLookup spanSizeLookup = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null
                        && adapter.getItemViewType(position) == RecyclerBaseAdapter.TYPE_PROG) {
                    return spanCount;
                }
                return 1;
            }
        };
        spanSizeLookup.setSpanIndexCacheEnabled(true);
        setSpanSizeLookup(spanSizeLookup);
    }

}