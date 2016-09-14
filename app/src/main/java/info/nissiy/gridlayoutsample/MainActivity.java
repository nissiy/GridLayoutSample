package info.nissiy.gridlayoutsample;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, EndlessScrollListener.OnLoadMoreListener {
    private static Handler handler = new Handler();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Resources res;
    private PhotoGridAdapter adapter;
    private EndlessScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        res = getResources();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }

        int spanCount = 2;
        if (Util.isLandscape(getApplicationContext())) {
            spanCount = 3;
        }

        adapter = new PhotoGridAdapter(this, new ArrayList<>(), spanCount);
        GridWithProgressLayoutManager layoutManager = new GridWithProgressLayoutManager(this, spanCount, adapter);
        scrollListener = new EndlessScrollListener(1, layoutManager);
        scrollListener.setOnLoadMoreListener(this);

        int unitMargin = res.getDimensionPixelSize(R.dimen.unit_margin);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, unitMargin));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.color_primary);
        swipeRefreshLayout.setOnRefreshListener(this);
        loadData(1);
    }

    @Override
    public void onLoadMore(int currentPage) {
        loadData(currentPage);
    }

    private void loadData(final int page) {
        // Set the Stub for display the ProgressBar
        ProgressStub progressStub = new ProgressStub();
        if (page > 1) {
            handler.post(() -> adapter.add(progressStub));
        }

        handler.postDelayed(() -> {
            // Remove the Stub
            if (page > 1) {
                adapter.remove(progressStub);
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

            int startNum = 10 * (page - 1);
            int endNum = 10 * page - 1;
            for (; startNum <= endNum; startNum++) {
                int suffix = startNum % 8;
                int drawableResId = res.getIdentifier("photo" + suffix, "drawable", getPackageName());
                adapter.add(new Photo(drawableResId));
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        scrollListener.onRefresh();

        loadData(1);
    }

}
