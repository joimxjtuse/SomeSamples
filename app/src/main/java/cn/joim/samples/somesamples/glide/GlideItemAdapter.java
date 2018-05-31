package cn.joim.samples.somesamples.glide;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bean.ContentBean;
import cn.joim.samples.somesamples.R;
//import cn.joim.samples.somesamples.glide.ContentItemDataBinding;

/**
 * Created by joim on 2018/4/25.
 *
 * TODO 搞了半天adapter的binding，但是一直都报错，故而暂时恢复了旧的代码.
 */

public class GlideItemAdapter extends RecyclerView.Adapter<GlideItemAdapter.ContentHolder> {


    private Context mContext;

    private List<ContentBean> items;

    public GlideItemAdapter(Context context) {
        this.mContext = context;
    }

    public void bindItems(List<ContentBean> list) {
        items = list;

        notifyDataSetChanged();
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        ContentItemDataBinding binding = ContentItemDataBinding.inflate(LayoutInflater.from(parent.getContext()),
//                parent, false);
//        return new ContentHolder(binding);
        return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_glide, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {

//        ContentItemDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
//        ContentItemViewModel itemViewModel = new ContentItemViewModel(holder.itemView.getContext());
//
//        binding.setViewmodel(itemViewModel);
//        binding.executePendingBindings();
        ContentBean contentBean = getItem(position);

        Glide.with(holder.itemView.getContext()).load(contentBean.getImageRes()).into(holder.mImgThumbnail);
        holder.mTxtTitle.setText(contentBean.getTitle());
        holder.mTxtDescription.setText(contentBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ContentBean getItem(int position) {
        return items.get(position);
    }

    static final class ContentHolder extends RecyclerView.ViewHolder {

//        ContentItemDataBinding dataBinding;
//
//        public ContentHolder(ContentItemDataBinding dataBinding) {
//            super(dataBinding.getRoot());
//
//            this.dataBinding = dataBinding;
//        }

        ImageView mImgThumbnail;
        TextView mTxtTitle, mTxtDescription;


        public ContentHolder(@NonNull View itemView) {

            super(itemView);
            _init();
        }

        private void _init() {
            mImgThumbnail = itemView.findViewById(R.id.image_view);

            mTxtTitle = itemView.findViewById(R.id.text_title);
            mTxtDescription = itemView.findViewById(R.id.text_description);
        }
    }
}
