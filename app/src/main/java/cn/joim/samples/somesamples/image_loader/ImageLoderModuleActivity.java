package cn.joim.samples.somesamples.image_loader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.utils.ActivityUtils;

/**
 * 为了巩固下android-arthitectrue的几个小项目，这里我会尽量在实现中按功能使用"https://github.com/joimxjtuse/android-architecture.git"
 * 中的设计，希望对这几个结构有个人的认识和了解.
 * <p>
 * todo-mvp
 * 这里做两处总结：todmvp和imageloader.
 * <p>
 * ImageLoader，使用taskExecutor和taskExecutorForCachedImages来处理网络任务和磁盘任务，内存cache。
 * <p>
 * 当前我们所做的项目，如果使用ImageLoader，可能有有一些并不太好扩展的：
 * 1. response均为bitmap，如果想要扩展gif图片，这一扩展就比较麻烦；
 * 最早在使用ImageLoader的时候，对于这一功能，曾经使用过这一解决方案：
 * 1) 监听文件下载事件，使用开源的GifImageView再次读文件支持gif的办法；
 * 2) 还有一种方案，将ImageLoadingListener、memeryCache所返回的均调整为Drawable，解析之前根据图片的格式分别解析为BitmapDrawable/GifDrawable也可以解决（这一方案改动量不小，但比方案I优一些）。
 * <p>
 * 2. 对于volley、glide等有一个比较好的有点，就是针对当前widget的尺寸去解析出最适合尺寸的图片，这在内存上有很多优势，而ImageLoader提供了ImageAware来自定义图片
 * 尺寸（默认返回的当前手机的尺寸值）。
 * 1）这一问题，在最开始学习Volley的时候，曾将NetWorkImageView的思路借鉴过来，所有图片请求由NetWorkImageView发出，也可解决；
 * <p>
 * <p>
 * todo-mvp，2018年前时间比较充裕的时候阅读了开源的几个android arthitectrue，感觉这个项目更有实践价值些，也更适合多人维护的项目，当项目比较大的时候可读性也高些。（个人感觉，希望之后的深入学习会有另一种意识）
 * 按模块划分功能，自定义view接口用于做数据绑定（可在activity/fragment内实现），自定义presenter负责数据请求，view和presenter互相关联。
 *
 *                                           model <--- presenter <---> view
 */
public class ImageLoderModuleActivity extends AppCompatActivity {

    private ImageLoaderModulePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loder);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());

        ImageLoderModuleFragment imageLoderModuleFragment = (ImageLoderModuleFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (imageLoderModuleFragment == null) {

            imageLoderModuleFragment = ImageLoderModuleFragment.newInstance();

            Bundle bundle = new Bundle();

            imageLoderModuleFragment.setArguments(bundle);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), imageLoderModuleFragment, R.id.contentFrame);
        }

        mPresenter = new ImageLoaderModulePresenter(imageLoderModuleFragment);


    }

}
