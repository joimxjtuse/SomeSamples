package cn.joim.samples.somesamples.image_loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import bean.ContentBean;
import cn.joim.headerandfooterrecyclerview.BaseViewHolder;
import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.utils.ActivityUtils;

/**
 * Created by joim on 2018/4/20.
 */

public class ImageLoaderAdapter extends RecyclerView.Adapter<ImageLoaderAdapter.ContentHolder> {

    private List<ContentBean> items;

    @NonNull
    private Context mContext;

    public ImageLoaderAdapter(@NonNull Context context) {
        mContext = context;
    }

    public void bindItems(List<ContentBean> list) {
        items = list;

        notifyDataSetChanged();
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_image_loader, parent, false));
    }

    @Override
    public void onBindViewHolder(final ContentHolder holder, int position) {

        ContentBean contentBean = getItem(position);

        String uriString = contentBean.getImageRes();

        ImageLoader.getInstance().loadImage(uriString, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mImageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mImageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        holder.mTextView.setText(contentBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public ContentBean getItem(int position) {
        return items.get(position);
    }

    @Override
    public void onViewRecycled(ContentHolder holder) {
        super.onViewRecycled(holder);

        if (holder != null) {
            holder.onRecycle();
        }
    }

    static final class ContentHolder extends BaseViewHolder {

        private ImageView mImageView;

        private TextView mTextView;

        public ContentHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(@NonNull View itemView) {

            mImageView = itemView.findViewById(R.id.image_view);

            mTextView = itemView.findViewById(R.id.text_view);

        }

        @Override
        public void onRecycle() {
            super.onRecycle();
            ImageLoader.getInstance().cancelDisplayTask(mImageView);
        }
    }
}
