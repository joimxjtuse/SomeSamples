package cn.joim.samples.somesamples.glide;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by joim on 2018/4/25.
 */

public class ContentItemViewModel extends BaseObservable {

    final ObservableField<String> titleText = new ObservableField<>();

    final ObservableField<String> descriptionText = new ObservableField<>();

    final ObservableField<String> imageUrlStr = new ObservableField<>();

    private Context mContext;

    public ContentItemViewModel(Context context) {
        this.mContext = context;
    }

    public void start() {

    }

    public void onItemClick() {

    }

}
