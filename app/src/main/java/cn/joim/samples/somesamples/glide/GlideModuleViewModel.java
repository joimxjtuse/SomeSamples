package cn.joim.samples.somesamples.glide;

import android.content.Context;
import android.databinding.ObservableField;

/**
 * Created by joim on 2018/4/24.
 */

public class GlideModuleViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    // To avoid leaks, this must be an Application Context.
    private final Context mContext;

    public GlideModuleViewModel(Context _context) {

        this.mContext = _context.getApplicationContext();
    }

    public void onActivityCreated() {

    }

    public void start() {

        title.set("This is todo-mvvm arthitecture.");

    }
}
