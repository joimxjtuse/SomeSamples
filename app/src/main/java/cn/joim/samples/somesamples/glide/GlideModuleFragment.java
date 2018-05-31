package cn.joim.samples.somesamples.glide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.joim.headerandfooterrecyclerview.UniversalRecyclerView;
import cn.joim.samples.somesamples.utils.ActivityUtils;


/**
 * Created by joim on 2018/4/24.
 */

public class GlideModuleFragment extends Fragment {

    private GlideModuleViewModel mViewModel;

    private GlideModuleBinding mViewDataBinding;

    private UniversalRecyclerView mRecyclerView;
    GlideItemAdapter mAdapter;

    private TextView mTextTitle;

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


        // one init method.
//        View contentView = inflater.inflate(R.layout.fragment_glide, container, false);
//        if (mViewDataBinding == null) {
//            mViewDataBinding = GlideModuleBinding.bind(contentView);
//        }
        // another init method.
        mViewDataBinding = GlideModuleBinding.inflate(inflater, container, false);
        mViewDataBinding.setView(this);

        mViewDataBinding.setViewmodel(mViewModel);

        initList();
        mTextTitle = mViewDataBinding.textTitle;
//        getFragmentManager().beginTransaction().add()

        return mViewDataBinding.getRoot();
    }

    private void initList() {

        mRecyclerView = mViewDataBinding.recyclerView;

        mAdapter = new GlideItemAdapter(getContext());


    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }
}
