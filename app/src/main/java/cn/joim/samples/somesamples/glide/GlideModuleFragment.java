package cn.joim.samples.somesamples.glide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.utils.ActivityUtils;


/**
 * Created by joim on 2018/4/24.
 */

public class GlideModuleFragment extends Fragment {

    private GlideModuleViewModel mViewModel;

    private GlideModuleBinding mViewDataBinding;

    public void setViewModel(@NonNull GlideModuleViewModel viewModel) {
        mViewModel = ActivityUtils.checkNotNull(viewModel);
    }


    public static GlideModuleFragment newInstance() {
        return new GlideModuleFragment();
    }

    public GlideModuleFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_glide, container, false);

        if (mViewDataBinding == null) {
            mViewDataBinding = GlideModuleBinding.bind(contentView);
        }
        mViewDataBinding.setViewmodel(mViewModel);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }
}
