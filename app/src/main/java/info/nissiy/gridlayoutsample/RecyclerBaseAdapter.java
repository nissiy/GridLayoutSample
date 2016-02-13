package info.nissiy.gridlayoutsample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int TYPE_ITEM = 1;
    public final static int TYPE_PROG = 2;

    private final Object lock = new Object();
    protected final List<Object> objects;

    public RecyclerBaseAdapter(List<Object> objects) {
        this.objects = objects;
    }

    public void add(@NonNull Object object) {
        int position = objects.size();
        synchronized (lock) {
            objects.add(object);
        }
        notifyItemInserted(position);
    }

    public void remove(Object object) {
        int position = objects.indexOf(object);
        synchronized (lock) {
            objects.remove(object);
        }
        notifyItemRemoved(position);
    }

    public void clear() {
        synchronized (lock) {
            objects.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

}
