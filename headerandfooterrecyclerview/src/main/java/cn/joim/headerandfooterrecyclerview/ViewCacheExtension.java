package cn.joim.headerandfooterrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * Created by joim on 2018/4/20.
 */

public class ViewCacheExtension extends RecyclerView.ViewCacheExtension {

    public static ViewCacheExtension sInstance;


    private SparseArray<SoftReference<View>> mSparseArray;

    public static ViewCacheExtension getInstance() {
        if (sInstance == null) {
            synchronized (ViewCacheExtension.class) {
                if (sInstance == null) {
                    sInstance = new ViewCacheExtension();
                }
            }
        }
        return sInstance;
    }

    private ViewCacheExtension() {

        _init();
    }

    private void _init() {
        mSparseArray = new SparseArray<>();
    }

    public boolean put(int position, View cachedView) {
        mSparseArray.put(position, new SoftReference(cachedView));
        return true;
    }


    @Override
    public View getViewForPositionAndType(RecyclerView.Recycler recycler, int position, int type) {
        return null;
    }
}
