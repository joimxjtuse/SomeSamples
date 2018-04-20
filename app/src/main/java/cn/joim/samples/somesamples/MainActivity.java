package cn.joim.samples.somesamples;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.joim.samples.headerandfooterrecyclerview.BaseViewHolder;
import cn.joim.samples.headerandfooterrecyclerview.UniversalRecyclerView;
import cn.joim.samples.somesamples.model.ListItem;
import cn.joim.samples.somesamples.widget.LoopViewPager;

public class MainActivity extends AppCompatActivity {

    UniversalRecyclerView mRecyclerView, mHeaderRecyclerView;

    private MyAdapter myAdapter;

    /**
     * 参考了git@github.com:cundong/HeaderAndFooterRecyclerView.git作者的思路，但是感觉对于做UI的同学，需要对adapter主动进行封装才能
     * 实现，这对写功能的人应该是不舒服的，如果可以向ListView思路一样，更少的学习成本也许更优雅一些，设计了这样一套可动态添加Header和Footer的RecyclerView.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        View headerView = View.inflate(this, R.layout.item_header, null);
        View footerView = View.inflate(this, R.layout.item_footer, null);

        myAdapter = new MyAdapter();
        List<ListItem> itemLists = new ArrayList<>();
        writeListItems(itemLists);
        myAdapter.setItems(itemLists);

        mRecyclerView.addHeaderView(headerView);

        mRecyclerView.setAdapter(myAdapter);

        // add gallery header.
        mHeaderRecyclerView = new UniversalRecyclerView(this);
        mHeaderRecyclerView.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(mHeaderRecyclerView);

        LinearLayoutManager galleryLayoutManager = new LinearLayoutManager(this);
        galleryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHeaderRecyclerView.setLayoutManager(galleryLayoutManager);
        mHeaderRecyclerView.setAdapter(new MyGalleryAdapter(this));
        mRecyclerView.addHeaderView(mHeaderRecyclerView);

        mRecyclerView.addFooterView(footerView);
    }

    private void writeListItems(@NonNull List<ListItem> list) {

        for (int i = 0; i < 10000; i++) {

            list.add(new ListItem("this is " + (i + 1) + " item,.", ""));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mHeaderRecyclerView != null) {
            mHeaderRecyclerView.clear();
        }
        if (mRecyclerView != null) {
            mRecyclerView.clear();
        }
        super.onDestroy();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        private List<ListItem> lists;

        public void setItems(List<ListItem> items) {
            lists = items;
        }

        private int mCreateCount, mBindCount;

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.i("joim", "onCreateViewHolder times:" + (++mCreateCount));

            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Log.i("joim", "onBindViewHolder times:" + (++mBindCount));
            ListItem item = getItem(position);
            holder.mTextView.setText(item.getTitle());

        }

        public ListItem getItem(int position) {
            return lists.get(position);
        }


        @Override
        public int getItemCount() {
            return lists.size();
        }
    }

    private static class MyHolder extends BaseViewHolder {

        private TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(@NonNull View itemView) {
            mTextView = itemView.findViewById(R.id.txt_title);
        }

        @Override
        public void onRecycle() {
            super.onRecycle();
        }
    }

    private class MyGalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {

        private Context _context;

        private List<Integer> list = new ArrayList();

        public MyGalleryAdapter(Context context) {

            _context = context;
            init();
        }

        void init() {
            list.add(R.mipmap.image_10);
            list.add(R.mipmap.image_11);
            list.add(R.mipmap.image_12);
            list.add(R.mipmap.image_13);
            list.add(R.mipmap.image_14);
            list.add(R.mipmap.image_15);
            list.add(R.mipmap.image_16);
            list.add(R.mipmap.image_17);
            list.add(R.mipmap.image_18);
            list.add(R.mipmap.image_19);

        }

        @Override
        public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GalleryHolder(LayoutInflater.from(_context)
                    .inflate(R.layout.item_gallery, parent, false));
        }

        @Override
        public void onBindViewHolder(GalleryHolder holder, int position) {

            holder.imageView.setImageResource(getItem(position));
        }

        public int getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class GalleryHolder extends BaseViewHolder {

        private ImageView imageView;

        public GalleryHolder(View itemView) {
            super(itemView);


        }

        @Override
        protected void initView(@NonNull View itemView) {
            imageView = itemView.findViewById(R.id.image_view);
        }

        @Override
        public void onRecycle() {
            super.onRecycle();
            //imageview.cancelRequest();
        }
    }

}
