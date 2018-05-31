package cn.joim.samples.somesamples.glide;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import bean.ContentBean;

/**
 * Created by joim on 2018/4/26.
 */

public class Utils {

   /* @BindingAdapter("bind:image")
    public static void loadImage(ImageView imageView, String uriString) {

        Glide.with(imageView.getContext()).load(uriString).into(imageView);
    }*/

    @SuppressWarnings("unchecked")
    @BindingAdapter({"app:items"})
    public static void bindData(RecyclerView recyclerView, List<ContentBean> lists) {

        GlideItemAdapter adapter = new GlideItemAdapter(recyclerView.getContext());
        adapter.bindItems(lists);

        recyclerView.setAdapter(adapter);
    }

}

