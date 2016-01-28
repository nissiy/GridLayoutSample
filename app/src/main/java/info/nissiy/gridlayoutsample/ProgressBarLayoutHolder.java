package info.nissiy.gridlayoutsample;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

public class ProgressBarLayoutHolder extends RecyclerView.ViewHolder {
    public final FrameLayout progressBarLayout;

    public ProgressBarLayoutHolder(FrameLayout view) {
        super(view);
        progressBarLayout = view;
    }
}
