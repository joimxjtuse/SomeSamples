package cn.joim.samples.somesamples.image_loader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bean.ContentBean;
import cn.joim.headerandfooterrecyclerview.UniversalRecyclerView;
import cn.joim.samples.somesamples.R;
import cn.joim.samples.somesamples.widget.EmptyView;

/**
 * Created by joim on 2018/4/20.
 */

public class ImageLoderModuleFragment extends Fragment implements ImageLoaderModule.View {

    private ImageLoaderModule.Presenter mPresenter;

    private TextView mTextTitle;

    private UniversalRecyclerView mRecyclerView;

    private ImageLoaderAdapter mAdapter;

    public static ImageLoderModuleFragment newInstance() {
        return new ImageLoderModuleFragment();
    }

    @Override
    public void setPresenter(ImageLoaderModule.Presenter presenter) {

        this.mPresenter = presenter;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_image_loader, container, false);
        initUi(contentView);
        return contentView;
    }

    private void initUi(View contentView) {

        mTextTitle = contentView.findViewById(R.id.text_title);

        mRecyclerView = contentView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EmptyView emptyView = new EmptyView(getContext());
        mRecyclerView.setEmptyView(emptyView);

        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.start();
            }
        });

        mAdapter = new ImageLoaderAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setTitle(String titleStr) {
        mTextTitle.setText(titleStr);
    }

    @Override
    public void bindListContent(List<ContentBean> listItems) {
        mAdapter.bindItems(listItems);

    }
}
