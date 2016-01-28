package info.nissiy.gridlayoutsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoGridAdapter extends RecyclerBaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int imageSize;

    public PhotoGridAdapter(@NonNull Context context, @NonNull List<Object> items, int spanCount) {
        super(items);
        this.context = context;
        inflater = LayoutInflater.from(context);
        int unitMargin = context.getResources().getDimensionPixelSize(R.dimen.unit_margin);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        imageSize = (widthPixels - (spanCount - 1) * unitMargin) / spanCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_ITEM) {
            FrameLayout view = (FrameLayout) inflater.inflate(R.layout.photo_layout, parent, false);
            AppCompatImageView photoImageView = (AppCompatImageView) view.findViewById(R.id.photo_image_view);
            photoImageView.setLayoutParams(new FrameLayout.LayoutParams(imageSize, imageSize));
            viewHolder = new PhotoLayoutHolder(view);
        } else {
            FrameLayout view = (FrameLayout) inflater.inflate(R.layout.progress_bar_layout, parent, false);
            viewHolder = new ProgressBarLayoutHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotoLayoutHolder) {
            Photo photo = (Photo) objects.get(position);
            FrameLayout photoLayout = ((PhotoLayoutHolder) holder).photoLayout;
            AppCompatImageView photoImageView = (AppCompatImageView) photoLayout.findViewById(R.id.photo_image_view);
            Picasso.with(context).load(photo.drawableResId).into(photoImageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        final Object object = objects.get(position);
        if (object instanceof ProgressStub) {
            return TYPE_PROG;
        } else {
            return TYPE_ITEM;
        }
    }

    public final class PhotoLayoutHolder extends RecyclerView.ViewHolder {
        public final FrameLayout photoLayout;

        public PhotoLayoutHolder(FrameLayout view) {
            super(view);
            photoLayout = view;
        }
    }

}
