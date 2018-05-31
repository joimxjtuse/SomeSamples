package cn.joim.samples.somesamples.glide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.glide.todomvvm_template.ViewModelHolder;
import cn.joim.samples.somesamples.utils.ActivityUtils;

/**
 *
 * Glide在4.0以后的版本，将
 *
 *
 *
 *
 * */
public class GlideModuleActivity extends AppCompatActivity {

    public static final String GLIDE_MODULE_VIEWMODEL_TAG = "GLIDE_MODULE_VIEWMODEL_TAG";

    GlideModuleViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                 