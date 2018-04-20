package cn.joim.headerandfooterrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by joim on 2018/2/28.
 */
class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;

    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE / 2;

    private static final int TYPE_NORMAL_VIEW = Integer.MAX_VALUE / 2;

    private List<View> mHeaderViews;

    private List<View> mFooterViews;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mRealAdapter;

    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    /**
     * warning : constructor method if changed to be public, make true list not empty, very very important.
     */
    protected HeaderViewRecyclerAdapter(@NonNull List<View> headerViews, @NonNull List<View> footerViews,
                                        @NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> realAdapter) {

        this.mHeaderViews = headerViews;

        this.mFooterViews = footerViews;

        this.mRealAdapter = realAdapter;

        _init();
    }

    private void _init() {
        mRealAdapter.registerAdapterDataObserver(dataObserver);
        //registerAdapterDataObserver(dataObserver);
    }

    protected void unRegisterInnerObservable() {
        //unregisterAdapterDataObserver(dataObserver);
        mRealAdapter.unregisterAdapterDataObserver(dataObserver);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int headerViewsCountCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewsCountCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < TYPE_NORMAL_VIEW) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mRealAdapter.onCreateViewHolder(parent, viewType - TYPE_NORMAL_VIEW);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();

        if (position >= headerViewsCountCount && position < headerViewsCountCount + mRealAdapter.getItemCount()) {
            mRealAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            holder.setIsRecyclable(false);
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + mRealAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {

        int realCount = mRealAdapter.getItemCount();

        int headerViewsCountCount = getHeaderViewsCount();

        if (position < headerViewsCountCount) {

            return TYPE_HEADER_VIEW + position;
        } else if (position >= headerViewsCountCount && position < headerViewsCountCount + realCount) {

            int realItemViewType = mRealAdapter.getItemViewType(position - headerViewsCountCount);
            if (realItemViewType >= TYPE_NORMAL_VIEW) {
                throw new IllegalArgumentException("what is an strange list type!");
            }
            return realItemViewType + TYPE_NORMAL_VIEW;
        } else {
            return TYPE_FOOTER_VIEW + position - headerViewsCountCount - realCount;
        }
    }

    @Override
    public final long getItemId(int position) {

        long itemId;
        int realCount = mRealAdapter.getItemCount();

        int headerViewsCountCount = getHeaderViewsCount();
        if (position < headerViewsCountCount) {
            itemId = mHeaderViews.get(position).hashCode();
        } else if (position >= headerViewsCountCount && position < headerViewsCountCount + realCount) {
            itemId = mRealAdapter.getItemId(position);
        } else {
            itemId = mFooterViews.get(position - headerViewsCountCount - realCount).hashCode();
        }
        return itemId;
    }

    protected boolean isHeaderPosition(int position) {
        return position >= 0 && position < getHeaderViewsCount();
    }

    protected boolean isFooterPosition(int position) {

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        return position >= start && start < getItemCount();
    }

    protected int getHeaderViewsCount() {
        return mHeaderViews != null ? mHeaderViews.size() : 0;
    }

    protected int getFooterViewsCount() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    protected void addHeaderView(View header) {

        if (header != null) {
            mHeaderViews.add(header);

            notifyHeaderInserted();
        }
    }

    protected void addFooterView(View footer) {

        if (footer != null) {
            mFooterViews.add(footer);

            notifyFooterInserted();
        }
    }

    protected boolean removeHeaderView(View view) {
        boolean removed = mHeaderViews.remove(view);
        if (removed) {
            notifyHeaderRemoved();
        }
        return removed;
    }

    protected boolean removeFooterView(View view) {
        boolean removed = mFooterViews.remove(view);
        if (removed) {
            notifyFooterRemoved();
        }
        return removed;
    }


    protected void notifyHeaderChanged() {

        this.notifyItemRangeInserted(0, getHeaderViewsCount());
    }

    protected void notifyFooterChanged() {

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeInserted(start, getFooterViewsCount());
    }

    private void notifyHeaderInserted() {

        this.notifyItemRangeInserted(0, getHeaderViewsCount());
    }

    private void notifyFooterInserted() {

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeInserted(start, getFooterViewsCount());
    }

    private void notifyHeaderRemoved() {

        this.notifyItemRangeRemoved(0, getHeaderViewsCount());
    }

    private void notifyFooterRemoved() {

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeRemoved(start, getFooterViewsCount());
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
