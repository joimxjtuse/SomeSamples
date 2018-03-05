package cn.joim.samples.somesamples;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.joim.samples.headerandfooterrecyclerview.UniversalRecyclerView;
import cn.joim.samples.somesamples.model.ListItem;

public class MainActivity extends AppCompatActivity {

    UniversalRecyclerView mRecyclerView;

    private MyAdapter myAdapter;

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
        mRecyclerView.addFooterView(footerView);

        myAdapter.notifyDataSetChanged();
    }

    private void writeListItems(@NonNull List<ListItem> list) {

        for (int i = 0; i < 20; i++) {

            list.add(new ListItem("this is " + (i + 1) + " item,.", ""));
        }


    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        private List<ListItem> lists;

        public void setItems(List<ListItem> items) {
            lists = items;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

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

    private class MyHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.txt_title);
        }
    }

}
