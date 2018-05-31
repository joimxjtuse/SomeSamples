package cn.joim.samples.somesamples.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.glide.todomvvm_template.ViewModelHolder;
import cn.joim.samples.somesamples.utils.ActivityUtils;

/**
 * Glide在4后的版本，响应到Ui层的是Drawable，而不再试GlideDrawable；最初引入glide的目的，主要还在于它对于gif、png、webp支持较好，而且
 * ui层不再刻意针对图片格式做特殊处理。Glide有几处好处是好于我们之前的model层代码的：
 * 1. 图片请求会伴随activity/fragment的生命周期的结束而释放部分资源，这在之前的image-loader、volley基础上是不太容易实现这个的；
 * 2. 不同格式的图片不需要再刻意处理，image-loader和volley就没有这一优势，在不大量修改源码的情况下支持并不太好；
 * <p>
 * 我们这边最开始使用的是高度定制的volley，其实在加载速度上，倒没太感觉到glide在速度上的优势，初期的目的只是在响应图片请求的时候
 * 返回的是Drawable（gif/bitmapdrawable），全部ImageView组件透明的支持gif而已，glide的引入减化了很多图片请求的业务处理，
 * ui层做的也仅仅是发出请求而已了。
 */
public class GlideModuleActivity extends AppCompatActivity {

    public static final String GLIDE_MODULE_VIEWMODEL_TAG = "GLIDE_MODULE_VIEWMODEL_TAG";

    GlideModuleViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Glide.with(this).load("").listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_module);

        mViewModel = findOrCreateViewModel();

        GlideModuleFragment fragment = findOrCreateViewFragment();

        // Link View and ViewModel
        fragment.setViewModel(mViewModel);

        mViewModel.onActivityCreated();
    }

    @NonNull
    private GlideModuleFragment findOrCreateViewFragment() {

        GlideModuleFragment fragment = (GlideModuleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = GlideModuleFragment.newInstance();

            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.contentFrame);
        }
        return fragment;
    }

    private GlideModuleViewModel findOrCreateViewModel() {

        ViewModelHolder<GlideModuleViewModel> retainedViewModel = (ViewModelHolder<GlideModuleViewModel>) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {

            // There is no ViewModel yet, create it.
            GlideModuleViewModel viewModel = new GlideModuleViewModel(getApplicationContext());

            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    GLIDE_MODULE_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
                 