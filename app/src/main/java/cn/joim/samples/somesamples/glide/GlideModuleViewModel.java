package cn.joim.samples.somesamples.glide;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import bean.ContentBean;
import cn.joim.samples.somesamples.BR;
import cn.joim.samples.somesamples.utils.Cnstants;


/**
 * Created by joim on 2018/4/24.
 */

public class GlideModuleViewModel extends BaseObservable {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableList<ContentBean> items = new ObservableArrayList<>();

    // To avoid leaks, this must be an Application Context.
    private final Context mContext;

    private Handler mHandler;

    public GlideModuleViewModel(Context _context) {

        this.mContext = _context.getApplicationContext();

        mHandler = new Handler();
    }

    public void onActivityCreated() {

    }

    public void start() {

        title.set("This is todo-mvvm arthitecture.");

        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {

                //请求列表数据，通过view反馈给fragment.
                final List<ContentBean> list = new ArrayList<>();

                for (int i = 0; i < 10000; i++) {

                    int position = i % Cnstants.IMAGES.length;

                    list.add(new ContentBean(Cnstants.IMAGES[position], "标题 : " + i));
                }

                items.addAll(list);
                notifyPropertyChanged(BR._all);
            }
        });

    }
}
