package cn.joim.samples.headerandfooterrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joim on 2018/3/5.
 */

public class UniversalRecyclerView extends RecyclerView {


    private View mEmptyView;

    private List<View> mHeaderViews, mFooterViews;

    private boolean mIsHeaderFullScreen = true, mIsFooterScreen = true;

    private Adapter mRealAdapter;

    private HeaderViewRecyclerAdapter mDecoratingAdapter;

    public UniversalRecyclerView(Context context) {
        this(context, null);
    }

    public UniversalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UniversalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (getRecycledViewPool() != null) {
            getRecycledViewPool().setMaxRecycledViews(0, 10);
        }
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;

        checkIfEmpty();
    }

    private final void checkIfEmpty() {

        if (mEmptyView != null && mDecoratingAdapter != null) {

            final boolean emptyViewVisible = mRealAdapter.getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    public void setHeaderFullScreen(boolean isFull) {

        if (this.mIsHeaderFullScreen != isFull) {
            this.mIsHeaderFullScreen = isFull;

            if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                ((HeaderViewRecyclerAdapter) getAdapter()).notifyHeaderChanged();
            }
        }
    }

    public void setFooterFullScreen(boolean isFull) {

        if (this.mIsHeaderFullScreen != isFull) {
            this.mIsHeaderFullScreen = isFull;
            if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                ((HeaderViewRecyclerAdapter) getAdapter()).notifyFooterChanged();
            }
        }
    }

    public void addHeaderView(View headerView) {

        if (headerView == null) {
            return;
        }

        if (mDecoratingAdapter != null) {
            mDecoratingAdapter.addHeaderView(headerView);
        } else {

            initHeaderListIfNecessary();
            mHeaderViews.add(headerView);
        }

    }

    public void addFooterView(View footerView) {

        if (footerView == null) {
            return;
        }

        if (mDecoratingAdapter != null) {
            mDecoratingAdapter.addFooterView(footerView);
        } else {
            initFooterListIfNecessary();
            mFooterViews.add(footerView);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (mRealAdapter != null) {
            mRealAdapter.unregisterAdapterDataObserver(mObserver);
        }

        this.mRealAdapter = adapter;

        mRealAdapter.registerAdapterDataObserver(mObserver);

        if (mDecoratingAdapter != null) {
            mDecoratingAdapter.unRegisterInnerObservable();
        }

        //adaptive user adapter into header-footer-adapter if needed.
        if (adapter instanceof HeaderViewRecyclerAdapter) {
            mDecoratingAdapter = (HeaderViewRecyclerAdapter) adapter;
        } else if (adapter != null) {

            initHeaderListIfNecessary();
            initFooterListIfNecessary();
            mDecoratingAdapter = new HeaderViewRecyclerAdapter(mHeaderViews, mFooterViews, adapter);
        }

        super.setAdapter(mDecoratingAdapter);
        checkIfEmpty();
    }

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();

        }
    };

    @Override
    public void setLayoutManager(LayoutManager layout) {

        initSpanSizeIfNecessary(layout);
        super.setLayoutManager(layout);
    }

    /**
     * 对于GridLayoutManager的展示，默认：header、footer应该是占全屏的.
     */
    private void initSpanSizeIfNecessary(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                        HeaderViewRecyclerAdapter adapter = (HeaderViewRecyclerAdapter) getAdapter();
                        if (mIsHeaderFullScreen && adapter.isHeaderPosition(position)) {
                            return ((GridLayoutManager) getLayoutManager()).getSpanCount();
                        } else if (mIsFooterScreen && adapter.isFooterPosition(position)) {
                            return ((GridLayoutManager) getLayoutManager()).getSpanCount();

                        }
                    }
                    return 1;
                }
            };
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        }
    }


    private void initHeaderListIfNecessary() {

        if (mHeaderViews == null) {
            mHeaderViews = new ArrayList<>();
        }
    }

    private void initFooterListIfNecessary() {

        if (mFooterViews == null) {
            mFooterViews = new ArrayList<>();
        }
    }

    public int getHeaderViewsCount() {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).getHeaderViewsCount();
        }
        return 0;
    }

    public int getFooterViewsCount() {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).getFooterViewsCount();
        }
        return 0;
    }

    public boolean removeHeaderView(View headerView) {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).removeHeaderView(headerView);
        }
        return false;
    }

    public boolean removeFooterView(View footerView) {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).removeFooterView(footerView);
        }
        return false;
    }

    /**
     * 这种方式实现比较坑的事情就是header、footer会一直不会被回收，所以在destroy的时候需要单独访问clear
     */
    public void clear() {

        if (getAdapter() == null) {
            return;
        }

        if (mHeaderViews != null) {
            mHeaderViews.clear();
            mDecoratingAdapter.notifyHeaderChanged();
        }

        if (mFooterViews != null) {
            mFooterViews.clear();
            mDecoratingAdapter.notifyFooterChanged();
        }

        if (mRealAdapter != null) {
            try {
                mRealAdapter.unregisterAdapterDataObserver(mObserver);
            } catch (Exception exception) {
                //exception.printStackTrace();
            }
        }

        mRealAdapter = null;

        mDecoratingAdapter = null;
    }


}
