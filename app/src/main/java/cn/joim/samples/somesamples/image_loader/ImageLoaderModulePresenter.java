package cn.joim.samples.somesamples.image_loader;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import bean.ContentBean;
import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.utils.Cnstants;

/**
 * Created by joim on 2018/4/20.
 */

public class ImageLoaderModulePresenter implements ImageLoaderModule.Presenter {

    private Handler mHandler = new Handler();

    @NonNull
    private final ImageLoaderModule.View mImageLoaderModuleView;

    public ImageLoaderModulePresenter(@NonNull ImageLoaderModule.View imageLoaderModuleView) {

        mImageLoaderModuleView = imageLoaderModuleView;
        mImageLoaderModuleView.setPresenter(this);
    }

    /**
     * lazy load.
     */
    @Override
    public void start() {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {

                //请求列表数据，通过view反馈给fragment.
                final List<ContentBean> list = new ArrayList<>();

                for (int i = 0; i < 10000; i++) {

                    int position = i % Cnstants.IMAGES.length;

                    list.add(new ContentBean(Cnstants.IMAGES[position], "标题 : " + i));

                }
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mImageLoaderModuleView.bindListContent(list);

                    }
                }, 2500);
            }
        });

        loadTitle();
    }

    @Override
    public void loadTitle() {
        mImageLoaderModuleView.setTitle("This is ImageLoaderTitle!");

    }
}
