package info.nissiy.gridlayoutsample;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private int visibleThreshold;
    private boolean loading = true;
    private int previousTotal = 0;
    private int currentPage = 1;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore(int currentPage);
    }

    public EndlessScrollListener(int visibleThreshold, LinearLayoutManager layoutManager) {
        this.visibleThreshold = visibleThreshold;
        this.layoutManager = layoutManager;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        // Adjust in order to display the ProgressBar in GridLayoutManager
        if (loading && totalItemCount > previousTotal + 1) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            loading = true;
            if (onLoadMoreListener != null) {
                recyclerView.post(() -> onLoadMoreListener.onLoadMore(currentPage));
            }
        }
    }

    public void onRefresh() {
        loading = true;
        previousTotal = 0;
        currentPage = 1;
    }

}