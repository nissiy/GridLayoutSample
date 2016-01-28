package info.nissiy.gridlayoutsample;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;

    public GridSpacingItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Skip in the case of ProgressBarLayoutHolder
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        if (viewHolder instanceof ProgressBarLayoutHolder) {
            return;
        }

        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.left = column * spacing / spanCount;
        outRect.right = spacing - (column + 1) * spacing / spanCount;
        outRect.bottom = spacing;
    }

}
